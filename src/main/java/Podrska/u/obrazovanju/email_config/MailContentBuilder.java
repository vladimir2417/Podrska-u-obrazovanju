package Podrska.u.obrazovanju.email_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
public class MailContentBuilder {
    private TemplateEngine templateEngine;
    @Autowired
    public  MailContentBuilder(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    public String build(String message){
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("email-template", context);
    }

    public String buildVaucer(String message){
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("email-template-vaucer", context);
    }

    public String buildPromo(String message, String nazivUstanove, String opisUstanove){
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("nazivUstanove", nazivUstanove);
        context.setVariable("opisUstanove", opisUstanove);
        return templateEngine.process("email-template-promo", context);
    }
    public String buildObilazak(String message, String nazivUstanove, String opisUstanove, String datum, String vreme){
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("nazivUstanove", nazivUstanove);
        context.setVariable("opisUstanove", opisUstanove);
        context.setVariable("datum", datum);
        context.setVariable("vreme", vreme);
        return templateEngine.process("email-template-odobren-obilazak", context);
    }


}
