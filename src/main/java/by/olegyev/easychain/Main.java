package by.olegyev.easychain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static final List<EasyBlock> easyBlockchain = new ArrayList<>();
    public static final int difficulty = 3;

    public static void main(String[] args) {
        final EasyWallet wA = new EasyWallet(easyBlockchain);
        final EasyWallet wB = new EasyWallet(easyBlockchain);
        System.out.println("Wallet A balance: " + wA.getBalance());
        System.out.println("Wallet B balance: " + wB.getBalance());

        System.out.println("Adding two transactions...");
        prefillEasyBlockchain(wA, wB);
        System.out.println();
        printEasyBlockchain();
        System.out.println("Wallet A balance: " + wA.getBalance());
        System.out.println("Wallet B balance: " + wB.getBalance());

        System.out.println();
        System.out.println("Adding another two transactions...");
        addAnotherBlock(wA, wB, easyBlockchain.get(0).getHash());
        System.out.println();
        printEasyBlockchain();
        System.out.println("Wallet A balance: " + wA.getBalance());
        System.out.println("Wallet B balance: " + wB.getBalance());

        // easyBlockchain.get(1).getTransactions().get(1).setValue(100);

        System.out.println("EasyBlockchain is valid? \n" + isEasyBlockchainValid());
    }

    private static String getHashPrefix() {
        final char[] prefixArray = new char[difficulty];
        Arrays.fill(prefixArray, '0');
        return new String(prefixArray);
    }

    private static void prefillEasyBlockchain(final EasyWallet wA, final EasyWallet wB) {
        final List<EasyTransaction> easyTransactions = new ArrayList<>();

        final EasyTransaction trA = wA.send(wB.getPublicKey(), 10);
        if (trA != null) {
            easyTransactions.add(trA);
        }

        final EasyTransaction trB = wA.send(wB.getPublicKey(), 30);
        if (trB != null) {
            easyTransactions.add(trB);
        }

        final EasyBlock genesisEasyBlock = new EasyBlock(easyTransactions, "0");
        addEasyBlock(genesisEasyBlock);
    }

    private static void addAnotherBlock(final EasyWallet wA, final EasyWallet wB, final String prevHash) {
        final List<EasyTransaction> easyTransactions = new ArrayList<>();

        final EasyTransaction trA = wB.send(wA.getPublicKey(), 5);
        if (trA != null) {
            easyTransactions.add(trA);
        }

        final EasyTransaction trB = wB.send(wA.getPublicKey(), 15);
        if (trB != null) {
            easyTransactions.add(trB);
        }

        final EasyBlock genesisEasyBlock = new EasyBlock(easyTransactions, prevHash);
        addEasyBlock(genesisEasyBlock);
    }

    private static void addEasyBlock(final EasyBlock blockToAdd) {
        final String minedHash = blockToAdd.mineEasyBlock(difficulty);
        System.out.println("New block mined : " + minedHash);
        easyBlockchain.add(blockToAdd);
    }

    private static boolean isEasyBlockchainValid() {
        String hashPrefix = getHashPrefix();

        for (int i = 0; i < easyBlockchain.size() - 1; i++) {
            final EasyBlock currentEasyBlock = easyBlockchain.get(i + 1);
            final EasyBlock previousEasyBlock = easyBlockchain.get(i);

            if (!currentEasyBlock.getPreviousHash().equals(previousEasyBlock.getHash())) {
                System.out.println("Previous hash is invalid!");
                return false;
            }

            if (!currentEasyBlock.getHash().equals(currentEasyBlock.calculateEasyBlockHash())) {
                System.out.println("Hash is invalid!");
                return false;
            }

            if (!hashPrefix.equals(currentEasyBlock.getHash().substring(0, difficulty))) {
                System.out.println("EasyBlock was not mined!");
                return false;
            }
        }

        return true;
    }

    private static void printEasyBlockchain() {
        int i = 0;
        for (EasyBlock block : easyBlockchain) {
            final String text = i == 0 ? "Genesis EasyBlock : " : "EasyBlock #" + i + " : ";
            System.out.println(text + block.toString() + "\n");
            i++;
        }
    }

}