package uta.cse.algo;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by riby on 11/22/15.
 */
public class KMPRunFile extends Thread implements  FileRead  {
    //@Autowired
   // private static FileRepository repository;
    private ArrayList<String> testFile;
    private ArrayList<ArrayList<String>> listOfFiles;
    private ArrayList<String> Fnames;

    KMPRunFile(ArrayList<String> testFile, ArrayList<ArrayList<String>> listOfFiles, ArrayList<String> Fnames )
    {
        this.testFile=testFile;
        this.listOfFiles=listOfFiles;
        this.Fnames=Fnames;

    }

    public void run()
    {
        readFile();
        int i=0;

        for(ArrayList<String> al:listOfFiles)
        {

            for(String s1: al)
            {
                for(String s2:testFile)
                {
                    Kmp k=new Kmp(s1.split(""),s2.split(""),Fnames.get(i));
                    k.run();
                }
            }
            i++;
        }

       //List<FileModel> Listrepo= repository.findAll();
        String [] T={"a","b","c","a","b","a","b","a","g","c"};
        String [] P={"a","b","a","b","a","g","c"};
        String t1="abcababagc";
        String p1="ababagc";
     // Kmp k=new Kmp(t1.split(""),p1.split(""));
        //System.out.print(t1.split("")[0]);
       // Kmp k=new Kmp(T,P);

     //   k.run();

        //Kmp k=new Kmp(t1.split(""),p1.split(""));

     //   System.out.println(Listrepo.size());
    }

    @Override
    public void readFile() {
        // To convert paragraphs into sentences in array list

       testFile=makeSentences(testFile);
        int i=0;
        for (ArrayList<String> ar: listOfFiles)
        {
            listOfFiles.set(i++, makeSentences(ar));

        }


    }
    public ArrayList<String> makeSentences(ArrayList<String> file) {
        ArrayList<String> temp = new ArrayList<String>();

        for (String s1 : file) {
            String[] str;
            if (s1.contains(".")) {
                str = s1.split("\\.");
                for (String s0 : str)
                    temp.add(s0.trim());
            } else
                temp.add(s1);

        }
        for(String a:temp)
    System.out.println(a);
        return temp;
    }
}

