package uta.cse.algo;

/**
 * Created by riby on 11/20/15.
 */
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;


@SpringBootApplication

public class Application {
/*
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
*/

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
