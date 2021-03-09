package by.olegyev.easychain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.List;

public class EasyWallet {

    private String privateKey;
    private String publicKey;
    private float balance = 100f;
    private List<EasyBlock> easyBlockchain;

    public EasyWallet(final List<EasyBlock> easyBlockchain) {
        generateKeyPair();
        this.easyBlockchain = easyBlockchain;
    }

    public void generateKeyPair() {
        final String algo = "RSA";
        KeyPair keyPair;

        try {
            keyPair = KeyPairGenerator.getInstance(algo).generateKeyPair();

            privateKey = keyPair.getPrivate().toString();
            publicKey = keyPair.getPublic().toString();
        } catch (Exception ex) {
            System.out.println("ERROR! " + ex.getMessage());
        }
    }

    public float getBalance() {
        float total = balance;
        for (EasyBlock block : easyBlockchain) {
            for (int i = 0; i < block.getTransactions().size(); i++) {
                final EasyTransaction transaction = block.getTransactions().get(i);
                if (transaction.getRecipient().equals(publicKey)) total += transaction.getValue();
                if (transaction.getSender().equals(publicKey)) total -= transaction.getValue();
            }
        }
        return total;
    }

    public EasyTransaction send(final String recipient, final float value) {
        if (getBalance() < value) {
            System.out.println("Not enough tokens!");
            return null;
        }

        return new EasyTransaction(publicKey, recipient, value);
    }

    public String getPublicKey() {
        return publicKey;
    }

}