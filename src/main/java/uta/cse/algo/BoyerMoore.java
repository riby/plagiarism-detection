package uta.cse.algo;

/**
 * Created by alokrai on 12/6/15.
 */

import java.util.ArrayList;

public class BoyerMoore {
    private final int R;     // the radix
    private int[] right;     // the bad-character skip array
    private String pat;      // or as a string
    private String fname;
    private String txt;

    // pattern provided as a string
    public BoyerMoore(String pat, String txt, String fname) {
        this.R = 256;
        this.pat = pat;
        this.txt = txt;
        this.fname = fname;

        // position of rightmost occurrence of c in the pattern
        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < pat.length(); j++)
            right[pat.charAt(j)] = j;
    }

    // return offset of first match; N if no match
    public ArrayList<Integer> search(String txt) {
        int M = pat.length();
        int N = txt.length();
        ArrayList<Integer> newArrayInt = new ArrayList<Integer>();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    skip = Math.max(1, j - right[txt.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0) {
                newArrayInt.add(i);    // found
                skip++;
            }
        }
        return newArrayInt;                       // not found
    }

    public boolean match() {

        ArrayList<Integer> result = new ArrayList<Integer>();
        result = search(pat);
        return !result.isEmpty();
    }
}

// test client
   /* public static void main(String[] args) throws IOException{


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Boyer Moore Algorithm Test\n");
        System.out.println("\nEnter Text\n");
        String txt = br.readLine();
        System.out.println("\nEnter Pattern\n");
        String pat = br.readLine();



        //String pat = "abc";
        //String txt = "asdf ghjk klll abc qwerty abc and poaslf abc";

        BoyerMoore boyermoore1 = new BoyerMoore(pat);

        ArrayList<Integer> offset = boyermoore1.search(txt);

        // print results
        System.out.println("Offset: "+ offset);
    }*/