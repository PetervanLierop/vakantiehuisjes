/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bookings;

import business.Accommodation;
import business.Booking;
import data.BookingDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "ReturnBookingTableAsExcel", urlPatterns = {"/ReturnBookingTableAsExcel"})
public class ReturnBookingTableAsExcel extends HttpServlet {

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
        Accommodation accommodation = (Accommodation) session.getAttribute("currentAccommodation");
        int accomodationId = accommodation.getId();
        List<Booking> bookings = BookingDB.selectAllBookings(accomodationId);
        String d = "\t";
        StringBuilder report = new StringBuilder("De Boekingtabel\n\n"
                + "Huurder" + d
                + "Accommodatie" + d
                + "Start Datum" + d
                + "Eind Datum" + "\n"
        );
        for (Booking booking : bookings) {
            report.append(booking.getRenter().getFirstName() + " "
                    + booking.getRenter().getLastName() + d
                    + booking.getAccommodation().getName() + d
                    + booking.getStartDate() + d
                    + booking.getEndDate() + "\n");
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("cache-control", "no-cache");

        PrintWriter out = response.getWriter();
        out.println(report);
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
