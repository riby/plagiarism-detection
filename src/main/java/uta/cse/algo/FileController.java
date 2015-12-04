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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

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
    @RequestMapping("/success")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "/folder_upload.html";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile  file, Model model){
        String name="";
        if (!file.isEmpty()) {
            try {


                name=file.getOriginalFilename();
                ArrayList<String> list = new ArrayList<String>();

                InputStream inputStream = file.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    System.out.println(line);
                    list.add(line);
                }
                repository.save(new FileModel(name, list));
                //greeting();
               return "redirect:/success";
                //return new ModelAndView("folder_upload.html");
            } catch (Exception e) {
               // return "You failed to upload " + name + " => " + e.getMessage();
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
           return "You failed to upload " + name + " because the file was empty.";
            //return "/folder_upload.html";
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
        //KMPRunFile km=new KMPRunFile(a1,listOfFiles,fnames);
        //km.run();
        //LcssModule lcss=new LcssModule(a1,listOfFiles,fnames);
        //lcss.execute();
        NaiveRunFile nr=new NaiveRunFile(a1,listOfFiles,fnames);
        nr.run();


        return "You can upload a file by posting to this same URL.";
    }

}
