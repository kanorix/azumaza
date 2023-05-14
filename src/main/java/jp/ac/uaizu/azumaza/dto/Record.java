package jp.ac.uaizu.azumaza.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import jp.ac.uaizu.azumaza.annotation.Column;
import jp.ac.uaizu.azumaza.dao.DataBase.ColumnType;

public abstract class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id", type = ColumnType.INTEGER)
    private int id;

    @Column(name = "created_at", type = ColumnType.DATETIME)
    private LocalDateTime createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
