package Captcha;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Random;

@Controller
@SessionAttributes("captchaText") 
public class CaptchaController {

    @GetMapping("/")
    public String showCaptchaForm(Model model) {
        // Generate a random CAPTCHA text and store it in the model
        String captchaText = generateRandomCaptchaText();
        model.addAttribute("captchaText", captchaText);
        return "captcha";
    }

    @PostMapping("/captcha/validate")
    public String validateCaptcha(@ModelAttribute("captchaResponse") String userCaptchaResponse,
                                  @ModelAttribute("captchaText") String storedCaptcha,
                                  SessionStatus sessionStatus) {

        if (userCaptchaResponse != null && storedCaptcha != null && userCaptchaResponse.equalsIgnoreCase(storedCaptcha)) {
           
            sessionStatus.setComplete();
            return "success";
        } else {
           
            return "error";
        }
    }
    @GetMapping("/tryAgain")
    public String tryAgain(SessionStatus sessionStatus) {
        
        sessionStatus.setComplete();
        return "redirect:/";
    }
    private String generateRandomCaptchaText() {
        
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captchaText = new StringBuilder();
        Random rnd = new Random();
        while (captchaText.length() < 5) {
            int index = (int) (rnd.nextFloat() * chars.length());
            captchaText.append(chars.charAt(index));
        }
        return captchaText.toString();
    }
}
