/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

import business.Security;
import business.User;
import business.UserRole;
import data.UserDB;
import data.UserRoleDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
@WebServlet(name = "ProcessAddUser", urlPatterns = {"/ProcessAddUser"})
public class ProcessAddUser extends HttpServlet {

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
        String url = "/AddUser";
        int userRoleId;
        UserRole userRole = null;;
        boolean inputIsOK = true;
        String inlogName = request.getParameter("inlogName");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String emailAddress = request.getParameter("emailAddress");
        String userRoleIdString = request.getParameter("userRoleId");
        boolean administrator = (request.getParameter("administrator") != null);
        if (Check.isInteger(userRoleIdString)) {
            userRoleId = Integer.parseInt(userRoleIdString);
            userRole = UserRoleDB.selectUserRole(userRoleId);
        } else {
            message = "Gebruiker niet aangemaakt, zoek hulp..";
            inputIsOK = false;
        }
        if (UserDB.inlogNameExists(inlogName)) {
            message = "Inlognaam is al in gebruik, kies een andere.";
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("emailAddress", emailAddress);
            inputIsOK = false;
        }
        if (inputIsOK) {
            Security security = new Security();
            String defaultPassword = security.setPassword();
            boolean isTemporary = true;
            security.setExpirationDate(isTemporary);
            User user = new User(
                    inlogName,
                    firstName,
                    lastName,
                    emailAddress,
                    security,
                    administrator,
                    userRole);
            user = UserDB.insertUser(user);
            message = "Gebruiker toegevoeg";
            Properties props = new Properties();
            props.put("mail.smtp.host", "localhost");
            Session session = Session.getDefaultInstance(props);
            Message mailMessage = new MimeMessage(session);
            try {
                mailMessage.setSubject("Welkom bij Vakantiehuisje");
                mailMessage.setText("Uw wachtwoord is: " + defaultPassword + ".");
                Address fromAddress = new InternetAddress("postmaster@localhost");
                Address toAddress = new InternetAddress(user.getEmailAddress());
                mailMessage.setFrom(fromAddress);
                mailMessage.setRecipient(Message.RecipientType.TO, toAddress);
                mailMessage.setContent(
                        "welkom bij vakantiehuisje."
                        + "Uw wachtwoord is: " + defaultPassword + ".",
                        "text/html");

                Transport.send(mailMessage);
            } catch (MessagingException ex) {
                PrintWriter out = response.getWriter();
                out.println(ex.getMessage() + "\n"
                        + ex.getCause() + " XQ");
                ex.printStackTrace();
                return;
            }
            message = "Er is een welkom e-mail gezonden naar de "
                    + " nieuwe gebruiker: "
                    + user.getFirstName() + " " + user.getLastName();
            url = "/collectUsers";
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
