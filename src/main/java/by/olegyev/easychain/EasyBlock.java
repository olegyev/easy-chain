package by.olegyev.easychain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

public class EasyBlock {

    private String hash;
    private String previousHash;
    private String transactionContent;
    private long timestamp;
    private int nonce;

    public EasyBlock(final String transactionContent, final String previousHash) {
        this.transactionContent = transactionContent;
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        this.hash = calculateEasyBlockHash();
    }

    public String calculateEasyBlockHash() {
        final String dataToHash = previousHash + timestamp + nonce + transactionContent;
        MessageDigest digest;
        byte[] bytes = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("ERROR! " + ex.getMessage());
        }

        StringBuilder sb = new StringBuilder();
        if (bytes != null) {
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
        }

        return sb.toString();
    }

    public String mineEasyBlock(final int difficulty) {
        final char[] prefixArray = new char[difficulty];
        Arrays.fill(prefixArray, '0');
        final String hashPrefix = new String(prefixArray);

        String a = hash.substring(0, difficulty);
        ;

        while (!a.equals(hashPrefix)) {
            nonce++;
            hash = calculateEasyBlockHash();
            a = hash.substring(0, difficulty);
        }

        return hash;
    }

    public String getHash() {
        return this.hash;
    }

    public String getPreviousHash() {
        return this.previousHash;
    }

    @Override
    public String toString() {
        return "\n hash='" + hash + '\'' +
                "\n previousHash='" + previousHash + '\'' +
                "\n transactionContent='" + transactionContent + '\'' +
                "\n timestamp=" + timestamp +
                "\n nonce=" + nonce;
    }
}