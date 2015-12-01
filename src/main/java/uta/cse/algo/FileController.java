package uta.cse.algo;

/**
 * Created by riby on 11/20/15.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@SpringBootApplication
public class FileController {
    @Autowired
    private FileRepository repository;

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value="/upload_folders", method=RequestMethod.POST)
    public @ResponseBody String UploadReceipts(@RequestParam("files[]") List<MultipartFile> file) throws Exception {

        for(int i=0; i< file.size(); i++)
        {
            if(!file.get(i).isEmpty())
            {
                MultipartFile cm = (MultipartFile) file.get(i);


                System.out.println(cm.getOriginalFilename()+cm.getSize());
                String name=cm.getOriginalFilename();
                ArrayList<String> list = new ArrayList<String>();

                InputStream inputStream = cm.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    System.out.println(line);
                    list.add(line);
                }
                repository.save(new FileModel(name,list));


            }
        }
        return "uploadfilesuccess";
    }
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile  file){
        if (!file.isEmpty()) {
            try {

                String Fname=file.getOriginalFilename();
                ArrayList<String> list = new ArrayList<String>();

                InputStream inputStream = file.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    System.out.println(line);
                    list.add(line);
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

    public List<FileModel> getAllFiles()
    {
        return repository.findAll();

    }
    @RequestMapping(value="/process_files", method=RequestMethod.GET)
    public @ResponseBody String performMatching() {
        List<FileModel> Listrepo= repository.findAll();
        String [] T={"a","b","c","a","b","a","b","a","g","c"};
        String [] P={"a","b","a","b","a","g","c"};

       // System.out.println(Listrepo);
        for(FileModel fl :Listrepo)
            System.out.println(fl.getName());

        System.out.println(repository.findByName("asd1").getLines());

        ArrayList<String> a1=repository.findByName("text4.txt").getLines();
        ArrayList<ArrayList<String>> listOfFiles=new ArrayList<ArrayList<String>>();
        ArrayList<String> fnames=new ArrayList<String>();
        fnames.add("test1.txt");
        fnames.add("test2.txt");
        fnames.add("test3.txt");
        int i=0;
        for( String s1: fnames) {
            ArrayList<String> a2=repository.findByName(s1).getLines();
            listOfFiles.add(a2);
        }
        //ArrayList<String> a2=repository.findByName("asd").getLines();
        System.out.println(a1);
       // System.out.println(a2);
        KMPRunFile km=new KMPRunFile(a1,listOfFiles,fnames);
        km.run();
     /*   for( String s1 : a1)
        {
            for(String s2:a2)
            {
                System.out.println(s1+" with "+s2);


                Kmp k=new Kmp(s2.split(""),s1.split(""));
                k.run();
            }
        }*/
       // Kmp k=new Kmp(T,P);
        //k.run();

      //  Interator
//        for (FileModel fl )


        return "You can upload a file by posting to this same URL.";
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(FileController.class, args);
    }
}
