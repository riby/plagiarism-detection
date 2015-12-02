package uta.cse.algo;

/**
 * Created by ADMIN on 02-12-2015.
 */

import java.io.*;
import java.util.*;

public class NaiveMatch {
    public static Map<String, Integer> trackSentenceMatching = new HashMap<String,Integer>();
    private String[] T;
    private String[] P;
    private String fname;

    public NaiveMatch(String[] T, String[] P, String fname){
        this.T = T;
        this.P = P;
        this.fname = fname;
    }
    public static void main(String[] args)
    {
        //matchString("cocacocacolacocacolacocacocacocacola","cocacola");
    }


	/*public void run(){

	}*/

	/*public static void matchString(String text, String pattern)
	{
		int textLength = text.length();
		int patternLength = pattern.length();
		for(int s=0; s <= (textLength - patternLength); s++)
		{
			boolean found = true;
			for(int i=0; i<patternLength; i++)
			{
				if(!(pattern.charAt(i) == text.charAt(s+i)))
				{
					found = false;
					break;
				}
			}
			if(found)
			{
				System.out.println("pattern occurs at "+ s);
			}
		}
	}*/

    public void run()
    {
        //int textLength = text.length();
        //int patternLength = pattern.length();
        for(int s=0; s <= (T.length - P.length); s++)
        {
            boolean found = true;
            for(int i=0; i<P.length; i++)
            {
                if(!(P[i].equals(T[s+i])))
                {
                    found = false;
                    break;
                }
            }
            if(found)
            {
                System.out.println("pattern occurs at "+ s);
            }
        }
    }
    public static void matchStringFile(File directory, File patternFile)
    {
        File corpusFiles[] = directory.listFiles();

        for(int i=0; i<corpusFiles.length; i++){
            checkPattern(patternFile,corpusFiles[i]);
        }

    }

    public static void checkPattern(File testFile, File patternFile){
        ArrayList<String> testFileSentences = getSentences(testFile);
        ArrayList<String> patternFileSentences = getSentences(patternFile);
        for(int i =0; i<patternFileSentences.size();i++){
            String patternSentence = patternFileSentences.get(i);
            for(int j =0; j<testFileSentences.size(); j++){
                String testSentence = testFileSentences.get(j);
                for(int k =0; k < (testSentence.length() - patternSentence.length()); k++){
                    boolean found = true;
                    for(int l=0; l<patternSentence.length(); l++)
                    {
                        if(!(patternSentence.charAt(l) == testSentence.charAt(k+l)))
                        {
                            found = false;
                            break;
                        }
                    }
                    if(found)
                    {
                        System.out.println("sentence "+ patternSentence + " of pattern file is same as sentence number "
                                + j +" which is "+testSentence +" found in test file "+ testFile.getName());
                    }
                }
            }
        }
    }

    public static ArrayList<String> getSentences(File f){
        ArrayList<String> sentencesList = new ArrayList<String>();
        String getLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            while((getLine = br.readLine())!= null)
            {
                String[] sentences = getLine.split(".");
                for(String sentence : sentences){
                    sentencesList.add(sentence);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sentencesList;
    }
}
