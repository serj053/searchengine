package searchengine.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class TestP {
    @Value("${someParameter}")
    private Integer param;
    public void run(){
        System.out.println("param "+ param);
    }

    public static void main(String[] args) {
        System.out.println("Control");
        TestP test = new TestP();
        test.run();

    }
}
