/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userRoles;

import business.Accommodation;
import business.UserRole;
import data.PriceDB;
import data.UserDB;
import data.UserRoleDB;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Peter
 */
@WebServlet(name = "DeleteUserRole", urlPatterns = {"/DeleteUserRole"})
public class DeleteUserRole extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession(true);

        Enumeration paramNames = request.getParameterNames();
        String paramName = " ";
        String message = "Gebruikersrol niet verwijderd";
        while (paramNames.hasMoreElements()) {
            paramName = (String) paramNames.nextElement();
            if (paramName != null
                    && paramName.contains("delete")) {
                int userRoleIndex = Integer.parseInt(paramName.substring(6));
                UserRole userRole = UserRoleDB.selectUserRole(userRoleIndex);
                UserDB.updateUserRoleAllUsers(userRoleIndex, 2);
//                users with deleted userrole get the quest userrole (id=2)
                PriceDB.deleteAccommodationPrice(userRole);
                UserRoleDB.deleteUserRole(userRoleIndex);

                Accommodation currentAccommodation =
                        (Accommodation) request.getSession().getAttribute("currentAccommodation");
                currentAccommodation.getPriceLevels().remove(userRole.getDescription());
                message = "Gebruikersrol verwijderd";
            }
        }
        String url = "/ShowUserRoles";

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
