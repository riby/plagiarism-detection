
package uta.cse.algo;
/**
 * Created by alokrai on 12/6/15.
 */
import java.util.ArrayList;
public class BoyerMoore {
    private final int K;
    private int[] arrRight;
    private String pattern;
    private String fname;
    private String text;

    public BoyerMoore(String pattern, String text, String fname) {
        this.K = 1000;
        this.pattern = pattern;
        this.text = text;
        this.fname = fname;
        arrRight = new int[K];
        for (int c = 0; c < K; c++)
            arrRight[c] = -1;
        for (int j = 0; j < pattern.length(); j++)
            arrRight[pattern.charAt(j)] = j;
    }

    public ArrayList<Integer> search(String text) {
        int N1 = pattern.length();
        int N2 = text.length();
        ArrayList<Integer> ArrInt = new ArrayList<Integer>();
        int skip;
        for (int i = 0; i <= N2 - N1; i += skip) {
            skip = 0;
            for (int j = N1 - 1; j >= 0; j--) {
                if (pattern.charAt(j) != text.charAt(i + j)) {   //comparing the patter lastindex with textIndex
                    skip = Math.max(1, j - arrRight[text.charAt(i + j)]); //if pattern index is not atching with text index skip it
                    break;
                }
            }
            if (skip == 0) {
                ArrInt.add(i);
                skip++;
            }
        }
        return ArrInt;
    }
    public boolean match() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        result = search(pattern);
        return !result.isEmpty();
    }
}

