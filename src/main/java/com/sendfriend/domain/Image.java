package com.sendfriend.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Image {

    @Lob
    private byte[] fileBytes;

    private String fileName;

    public Image()  {}

    public Image(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public Image(byte[] fileBytes, int id ) {
        this.fileBytes = fileBytes;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
