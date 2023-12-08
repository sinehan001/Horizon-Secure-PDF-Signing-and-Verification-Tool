package com.sinehan.digital_signature.Repository;

import com.sinehan.digital_signature.Entity.FileData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<FileData, String> {
    FileData findByPdfName(String pdfName);
}
