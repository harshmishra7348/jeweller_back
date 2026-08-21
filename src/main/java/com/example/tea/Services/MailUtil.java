package com.example.tea.Services;

import com.example.tea.Model.Enquiry;
import com.example.tea.Model.EnquiryItem;
import com.example.tea.Model.InvoiceMST;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailUtil {
    @Autowired
    private JavaMailSender mailSender;

    /** Sends the generated invoice PDF to the customer's email as an attachment. */
    public void sendInvoicePdf(InvoiceMST invoice, byte[] pdfBytes, String to) throws MessagingException {
        if (to == null || to.trim().isEmpty()) {
            throw new MessagingException("Customer email is missing; cannot send invoice.");
        }
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Your Invoice " + invoice.getInvoiceNumber());
        helper.setText("Dear customer,\n\nPlease find your invoice attached.\n\nThank you for your business.", false);
        helper.addAttachment(invoice.getInvoiceNumber() + ".pdf", new ByteArrayResource(pdfBytes));
        mailSender.send(message);
    }

    /**
     * Emails a submitted inquiry (the whole cart) to the shop owner (admin). The reply-to is
     * set to the end customer so the owner can respond to them directly.
     */
    public void sendEnquiryToAdmin(Enquiry enquiry, List<EnquiryItem> items, String adminEmail) throws MessagingException {
        if (adminEmail == null || adminEmail.trim().isEmpty()) {
            throw new MessagingException("Admin email is not configured (app.company.email).");
        }
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false);
        helper.setTo(adminEmail);
        if (enquiry.getCustomerEmail() != null && !enquiry.getCustomerEmail().trim().isEmpty()) {
            helper.setReplyTo(enquiry.getCustomerEmail());
        }
        helper.setSubject("New product inquiry from " + nvl(enquiry.getCustomerName()));

        StringBuilder body = new StringBuilder();
        body.append("You have a new inquiry from your website.\n\n");
        body.append("Customer\n");
        body.append("  Name : ").append(nvl(enquiry.getCustomerName())).append("\n");
        body.append("  Email: ").append(nvl(enquiry.getCustomerEmail())).append("\n");
        body.append("  Phone: ").append(nvl(enquiry.getCustomerPhone())).append("\n\n");

        body.append("Products\n");
        if (items != null) {
            for (EnquiryItem item : items) {
                body.append("  - ").append(nvl(item.getItemName()))
                        .append(" (id ").append(item.getItemMSTId()).append(")")
                        .append("  x ").append(item.getQuantity()).append("\n");
            }
        }
        body.append("\nMessage:\n").append(nvl(enquiry.getMessage())).append("\n");
        helper.setText(body.toString(), false);
        mailSender.send(message);
    }

    private String nvl(String value) {
        return value != null ? value : "-";
    }
}
