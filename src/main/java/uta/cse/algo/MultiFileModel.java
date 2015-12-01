package uta.cse.algo;

/**
 * Created by riby on 12/1/15.
 */
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class MultiFileModel {



        private List<MultipartFile> listFiles;

        public List<MultipartFile> getFiles() {
            return listFiles;
        }

        public void setFiles(List<MultipartFile> files) {
            this.listFiles = files;
        }

}
