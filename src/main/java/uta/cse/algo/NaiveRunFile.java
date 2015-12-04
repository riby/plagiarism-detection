package uta.cse.algo;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NaiveRunFile  implements  FileRead  {
    //@Autowired
   // private static FileRepository repository;
    private ArrayList<String> testFile;
    private ArrayList<ArrayList<String>> listOfFiles;
    private ArrayList<String> Fnames;
    Map<String,Integer> matchingSentencesMap = new HashMap<String, Integer>();
    long totalTime;
    double plagarizedPercent;
    int totalCorpusSize;
    int patternFileSize;

    NaiveRunFile(ArrayList<String> testFile, ArrayList<ArrayList<String>> listOfFiles, ArrayList<String> Fnames )
    {
        this.testFile=testFile;
        this.listOfFiles=listOfFiles;
        this.Fnames=Fnames;

    }

    public String run()
    {
        readFile();
        int i=0;
        ArrayList<String> resultList=new ArrayList<String>();
        long startTime = System.currentTimeMillis();
        String result="<div style='align:center'><h2>Algorithms runtime matches and results</h2><h3>With % Uniqueness</br></h3><p>";
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
                        result=result+"</br>String "+s1 +" matched in file"+Fnames.get(i);

                        if(matchingSentencesMap.containsKey(s2)){
                            int value = matchingSentencesMap.get(s2);
                            matchingSentencesMap.put(s2,(value+1));
                        }
                        matchingSentencesMap.put(s2, 1);
                    }

                }
            }
            i++;
        }
        long endTime = System.currentTimeMillis();
        totalTime =  endTime-startTime;
        int matchedSentences = matchingSentencesMap.size();
        int totalSentences = testFile.size();

        plagarizedPercent = (((double) matchedSentences)/totalSentences)*100;

        System.out.println("Run time for Naive algorithm = "+totalTime);
        System.out.println("Document Plagarism Percentage = " + plagarizedPercent);
        result=result+"</br>Run time for LCSS algorithm = "+totalTime;
        result=result+"</br>Document Plagarism Percentage = " + plagarizedPercent;

        FileController.performanceDetailsList.add(new PerformanceDetails("NAIVE",plagarizedPercent,totalTime));
        //FileController.excelData.add(new ExcelFileData("KMP",totalCorpusSize,patternFileSize,totalTime));

        try{
            FileInputStream file = new FileInputStream(new File("TestExperimentExcel1.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheet("Naive");
            int rowNum = sheet.getLastRowNum() + 1;
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell((short) 0).setCellValue(totalCorpusSize);
            row.createCell((short)1).setCellValue(patternFileSize);
            row.createCell((short)2).setCellValue(totalTime);
            file.close();
            FileOutputStream outFile =new FileOutputStream(new File("TestExperimentExcel1.xls"));
            workbook.write(outFile);
            outFile.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return result;
    }

    @Override
    public void readFile() {
        // To convert paragraphs into sentences in array list

       testFile=makeSentences(testFile);
        int sum =0;
        int i=0;
        for (ArrayList<String> ar: listOfFiles)
        {
            listOfFiles.set(i++, makeSentences(ar));
            sum += listOfFiles.get(i-1).size();
        }
        patternFileSize = testFile.size();
        totalCorpusSize = sum;


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

