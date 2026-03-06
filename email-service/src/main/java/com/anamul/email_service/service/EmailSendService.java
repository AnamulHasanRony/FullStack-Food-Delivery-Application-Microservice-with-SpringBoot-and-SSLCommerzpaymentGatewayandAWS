package com.anamul.email_service.service;

import com.anamul.email_service.io.OrderItem;
import com.anamul.email_service.io.OrderResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmailForPaymentConfirmation(OrderResponse order) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);


        helper.setTo("anamul31@gmail.com");
        helper.setSubject("Food Order Payment Confirmation - Order #" + order.getId());

        StringBuilder body = new StringBuilder();
        body.append("<h3>Hello ").append(order.getCustomerName()).append(",</h3>");
        body.append("<p>Thank you for your payment. Your food order has been successfully!</p>");
        body.append("<h4>Ordered Food Items:</h4>");

        for (OrderItem item : order.getOrderedItem()) {

            body.append("<p>")
                    .append(item.getName())
                    .append(" | Qty: ")
                    .append(item.getQuantity())
                    .append(" | Price: Tk. ")
                    .append(item.getPrice()*item.getQuantity())
                    .append("</p>");
        }
        body.append("<ul>");
        body.append("<li><b>Order ID:</b> ").append(order.getId()).append("</li>");
        body.append("<li><b>Total Amount:</b> Tk. ").append(order.getTotalAmount()).append("</li>");
        body.append("<li><b>Payment Status:</b> ").append(order.getPaymentStatus()).append("</li>");
        body.append("<li><b>Order Status: Pending</b> ").append("</li>");
        body.append("</ul>");

        body.append("<h4>Feed Delivery Address Information:</h4>");
        body.append("<ul>");
        body.append("<li><b>Address:</b> ").append(order.getCustomerAddress()).append("</li>");
        body.append("<li><b>City:</b> ").append(order.getCustomerCity()).append("</li>");
        body.append("<li><b>Division:</b> ").append(order.getCustomerState()).append("</li>");
        body.append("<li><b>Country:</b> ").append(order.getCustomerCountry()).append("</li>");
        body.append("<li><b>Phone No:</b> ").append(order.getCustomerPhoneNo()).append("</li>");
        body.append("</ul>");

        body.append("<p>We will notify you once your food is ready.</p>");
        body.append("<p>Thank you for ordering with us.</p><br><br>");
        body.append("<p>Regards,</p>");
        body.append("<p>Food Delivery Application 🍔<p/>");

        helper.setText(body.toString(), true);

        mailSender.send(message);
        System.out.println("Payment confirmation email sent to " + "anamul31@gmail.com");



    }


}
