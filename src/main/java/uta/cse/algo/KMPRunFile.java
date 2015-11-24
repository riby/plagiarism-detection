package uta.cse.algo;

/**
 * Created by riby on 11/22/15.
 */
public class KMPRunFile {
    public static void main(String[] args)
    {

        String [] T={"a","b","c","a","b","a","b","a","g","c"};
        String [] P={"a","b","a","b","a","g","c"};

        Kmp k=new Kmp(T,P);
        k.run();

    }

}
