package pl.edu.agh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.edu.agh.repository.loans.LoanRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private final LoanRepository loanRepository;

    private List<String> emailsAndBooks;

    @Autowired
    public EmailService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@biblioteka.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println("Error while sending email to: " + to);
            System.out.println(e);
        }
    }

    public List<String> getEmailsForEmailNotifications(Date endDate) {
        return loanRepository.findEmailsAndBooksForEmailNotification(endDate);
    }

    public List<String> getEmailsForNewsLetter() {
        return loanRepository.findEmailsForNewsLetter();
    }

    public void sendEmailsNotification() {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date currentDatePlusTwoDays = calendar.getTime();

        System.out.println(currentDatePlusTwoDays);
        emailsAndBooks = getEmailsForEmailNotifications(currentDatePlusTwoDays);
        System.out.println(emailsAndBooks);
        for (String emailAndBook : emailsAndBooks) {
            emailAndBook = emailAndBook.replaceFirst(",", "#");
            String email = emailAndBook.split("#")[0];
            String book = emailAndBook.split("#")[1];

            this.sendEmail(email, "Przypomnienie o zwrocie książki",
                    "Przypominamy o zwrocie książki: " + book + ", pozostały Ci już tylko 2 dni.");
        }
    }

    public void sendNewsLetter(String subject, String text) {
        List<String> emails = getEmailsForNewsLetter();
        for (String email : emails) {
            this.sendEmail(email, subject, text);
        }
    }
}
