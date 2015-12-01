package uta.cse.algo;

import javax.validation.constraints.Null;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by riby on 11/22/15.
 */
public class Kmp {

    private String[] T;
    private String[] P;
    private String fname;

    public Kmp(String[] T, String P[],String fname) {
        this.T = T;
        this.P = P;
        this.fname=fname;

    }

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


    public String run() {
        int n = T.length;
        int m = P.length;
        int[] arr = compute_prefix_function(P);
        int q = 0;
        int count = 0, i = 0, j = 0;
        while (i < n) {
            if (P[j].equals(T[i])) {
                i += 1;
                j += 1;
            }
            if (j == m) {
                System.out.println("Found Pattern at index" + (i - j));
                j = arr[j - 1];
            } else if (i < n && !P[j].equals(T[i])) {
                if (j != 0)
                    j = arr[j - 1];
                else
                    i += 1;
            }
        }
        return null;
    }
}