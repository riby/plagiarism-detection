package uta.cse.algo;


public class LCSS {

	int threshold; 
	
	LCSS(int threshold){
		this.threshold = threshold;
	}
	
	boolean compute(String originalPara, String suspectPara) {
		
		//convert both paragraphs to lower case
		originalPara = originalPara.toLowerCase();
		suspectPara = suspectPara.toLowerCase();
		
		int length1 = suspectPara.length();
		int length2 = originalPara.length();
		
		int[][] opt = new int[length1+1][length2+1]; //2D matrix to store DP operations
		
		//compute the length of LCSS via dynamic programming
		for(int i = length1-1; i >= 0; i--) {
			for(int j = length2-1; j >= 0; j--) {
				if(suspectPara.charAt(i) == originalPara.charAt(j))
					opt[i][j] = opt[i+1][j+1] + 1;
				else
					opt[i][j] = Math.max(opt[i+1][j], opt[i][j+1]);
			}
		}

		
		double levelOfSuspicion = ((double)opt[0][0] / length1) * 100;
		System.out.println("\nLevel of suspicison: " + levelOfSuspicion);
		
		//if level of suspicion for plagiarism is above the set threshold, return true else not
		return levelOfSuspicion > threshold;
	}
}
