package uta.cse.algo;

/**
 * Created by riby on 11/22/15.
 */
public class Kmp {

    /*The Constructer take as input the Text to compare, Text pattern and fname is the file names
    * which is being passed at runtime*/
    private String[] T;
    private String[] P;
    private String fname;

    public Kmp(String[] T, String P[],String fname) {
        this.T = T;
        this.P = P;
        this.fname=fname;

    }

    /* Precompute function is used defined*/

    public int[] compute_prefix_function(String[] P) {
        int m = P.length;
        int[] arr = new int[m];
        int i = 1, k = 0;
        while (i < m) {
            if (P[i].equals(P[k]) ) {
                k += 1;
                arr[i] = k;
                i += 1;
            } else {
                if (k != 0)
                    k = arr[k - 1];
                else {
                    arr[i] = 0;
                    i += 1;
                }
            }
        }
        return arr;

    }

        /* Takes the intance varibales, after making the pre-compute array it compares the Text with Pattern. It returns accordingly.*/


    public boolean run() {
        int n = T.length;
        int m = P.length;
        boolean flag=false;
        int[] arr = compute_prefix_function(P);
        int q = 0;
        int count = 0, i = 0, j = 0;
        while (i < n) {
            if (P[j].equals(T[i])) {
                i += 1;
                j += 1;
            }
            if (j == m) {

                flag=true;
                j = arr[j - 1];
            } else if (i < n && !P[j].equals(T[i])) {
                if (j != 0)
                    j = arr[j - 1];
                else
                    i += 1;
            }
        }

        return flag;


    }
}