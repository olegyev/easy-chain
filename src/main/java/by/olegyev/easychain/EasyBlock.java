package by.olegyev.easychain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EasyBlock {

    private String hash;
    private String previousHash;
    private List<EasyTransaction> transactions;
    private long timestamp;
    private int nonce;

    public EasyBlock(final List<EasyTransaction> transactions, final String previousHash) {
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        this.hash = calculateEasyBlockHash();
    }

    public String calculateEasyBlockHash() {
        final StringBuilder data = new StringBuilder();

        for (EasyTransaction transaction : transactions) {
            data.append(transaction.getSender());
            data.append(transaction.getRecipient());
            data.append(transaction.getValue());
        }

        final String dataToHash = previousHash + timestamp + nonce + data.toString();
        MessageDigest digest;
        byte[] bytes = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("ERROR! " + ex.getMessage());
        }

        final StringBuilder bytesSb = new StringBuilder();
        if (bytes != null) {
            for (byte b : bytes) {
                bytesSb.append(String.format("%02x", b));
            }
        }

        return bytesSb.toString();
    }

    public String mineEasyBlock(final int difficulty) {
        final char[] prefixArray = new char[difficulty];
        Arrays.fill(prefixArray, '0');
        final String hashPrefix = new String(prefixArray);

        String target = hash.substring(0, difficulty);

        while (!target.equals(hashPrefix)) {
            nonce++;
            hash = calculateEasyBlockHash();
            target = hash.substring(0, difficulty);
        }

        return hash;
    }

    public String getHash() {
        return this.hash;
    }

    public String getPreviousHash() {
        return this.previousHash;
    }

    public List<EasyTransaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "\n hash='" + hash + '\'' +
                "\n previousHash='" + previousHash + '\'' +
                "\n numberOfTransactions=" + transactions.size() +
                "\n timestamp=" + timestamp +
                "\n nonce=" + nonce;
    }

}