package com.sinehan.digital_signature.Controller;

import com.sinehan.digital_signature.Entity.FileData;
import com.sinehan.digital_signature.Entity.FileSign;
import com.sinehan.digital_signature.Repository.FileRepository;
import com.sinehan.digital_signature.Service.FileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class APIController {

    private final FileService fileService;

    private final FileRepository fileRepository;
    public FileData fileData;

    public APIController(FileService fileService, FileRepository fileRepository) {
        this.fileService = fileService;
        this.fileRepository = fileRepository;
    }

    @GetMapping("/download/signedFile")
    public ResponseEntity<Resource> downloadSignedFile(@RequestParam String filename) {
        // Provide the path to the signed file
        String signedFilePath = System.getProperty("user.dir") + "/src/main/resources/static/files/"+filename;
        Path signedFilePathObject = Paths.get(signedFilePath);

        // Create a FileSystemResource from the signed file
        FileSystemResource fileResource = new FileSystemResource(signedFilePathObject.toFile());

        // Set headers for the file download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", filename);

        // Return ResponseEntity with the signed file as a Resource
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileResource);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/files/";
            Path uploadPath = Paths.get(uploadDir);

            // Ensure the directory exists, or create it
            if (!uploadPath.toFile().exists()) {
                uploadPath.toFile().mkdirs();
            }

            // Get the original file name
            String originalFileName = file.getOriginalFilename();
            String uuid = FileService.generateRandomUUID()+".pdf";
            String id = FileService.generateRandomUUID().toString();

            // Create a new file in the specified upload directory
            File destination = new File(uploadDir, uuid);
            file.transferTo(destination);

            fileData = new FileData();

            fileData.setId(id);
            fileData.setPdfName(uuid);
            fileData.setOriginalPdfName(originalFileName);

            FileSign fileSign = fileService.fileSign(uploadDir, uuid);

            fileData.setSignature(fileSign.getSignature());
            fileData.setKeyPair(fileSign.getKeyPair());

            fileRepository.save(fileData);

            String fileUrl = "download/signedFile?filename="+uuid;

            return new ResponseEntity<>("{\"message\": \"File Uploaded Successfully\", \"url\": \"" + fileUrl + "\"}", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<String> verifyFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/files/";
            Path uploadPath = Paths.get(uploadDir);

            // Ensure the directory exists, or create it
            if (!uploadPath.toFile().exists()) {
                uploadPath.toFile().mkdirs();
            }

            String filePath = uploadDir + file.getOriginalFilename();

            File destination = new File(filePath);
            file.transferTo(destination);

            // Get the original file name
            String originalFileName = file.getOriginalFilename();

            boolean fileVerify = fileService.validate_file(uploadDir, originalFileName);
            if(fileVerify) {
                return new ResponseEntity<>("{\"message\": \"File Verified Successfully\"}", HttpStatus.OK);
            }
            return new ResponseEntity<>("{\"error\": \"Unauthorized File\"}", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to Verify the File", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
