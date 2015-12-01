package uta.cse.algo;
import java.util.*;

/**
 * Created by riby on 11/20/15.
 */
public class FileModel {

    //@Id
    private String id;
    private String name;
    private ArrayList<String> linesOfData;

    public FileModel() {}

    public FileModel(String name, ArrayList<String> linesOfData) {
        this.name = name;
        this.linesOfData = linesOfData;
    }
    public String getName()
    {
        return this.name;
    }
    public ArrayList<String> getLines()
    {
        return this.linesOfData;
    }
}