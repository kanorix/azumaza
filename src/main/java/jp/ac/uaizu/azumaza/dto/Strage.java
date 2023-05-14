package jp.ac.uaizu.azumaza.dto;

import jp.ac.uaizu.azumaza.annotation.Column;
import jp.ac.uaizu.azumaza.annotation.Table;
import jp.ac.uaizu.azumaza.dao.DataBase.ColumnType;

@Table("strage")
public class Strage extends Record {

    @Column(name = "user_id", type = ColumnType.INTEGER)
    private int userId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size", type = ColumnType.INTEGER)
    private int fileSize;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
}
