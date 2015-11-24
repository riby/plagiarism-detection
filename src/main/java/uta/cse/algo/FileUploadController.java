package uta.cse.algo;

/**
 * Created by riby on 11/20/15.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    @Autowired
    private FileRepository repository;

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile  file){
       // FileRepository repository;


        if (!file.isEmpty()) {
            try {

                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("tmp")));
                stream.write(bytes);
                stream.close();
                File convFile = new File("tmp");
                Scanner s = new Scanner(convFile);
                ArrayList<String> list = new ArrayList<String>();

                String line;
                BufferedReader br = new BufferedReader(new FileReader(convFile));
                {
                    while ((line = br.readLine()) != null) {
                        list.add(line);

                    }
                }
                repository.save(new FileModel(name,list));
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

}
