package uta.cse.algo;

/**
 * Created by riby on 11/21/15.
 */


import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<FileModel, String> {
    public FileModel findByName(String name);

}



