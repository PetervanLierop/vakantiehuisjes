/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import business.Security;
import business.User;
import data.UserDB;
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

/**
 *
 * @author Peter
 */
@WebServlet(name = "RequestNewPassword", urlPatterns = {"/RequestNewPassword"})
public class RequestNewPassword extends HttpServlet {

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
        String inlogName = request.getParameter("inLogName");
        PasswordEncryptionService pes = new PasswordEncryptionService();
        User currentUser = UserDB.selectUser(inlogName);
        if (currentUser.getSecurity() == null) {
            currentUser.setSecurity(new Security());        }
        String message = null;
        String url = null;
        if (currentUser != null)
        {
            String defaultPassword = currentUser.getSecurity().setPassword();
            boolean isTemporary = true;
            currentUser.getSecurity().setExpirationDate(isTemporary);
            UserDB.updateSecurity(currentUser.getSecurity());
        // 1 - get a mail session
            Properties props = new Properties();
//        props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", "localhost");
//        props.put("mail.smtp.port", 25);
//        props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props);
//        session.setDebug(true);
        
        // 2 - create a message
            Message mailMessage = new MimeMessage(session);
            try {
                mailMessage.setSubject("nieuw wachtwoord voor Vakantiehuisje");
                mailMessage.setText("Uw nieuwe wachtwoord is" + defaultPassword + ".");

        // 3 - address the message
                Address fromAddress = new InternetAddress("postmaster@localhost");
//            Address fromAddress = new InternetAddress("pg.van.lierop@hetnet.nl");
                Address toAddress = new InternetAddress(currentUser.getEmailAddress());
                mailMessage.setFrom(fromAddress);
                mailMessage.setRecipient(Message.RecipientType.TO, toAddress);
                mailMessage.setContent("Uw nieuwe wachtwoord is: " + defaultPassword + ".",
                    "text/html");

        // 4 - send the message
                Transport.send(mailMessage);
//            Transport transport = session.getTransport("smtp");
//            transport.connect("v@nlierop.nl", "cft67ygv");
//            transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
//            transport.close();
            } catch (MessagingException ex) {
                PrintWriter out = response.getWriter();
                out.println(ex.getMessage() + "\n"
                          + ex.getCause() + " XQ");
                ex.printStackTrace();
                return;
            }
            message = "Het nieuwe wachtwoord is"
                    + " naar uw e-mailadres verzonden.";
            url = "/index.jsp";
            request.setAttribute("currentUser", currentUser);
        } else {
            message ="Naam onbekend.";
            url = "/passwordForgotten.jsp";           
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
