package uta.cse.algo;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LcssModule {

	private ArrayList<String> suspectFile;
	private ArrayList<ArrayList<String>> listOfFiles;
	private ArrayList<String> fileNames;
	Map<String,Integer> matchingParagraphsMap = new HashMap<String, Integer>();
	long totalTime;
	double plagarizedPercent;
	int totalCorpusSize;
	int patternFileSize;
	
	//constructor
	LcssModule(ArrayList<String> suspectFile, ArrayList<ArrayList<String>> listOfFiles, ArrayList<String> fileNames) {
		this.suspectFile = suspectFile;
		this.listOfFiles = listOfFiles;
		this.fileNames = fileNames;

	}
	
	String execute() {
		
		LCSS lcss = new LCSS(60);
		boolean result;
		int i=0;
		ArrayList<String> resultList=new ArrayList<String>();
		long startTime = System.currentTimeMillis();
		String resultFin="<div style='align:center'><h2>Algorithms runtime matches and results</h2><h3>With % Uniqueness</br></h3><p>";
		for(String suspectPara : suspectFile) {
			i=0;
			//check if this suspect paragraph is found anywhere in other files
			for( ArrayList<String> eachFile : listOfFiles) {


				for(String originalPara : eachFile){
					result = lcss.compute(originalPara, suspectPara);
					
					//if lcss verified it as a plagiarized paragraph, log it
					if(result)
                    {
                        System.out.println();
						resultFin=resultFin+"</br> String "+ suspectPara + " matched in with 60% Threshhold in paragraph " + originalPara + " in file " + fileNames.get(i);

						if(matchingParagraphsMap.containsKey(suspectPara)){
							int value = matchingParagraphsMap.get(suspectPara);
							matchingParagraphsMap.put(suspectPara,(value+1));
						}
						matchingParagraphsMap.put(suspectPara, 1);
                    }
				}
                i++;
			}

		}
		long endTime = System.currentTimeMillis();
		totalTime =  endTime-startTime;
		int matchedSentences = matchingParagraphsMap.size();
		int totalSentences = suspectFile.size();

		plagarizedPercent = (((double) matchedSentences)/totalSentences)*100;

		System.out.println("Run time for LCSS algorithm = "+totalTime);
		System.out.println("Document Plagarism Percentage = " + plagarizedPercent);
		resultFin=resultFin+"</br>Run time for LCSS algorithm = "+totalTime;
		resultFin=resultFin+"</br>Document Plagarism Percentage = " + plagarizedPercent;
		FileController.performanceDetailsList.add(new PerformanceDetails("LCSS",plagarizedPercent,totalTime));
		//FileController.excelData.add(new ExcelFileData("KMP",totalCorpusSize,patternFileSize,totalTime));
		int sum = 0;
		for(ArrayList<String> al : listOfFiles){
			sum += al.size();
		}
		try{
			FileInputStream file = new FileInputStream(new File("TestExperimentExcel1.xls"));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheet("LCSS");
			int rowNum = sheet.getLastRowNum() + 1;
			HSSFRow row = sheet.createRow(rowNum);
			row.createCell((short) 0).setCellValue(sum);
			row.createCell((short)1).setCellValue(suspectFile.size());
			row.createCell((short)2).setCellValue(totalTime);
			file.close();
			FileOutputStream outFile =new FileOutputStream(new File("TestExperimentExcel1.xls"));
			workbook.write(outFile);
			outFile.close();
		}
		catch(Exception e){
			System.out.println(e);
		}

		return resultFin;
	}
}