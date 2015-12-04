package uta.cse.algo;

import java.util.ArrayList;

public class LcssModule {

	private ArrayList<String> suspectFile;
	private ArrayList<ArrayList<String>> listOfFiles;
	private ArrayList<String> fileNames;
	
	//constructor
	LcssModule(ArrayList<String> suspectFile, ArrayList<ArrayList<String>> listOfFiles, ArrayList<String> fileNames) {
		this.suspectFile = suspectFile;
		this.listOfFiles = listOfFiles;
		this.fileNames = fileNames;
	}
	
	void execute() {
		
		LCSS lcss = new LCSS(60);
		boolean result;
		int i=0;
		for(String suspectPara : suspectFile) {
			i=0;
			//check if this suspect paragraph is found anywhere in other files
			for( ArrayList<String> eachFile : listOfFiles) {


				for(String originalPara : eachFile){
					result = lcss.compute(originalPara, suspectPara);
					
					//if lcss verified it as a plagiarized paragraph, log it
					if(result)
                    {
                        System.out.println("String "+suspectPara +" matched in with 60% Threshhold in paragraph "+originalPara+" in file "+fileNames.get(i));
                    }
				}
                i++;
			}
		}
	}
}