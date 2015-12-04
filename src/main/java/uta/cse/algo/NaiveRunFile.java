package uta.cse.algo;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class NaiveRunFile extends Thread implements  FileRead  {
    //@Autowired
   // private static FileRepository repository;
    private ArrayList<String> testFile;
    private ArrayList<ArrayList<String>> listOfFiles;
    private ArrayList<String> Fnames;

    NaiveRunFile(ArrayList<String> testFile, ArrayList<ArrayList<String>> listOfFiles, ArrayList<String> Fnames )
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
                    NaiveStringMatch nm=new NaiveStringMatch(s1.split(""),s2.split(""),Fnames.get(i));
                    boolean response =nm.run();
                    if(response)
                    {
                        System.out.println("String "+s1 +" matched in file"+Fnames.get(i));
                    }
                }
            }
            i++;
        }      
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
    public static boolean isBlankOrNull(String str) {
        return (str == null || "".equals(str.trim()));
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

