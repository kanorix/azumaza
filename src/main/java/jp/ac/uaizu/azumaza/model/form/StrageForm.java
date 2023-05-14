package jp.ac.uaizu.azumaza.model.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

public class StrageForm {

    private Part uploadFile;

    private List<FileForm> files = new ArrayList<>();

    public Part getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(Part uploadFile) {
        this.uploadFile = uploadFile;
    }

    public List<FileForm> getFiles() {
        return files;
    }

    public void setFiles(List<FileForm> files) {
        this.files = files;
    }
}
