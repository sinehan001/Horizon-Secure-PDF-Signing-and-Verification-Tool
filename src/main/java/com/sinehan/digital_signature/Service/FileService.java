package com.sinehan.digital_signature.Service;

import com.sinehan.digital_signature.Entity.FileData;
import com.sinehan.digital_signature.Entity.FileSign;
import com.sinehan.digital_signature.Repository.FileRepository;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.security.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public static UUID generateRandomUUID() {
        return UUID.randomUUID();
    }

    public static void GetTimestamp(String info) {
        System.out.println(info + new Timestamp((new Date()).getTime()));
    }

    public static byte[] GenerateSignature(byte[] fileBytes, KeyPair keys) throws SignatureException,
            UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException {
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA", "BC");
        ecdsaSign.initSign(keys.getPrivate());
        ecdsaSign.update(fileBytes);
        byte[] signature = ecdsaSign.sign();
        System.out.println(Arrays.toString(signature));
        return signature;
    }

    public static boolean ValidateSignature(byte[] fileBytes, KeyPair pair, byte[] signature) throws SignatureException,
            InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException {
        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA", "BC");
        ecdsaVerify.initVerify(pair.getPublic());
        ecdsaVerify.update(fileBytes);
        return ecdsaVerify.verify(signature);
    }

//    public static boolean ValidateSignature(byte[] fileBytes, KeyPair pair, byte[] signature, String filename) throws SignatureException,
//            InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException {
//        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA", "BC");
//        ecdsaVerify.initVerify(pair.getPublic());
//        ecdsaVerify.update(fileBytes);
//        return ecdsaVerify.verify(signature);
//    }

    public static KeyPair GenerateKeys()
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("B-571");
        KeyPairGenerator g = KeyPairGenerator.getInstance("ECDSA", "BC");
        g.initialize(ecSpec, new SecureRandom());
        return g.generateKeyPair();
    }

    public FileSign fileSign(String uploadDir, String org_filename) {
        FileSign fileSign = new FileSign();
        try {
            Security.addProvider(new BouncyCastleProvider());
            byte[] fileContent = Files.readAllBytes(Paths.get(uploadDir+org_filename));
            GetTimestamp("Key Generation started: ");
            KeyPair keys = GenerateKeys();
            GetTimestamp("Key Generation ended: ");
            GetTimestamp("Signature Generation started: ");
            byte[] signature = GenerateSignature(fileContent, keys);
            GetTimestamp("Signature Generation ended: ");
            GetTimestamp("Validation started: ");
            // Convert KeyPair to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(keys);
            byte[] keyPairBytes = byteArrayOutputStream.toByteArray();
            fileSign.setKeyPair(keyPairBytes);
            fileSign.setSignature(signature);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(keyPairBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            // Deserialize the KeyPair byte array back to KeyPair
            KeyPair retrievedKeys = (KeyPair) objectInputStream.readObject();


            boolean isValidated = ValidateSignature(fileContent, retrievedKeys, signature);
            System.out.println("Result: " + isValidated);
            GetTimestamp("Validation ended: ");
        } catch (NoSuchFileException e) {
            System.out.println("File not found");
        } catch (NoSuchAlgorithmException e) {
            System.out.print("No such algorithm found");
        } catch (IOException | ClassNotFoundException | InvalidAlgorithmParameterException | SignatureException |
                 NoSuchProviderException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return fileSign;
    }

    public FileData findByPdfName(@RequestParam String pdfName) {
        return fileRepository.findByPdfName(pdfName);
    }

    public boolean validate_file(String uploadDir, String filename) throws IOException, ClassNotFoundException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());
        FileData fileData = findByPdfName(filename);
        if (fileData != null) {
            byte[] fileContent = Files.readAllBytes(Paths.get(uploadDir+filename));

            byte[] keyPairBytes = fileData.getKeyPair();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(keyPairBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            KeyPair retrievedKeys = (KeyPair) objectInputStream.readObject();

            byte[] signatureBytes = fileData.getSignature();
            System.out.println(Arrays.toString(signatureBytes));

            return ValidateSignature(fileContent, retrievedKeys, signatureBytes);
        }
        else {
            return false;
        }
    }
}
