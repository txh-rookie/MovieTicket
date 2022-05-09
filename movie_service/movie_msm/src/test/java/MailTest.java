import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;

import javax.annotation.Resource;
import javax.mail.Message;
import java.util.Arrays;
import java.util.Random;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/5
 */
@SpringBootTest
public class MailTest {
    @Resource
    private JavaMailSender mailSender;
   @Test
    public void mail(){

   }
}
