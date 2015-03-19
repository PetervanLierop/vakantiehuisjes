/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bookings;

import business.Booking;
import data.BookingDB;
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
@WebServlet(name = "ProcessBookingDetails", urlPatterns = {"/ProcessBookingDetails"})
public class ProcessBookingDetails extends HttpServlet {

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
        int bookingIndex = 0;
        boolean inputIsOK = true;
        String bookingIndexString = request.getParameter("bookingIndex");
        if (Check.isInteger(bookingIndexString)) {
            bookingIndex = Integer.parseInt(bookingIndexString); 
        } else {
            inputIsOK = false;
        }
        int startElectrReadingLow = 0;
        String startElectrReadingLowString = request.getParameter("startElectrReadingLow");
        if (inputIsOK && Check.isInteger(startElectrReadingLowString)) {
            startElectrReadingLow = Integer.parseInt(startElectrReadingLowString); 
        } else {
            inputIsOK = false;
        }        
        int endElectrReadingLow = 0;
        String endElectrReadingLowString = request.getParameter("endElectrReadingLow");
        if (inputIsOK && Check.isInteger(endElectrReadingLowString)) {
            endElectrReadingLow = Integer.parseInt(endElectrReadingLowString); 
           } else {
            inputIsOK = false;
        }
        int startElectrReadingHigh = 0;
        String startElectrReadingHighString = request.getParameter("startElectrReadingHigh");
        if (inputIsOK && Check.isInteger(startElectrReadingHighString)) {
            startElectrReadingHigh = Integer.parseInt(startElectrReadingHighString); 
        } else {
            inputIsOK = false;
        }        
        int endElectrReadingHigh = 0;
        String endElectrReadingHighString = request.getParameter("endElectrReadingHigh");
        if (inputIsOK && Check.isInteger(endElectrReadingHighString)) {
            endElectrReadingHigh = Integer.parseInt(endElectrReadingHighString); 
           } else {
            inputIsOK = false;
        }
        Booking booking = BookingDB.selectBooking(bookingIndex);
        String message;
        String url = "/bookingDetails.jsp";
        if (inputIsOK) {
            booking.setStartElectrReadingLow(startElectrReadingLow);
            booking.setEndElectrReadingLow(endElectrReadingLow);
            booking.setStartElectrReadingHigh(startElectrReadingHigh);
            booking.setEndElectrReadingHigh(endElectrReadingHigh);
            message = "Booking aangepast";
        } else {
            message = "Gegevens niet correct, geen update";
        }
        
        request.setAttribute("booking", booking);
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
