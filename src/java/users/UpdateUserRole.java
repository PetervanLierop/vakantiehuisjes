/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

import business.User;
import business.UserRole;
import data.UserDB;
import data.UserRoleDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Check;

/**
 *
 * @author Peter
 */
@WebServlet(name = "UpdateUserRole", urlPatterns = {"/UpdateUserRole"})
public class UpdateUserRole extends HttpServlet {

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
        boolean inputIsOK = true;
        int userId = 0;
        int userRoleId = 0;
        String message = "";
        String url = "/collectUsers";
        String userIdString = request.getParameter("userId");
        if (Check.isInteger(userIdString)) {
            userId = Integer.parseInt(userIdString);
        } else {
            inputIsOK = false;
        }
        String userRoleIdString = request.getParameter("userRoleId");
        if (Check.isInteger(userRoleIdString)) {
            userRoleId = Integer.parseInt(userRoleIdString);
        } else {
            inputIsOK = false;
        }
        if (inputIsOK) {
            boolean administrator = (request.getParameter("administrator") != null);
            UserRole userRole = UserRoleDB.selectUserRole(userRoleId);
            User user = UserDB.selectUser(userId);
            user.setUserRole(userRole);
            user.setAdministrator(administrator);
            message = "Gebruikersrol aangepast";
        } else {
            message = "Gebruikersrol niet aangepast, zoek hulp..";
        }
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
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
