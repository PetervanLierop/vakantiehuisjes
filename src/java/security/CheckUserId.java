/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import business.Accommodation;
import business.User;
import data.AccommodationDB;
import data.UserDB;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Peter
 */
@WebServlet(name = "CheckUserId", urlPatterns = {"/CheckUserId"})
public class CheckUserId extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    HttpSession session;

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession(true);
        
//        String accept = request.getHeader("accept");
//        String connection = request.getHeader("connection");
//        String host = request.getHeader("host");
//        String referer = request.getHeader("referer");
//        String userAgent = request.getHeader("user-agent");
        
        String inlogName = request.getParameter("inlogName");
        String passWord = request.getParameter("passWord");
        String message;
        message = "";
        String url;
        User currentUser = UserDB.selectUser(inlogName);
        if (currentUser == null) {
            url = "/index.jsp";
            message = "Inlognaam is onbekend";
        } else {
            if (currentUser.getSecurity().checkPassword(passWord)) {
                List<Accommodation> accommodations = AccommodationDB.selectAllAccommodations();
                session.setAttribute("accommodations", (Serializable) accommodations);
                boolean isAdministrator = currentUser.getSecurity().isAdministrator();
                session.setAttribute("isAdministrator", isAdministrator);
                Accommodation currentAccommodation = AccommodationDB.selectAccomodation(1);
                session.setAttribute("currentAccommodation", currentAccommodation);
                session.setAttribute("currentUser", currentUser);
                if (currentUser.getSecurity().checkExpirationDate()) {
                    url = "/mainMenu.jsp";
                } else {
                    url = "/changePasswordMandatory.jsp";
                    message = "Wachtwoord is verlopen.";
                }
            } else {
                url = "/index.jsp";
                message = "Combinatie inlognaam en wachtwoord is onbekend";
            }
        }
        request.setAttribute("message", message);

        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
