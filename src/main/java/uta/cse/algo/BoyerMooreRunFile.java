package uta.cse.algo;

/**
 * Created by alokrai on 12/6/15.
 */

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoyerMooreRunFile implements FileRead {
    Map<String, Integer> matchingSentencesMap = new HashMap<String, Integer>();
    long totalTime;
    double plagarizedPercent;
    int totalCorpusSize;
    int patternFileSize;
    //@Autowired
    // private static FileRepository repository;
    private ArrayList<String> testFile;
    private ArrayList<ArrayList<String>> listOfFiles;
    private ArrayList<String> fname;

    /*
    The Constructer take as input the Text to compare, Text pattern and fname is the file names
    * which is being passed at runtime
    * */

    BoyerMooreRunFile(ArrayList<String> testFile, ArrayList<ArrayList<String>> listOfFiles, ArrayList<String> fname) {
        this.testFile = testFile;
        this.listOfFiles = listOfFiles;
        this.fname = fname;
    }

    public static boolean isBlankOrNull(String str) {
        return (str == null || "".equals(str.trim()));
    }

    public String run() {
        readFile();
        String result = "<div style='align:center'><h2>Algorithms runtime matches and results</h2><h3>With % Uniqueness</br></h3><p>";
        int i = 0;
        ArrayList<String> resultList = new ArrayList<String>();
        long startTime = System.currentTimeMillis();
        boolean response = false;
        for (ArrayList<String> al : listOfFiles) {

            for (String s1 : al) {
                for (String s2 : testFile) {
                    BoyerMoore k = new BoyerMoore(s1, s2, fname.get(i));
                    response = k.match();
                    if (response) {
                        System.out.println("String " + s1 + " matched in file" + fname.get(i));
                        result = result + "</br>String " + s1 + " matched in file" + fname.get(i);
                        if (matchingSentencesMap.containsKey(s2)) {
                            int value = matchingSentencesMap.get(s2);
                            matchingSentencesMap.put(s2, (value + 1));
                        }
                        matchingSentencesMap.put(s2, 1);
                    }
                }
            }
            i++;
        }
        //Time calculation of runtime

        long endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        int matchedSentences = matchingSentencesMap.size();
        int totalSentences = testFile.size();
        plagarizedPercent = (((double) matchedSentences) / totalSentences) * 100;

        // Total for algorithm

        System.out.println("Run time for BoyerMoore algorithm = " + totalTime);
        System.out.println("Document Plagarism Percentage = " + plagarizedPercent);
        result = result + "</br>Run time for BoyerMoore algorithm = " + totalTime;
        result = result + "</br>Document Plagarism Percentage = " + plagarizedPercent;

        FileController.performanceDetailsList.add(new PerformanceDetails("BOYER", plagarizedPercent, totalTime));
        //FileController.excelData.add(new ExcelFileData("KMP",totalCorpusSize,patternFileSize,totalTime));

        try {
            FileInputStream file = new FileInputStream(new File("TestExperimentExcel1.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheet("BOYER");
            int rowNum = sheet.getLastRowNum() + 1;
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell((short) 0).setCellValue(totalCorpusSize);
            row.createCell((short) 1).setCellValue(patternFileSize);
            row.createCell((short) 2).setCellValue(totalTime);
            file.close();
            FileOutputStream outFile = new FileOutputStream(new File("TestExperimentExcel1.xls"));
            workbook.write(outFile);
            outFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;

    }

    @Override
    public void readFile() {
        // To convert paragraphs into sentences in array list

        testFile = makeSentences(testFile);
        int sum = 0;// to find corpus size
        int i = 0;
        for (ArrayList<String> ar : listOfFiles) {
            listOfFiles.set(i++, makeSentences(ar));
            sum += listOfFiles.get(i - 1).size();     //to find corpus size

        }
        patternFileSize = testFile.size();
        totalCorpusSize = sum;


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
        for (String a : temp)
            System.out.println(a);
        return temp;
    }
}

