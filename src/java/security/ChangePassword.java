/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import business.Security;
import business.User;
import data.UserDB;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peter
 */
@WebServlet(name = "ChangePassword", urlPatterns = {"/ChangePassword"})
public class ChangePassword extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String message;
        message = "";
        String url;
        javax.servlet.http.HttpSession session = request.getSession(); 
        User currentUser = (User)session.getAttribute("currentUser");
        if (currentUser != null &&
            currentUser.getSecurity().checkPassword(oldPassword)) {
              Security security = currentUser.getSecurity();
              security.setPassword(newPassword);
              boolean isTemporary = false;
              security.setExpirationDate(isTemporary);
              UserDB.updateSecurity(security);
              url = "/mainMenu.jsp";
              message = "Wachtwoord is aangepast.";
        }
        else {
              url = "/changePassword.jsp";
              message = "Oude wachtwoord niet juist, probeer het opnieuw.";
        }
        session = request.getSession(true);
        session.setAttribute("currentUser", currentUser);
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
