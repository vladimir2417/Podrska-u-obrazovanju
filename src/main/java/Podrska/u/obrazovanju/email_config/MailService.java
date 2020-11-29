package Podrska.u.obrazovanju.email_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailContentBuilder mailContentBuilder;

    public void pripremiIPosaljiNoviKorisnik( String from, String recipient, String subject, String message ){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(message);
            String content = mailContentBuilder.build(message);

            String slika1 = "D:/FAKS/ZAVRSNI RAD/PodrskaUObrazovanju/src/main/resources/static/imagesMail/img1.jpg";
            String slika2 = "D:/FAKS/ZAVRSNI RAD/PodrskaUObrazovanju/src/main/resources/static/imagesMail/img2.jpg";
            String slika3 = "D:/FAKS/ZAVRSNI RAD/PodrskaUObrazovanju/src/main/resources/static/imagesMail/img3.jpg";

            FileSystemResource res1 = new FileSystemResource(new File(slika1));
            FileSystemResource res2 = new FileSystemResource(new File(slika2));
            FileSystemResource res3 = new FileSystemResource(new File(slika3));
            messageHelper.setText(content, true );

            messageHelper.addInline("slika1", res1);
            messageHelper.addInline("slika2", res2);
            messageHelper.addInline("slika3", res3);
        };
        try {
            mailSender.send(messagePreparator );
        }catch (MailException e){
            System.out.println("Greska u slanju mejla!"+e);
        }
    }

    public void pripremiIPosaljiSubscribe( String from, String recipient, String subject, String message,String nazivUstanove,String opisUstanove, String putanjaLogo ){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(message);
            String content = mailContentBuilder.buildPromo(message, nazivUstanove, opisUstanove);

            String slika1 = "D:/FAKS/ZAVRSNI RAD/PodrskaUObrazovanju/src/main/resources/static/imagesMail/img1.jpg";
            String slika3 = "D:/FAKS/ZAVRSNI RAD/PodrskaUObrazovanju/src/main/resources/static/imagesMail/img3.jpg";
            String logoUstanove = "D:/FAKS/ZAVRSNI RAD/PodrskaUObrazovanju/src/main"+putanjaLogo.replaceFirst("..", "");

            FileSystemResource res1 = new FileSystemResource(new File(slika1));
            FileSystemResource res3 = new FileSystemResource(new File(slika3));
            FileSystemResource res4 = new FileSystemResource(new File(logoUstanove));
            messageHelper.setText(content, true );

            messageHelper.addInline("slika1", res1);
            messageHelper.addInline("slika3", res3);
            messageHelper.addInline("logoUstanove", res4);
        };
        try {
            mailSender.send(messagePreparator );
        }catch (MailException e){
            System.out.println("Greska u slanju mejla!"+e);
        }
    }

    public void pripremiIPosaljiVaucer( String from, String recipient, String subject, String message, String putanjaBarcode ){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(message);
            String content = mailContentBuilder.buildVaucer(message);

            String slika1 = "D:/FAKS/ZAVRSNI RAD/PodrskaUObrazovanju/src/main/resources/static/imagesMail/img1.jpg";
            String barcode = putanjaBarcode;
            String slika3 = "D:/FAKS/ZAVRSNI RAD/PodrskaUObrazovanju/src/main/resources/static/imagesMail/img3.jpg";

            FileSystemResource res1 = new FileSystemResource(new File(slika1));
            FileSystemResource res2 = new FileSystemResource(new File(barcode));
            FileSystemResource res3 = new FileSystemResource(new File(slika3));
            messageHelper.setText(content, true );

            messageHelper.addInline("slika1", res1);
            messageHelper.addInline("barcode", res2);
            messageHelper.addInline("slika3", res3);
        };
        try {
            mailSender.send(messagePreparator );
        }catch (MailException e){
            System.out.println("Greska u slanju mejla!"+e);
        }
    }

    public void pripremiIPosaljiObilazak( String from, String recipient, String subject, String message,String nazivUstanove,String opisUstanove, String datum, String vreme, String putanjaLogo ){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(message);
            String content = mailContentBuilder.buildObilazak(message, nazivUstanove, opisUstanove, datum, vreme);

            String slika1 = "D:/FAKS/ZAVRSNI RAD/PodrskaUObrazovanju/src/main/resources/static/imagesMail/img1.jpg";
            String slika3 = "D:/FAKS/ZAVRSNI RAD/PodrskaUObrazovanju/src/main/resources/static/imagesMail/img3.jpg";
            String logoUstanove = "D:/FAKS/ZAVRSNI RAD/PodrskaUObrazovanju/src/main"+putanjaLogo.replaceFirst("..", "");

            FileSystemResource res1 = new FileSystemResource(new File(slika1));
            FileSystemResource res3 = new FileSystemResource(new File(slika3));
            FileSystemResource res4 = new FileSystemResource(new File(logoUstanove));
            messageHelper.setText(content, true );

            messageHelper.addInline("slika1", res1);
            messageHelper.addInline("slika3", res3);
            messageHelper.addInline("logoUstanove", res4);
        };
        try {
            mailSender.send(messagePreparator );
        }catch (MailException e){
            System.out.println("Greska u slanju mejla!"+e);
        }
    }
}
