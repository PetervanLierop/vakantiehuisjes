/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bookings;

import business.Accommodation;
import business.Booking;
import business.User;
import data.BookingDB;
import data.UserDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
@WebServlet(name = "ProcessShowBookings", urlPatterns = {"/ProcessShowBookings"})
public class ProcessShowBookings extends HttpServlet {

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
        String message = " ";
        String url = " ";
        Enumeration paramNames = request.getParameterNames();
        String paramName = " ";
        while(paramNames.hasMoreElements()) {
            paramName = (String)paramNames.nextElement();
            if (paramName != null &&
                paramName.contains("delete") ||
                paramName.contains("update") ||
                paramName.contains("detail")) {
                int bookingIndex = Integer.parseInt(paramName.substring(6));
                if (paramName.contains("delete")) {
                            BookingDB.deleteBooking(bookingIndex);
                        message = "Booking verwijderd";
                        url = "/CollectBookings";
                } else {
                        Booking booking = BookingDB.selectBooking(bookingIndex);
                        request.setAttribute("booking", booking);
                        if (paramName.contains("update")) {
                            url = "/updateBooking.jsp";
                        } else {
                            url = "/bookingDetails.jsp";
                    } 
                }
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
