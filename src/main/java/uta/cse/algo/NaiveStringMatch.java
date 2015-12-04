package uta.cse.algo;

import javax.validation.constraints.Null;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class NaiveStringMatch {

    private String[] T;
    private String[] P;
    private String fname;

    public NaiveStringMatch(String[] T, String P[],String fname) {
        this.T = T;
        this.P = P;
        this.fname=fname;

    }

    public boolean run() {
        int n = T.length;
        int m = P.length;
        boolean flag=false;
        for(int s=0; s <= (n - m); s++)
		{
			flag = true;
			for(int i=0; i<m; i++)
			{
				if(!(P[i].equalsIgnoreCase(T[s+i])))
				{
					flag = false;
					break;
				}
			}			
		}

        return flag;


    }
}