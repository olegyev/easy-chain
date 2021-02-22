package by.olegyev.easychain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static final List<EasyBlock> easyBlockchain = new ArrayList<>();
    public static final int difficulty = 4;

    public static void main(String[] args) {
        prefillEasyBlockchain();

        final EasyBlock secondEasyBlock = new EasyBlock("This is a Second Block.", easyBlockchain.get(easyBlockchain.size() - 1).getHash());
        addEasyBlock(secondEasyBlock);

        final EasyBlock thirdEasyBlock = new EasyBlock("This is a Third Block.", easyBlockchain.get(easyBlockchain.size() - 1).getHash());
        addEasyBlock(thirdEasyBlock);

        System.out.println();

        printEasyBlockchain();

        System.out.println();

        System.out.println("EasyBlockchain is valid? \n" + isEasyBlockchainValid());
    }

    private static String getHashPrefix() {
        final char[] prefixArray = new char[difficulty];
        Arrays.fill(prefixArray, '0');
        return new String(prefixArray);
    }

    private static void prefillEasyBlockchain() {
        final EasyBlock genesisEasyBlock = new EasyBlock("This is a Genesis Block.", "0");
        addEasyBlock(genesisEasyBlock);

        final EasyBlock firstEasyBlock = new EasyBlock("This is a First Block.", genesisEasyBlock.getHash());
        addEasyBlock(firstEasyBlock);
    }

    private static void addEasyBlock(final EasyBlock blockToAdd) {
        final String minedHash = blockToAdd.mineEasyBlock(difficulty);
        System.out.println("New Block Mined : " + minedHash);
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