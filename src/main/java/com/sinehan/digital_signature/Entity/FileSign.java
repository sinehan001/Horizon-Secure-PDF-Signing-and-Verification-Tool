package com.sinehan.digital_signature.Entity;

public class FileSign {
    private byte[] Signature;
    private byte[] KeyPair;

    public byte[] getSignature() {
        return Signature;
    }

    public void setSignature(byte[] signature) {
        Signature = signature;
    }

    public byte[] getKeyPair() {
        return KeyPair;
    }

    public void setKeyPair(byte[] keyPair) {
        KeyPair = keyPair;
    }
}
