package com.dvoss.entities;

import javax.persistence.*;

/**
 * Created by Dan on 6/27/16.
 */
@Entity
@Table(name = "files")
public class AnonFile {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String originalFilename;

    @Column(nullable = false)
    String realFilename;

    @Column(nullable = false)
    String comments;

    @Transient
    boolean isPermanent;

    public AnonFile() {
    }

    public AnonFile(String originalFilename, String realFilename) {
        this.originalFilename = originalFilename;
        this.realFilename = realFilename;
    }

    public AnonFile(String originalFilename, String realFilename, boolean isPermanent) {
        this.originalFilename = originalFilename;
        this.realFilename = realFilename;
        this.isPermanent = isPermanent;
    }

    public AnonFile(String originalFilename, String realFilename, String comments) {
        this.originalFilename = originalFilename;
        this.realFilename = realFilename;
        this.comments = comments;
    }

    public AnonFile(String originalFilename, String realFilename, String comments, boolean isPermanent) {
        this.originalFilename = originalFilename;
        this.realFilename = realFilename;
        this.comments = comments;
        this.isPermanent = isPermanent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getRealFilename() {
        return realFilename;
    }

    public void setRealFilename(String realFilename) {
        this.realFilename = realFilename;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
