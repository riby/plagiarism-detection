package uta.cse.algo;

/**
 * Created by ADMIN on 03-12-2015.
 */
public class PerformanceDetails {
    String algoName;
    double plagarismPercent;
    long runTime;

    public PerformanceDetails(String algoName, double plagarismPercent, long runTime)
    {
        this.algoName = algoName;
        this.plagarismPercent = plagarismPercent;
        this.runTime = runTime;
    }
}
