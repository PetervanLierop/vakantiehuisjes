/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bookings;

import business.Accommodation;
import business.Booking;
import business.Price;
import business.RentalRates;
import business.Renter;
import data.BookingDB;
import data.PriceDB;
import data.UserDB;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "ProcessAddBooking", urlPatterns = {"/ProcessAddBooking"})
public class ProcessAddBooking extends HttpServlet {

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
        String renterSelection = request.getParameter("renterSelection");
        int renterId = Integer.parseInt(renterSelection);
        String startDateString = request.getParameter("startDate");
        String endDateString = request.getParameter("endDate");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date startDateUtil = null;
        java.util.Date endDateUtil = null;
        try {
            startDateUtil = df.parse(startDateString);
            endDateUtil = df.parse(endDateString);
        } catch (ParseException ex) {
            Logger.getLogger(ProcessAddBooking.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date startDate = new java.sql.Date(startDateUtil.getTime());
        java.sql.Date endDate = new java.sql.Date(endDateUtil.getTime());
        Accommodation currentAccommodation =
                (Accommodation) request.getSession().getAttribute("currentAccommodation");
        String message;
        String url;
        if (currentAccommodation.isAvailable(startDate, endDate)) {
            Renter renter = UserDB.selectRenter(renterId);
            currentAccommodation.addBooking(renter, startDate, endDate);
            message = "Booking toegevoegd";
            url = "/CollectBookings";
        } else {
            message = "Kies een andere periode, deze is helaas niet beschikbaar";
            url = "/addBooking.jsp";
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
