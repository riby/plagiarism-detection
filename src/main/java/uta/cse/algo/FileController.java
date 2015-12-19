package uta.cse.algo;

/**
 * Created by riby on 11/20/15.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class FileController {
    //Arraylist keeping the performance statistics
    public static ArrayList<PerformanceDetails> performanceDetailsList = new ArrayList<PerformanceDetails>();
    //Get the correct Algo from request
    Map<String, Integer> algo = new HashMap<String, Integer>();

    //Binding of mongodb datasource using spring.io annotations
    @Autowired
    private FileRepository repository;

    private ArrayList<String> listOfFilesNames=new ArrayList<String>();
    private String testFile=null;

    //Get request handler using upload

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    /* Upload directories and all files in it  to f analytics  */

    @RequestMapping(value="/upload_folders", method=RequestMethod.POST)
    public String UploadReceipts(@RequestParam("files[]") List<MultipartFile> file) throws Exception {
        algo.put("LCSS",1);
        algo.put("KMP",2);
        algo.put("NAIVE",3);
        algo.put("BOYER",4);

        listOfFilesNames.clear();

        for(int i=0; i< file.size(); i++)
        {
            if(!file.get(i).isEmpty())
            {
                MultipartFile cm = file.get(i);


                System.out.println(cm.getOriginalFilename()+cm.getSize());
                String name=cm.getOriginalFilename();
                listOfFilesNames.add(name);
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
        return "redirect:/success2";
    }

    @RequestMapping("/success2")
    public String greeting2(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "/algo_select.html";
    }
    @RequestMapping("/success")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "/folder_upload.html";
    }

    // Upload the file which needs to be evaluated
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile  file, Model model){
        String name="";
        testFile="";
        repository.deleteAll();
        if (!file.isEmpty()) {
            try {


                name=file.getOriginalFilename();
                testFile=name;
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

    // It takes input and process the algorithm selected.
    @RequestMapping(value="/process_files", method=RequestMethod.POST)
    public @ResponseBody String performMatching(@RequestParam("algo_option") String algoName,Model model) {
        List<FileModel> Listrepo= repository.findAll();

        for(FileModel fl :Listrepo)
            System.out.println(fl.getName());

        int option=algo.get(algoName);
        ArrayList<String> a1=repository.findByName(testFile).getLines();
        ArrayList<ArrayList<String>> listOfFiles=new ArrayList<ArrayList<String>>();

        String result="";
        int i=0;
        for( String s1: listOfFilesNames) {
            ArrayList<String> a2=repository.findByName(s1).getLines();
            listOfFiles.add(a2);
        }
        switch (option) {
            case 2:
                KMPRunFile km = new KMPRunFile(a1, listOfFiles, listOfFilesNames);
                result=km.run();
                break;
            case 1:
                LcssModule lcss=new LcssModule(a1,listOfFiles,listOfFilesNames);
                result=lcss.execute();
                break;
            case 3:
                NaiveRunFile nr=new NaiveRunFile(a1,listOfFiles,listOfFilesNames);
                result=nr.run();
                break;
            case 4:
                BoyerMooreRunFile bm = new BoyerMooreRunFile(a1, listOfFiles, listOfFilesNames);
                result = bm.run();
                break;

        }



      //  model.addAttribute("result", result);
        return result + "</br></br><a href='/success2'>Test More Algo</a></br><a href='/'>Start New Submission</a></div>";
    }
    @RequestMapping("/success3")
    public String greeting3(@RequestParam(value="result") String result, Model model) {
        model.addAttribute("result", result);
        return "/output.html";
    }
}
