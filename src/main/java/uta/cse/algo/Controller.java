package uta.cse.algo;

/**
 * Created by riby on 11/20/15.
 */
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;


@SpringBootApplication

public class Controller {
/*
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
*/

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Controller.class, args);
    }
}
