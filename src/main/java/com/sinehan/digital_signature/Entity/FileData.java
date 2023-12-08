package com.sinehan.digital_signature.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_doc")
public class FileData {
    @Id
    private String id;
    @Field("pdfName")
    private String pdfName;
    @Field("originalPdfName")
    private String originalPdfName;
    @Field("signature")
    private byte[] signature;
    @Field("keyPair")
    private byte[] keyPair;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getOriginalPdfName() {
        return originalPdfName;
    }

    public void setOriginalPdfName(String originalPdfName) {
        this.originalPdfName = originalPdfName;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(byte[] keyPair) {
        this.keyPair = keyPair;
    }

}
