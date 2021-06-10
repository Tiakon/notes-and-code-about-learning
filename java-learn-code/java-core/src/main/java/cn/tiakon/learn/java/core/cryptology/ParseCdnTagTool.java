package cn.tiakon.learn.java.core.cryptology;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

// cn.tiakon.learn.java.core.cryptology.ParseCdnTagTool
public class ParseCdnTagTool {

    public static final int GCM_TAG_LENGTH = 16;

    public static void main(String[] args) throws Exception {

        String key = "4a06a14a7d573d0a157343ac1cb7d28b9561d9052887edbae228cbe945fe1607";
        String iv = "c70cf3c8fe51543a7f68ab74";

        String[] ciphers = {
                "uwMROPz-r5BAYaQXGdGnFWdfDmQqFYMiAFhT8quHUI-rE9bTYg",
                "uwMRJfz_r5ggYaQXGdGlQn5Z5ygoNftRGmYZr2fIPW4",
                "uwMRJfz_r5igYaQXGdGnjg6HFJaHOhi1jBNdosN1YGY",
                "uwMRJfz_r5hgYaQXGdGlAb8nvJo8j_fkuOyIhDEPvvQ",
                "uwMRJfz_r5jgYaQXGdGnS_lUCGwhq5PNFKGhxH2uyik",
                "uwMRJfz_r5jgYaQXGdGnCwAk72x7AakWPFm3idJhmRY",
                "uwMRJ_z_r5rgYaQXGdGnC2de_cO7KRaHLO9J-8vH5-5jpw",
                "uwMRJ_z_r5vgYaQXGdGnC2deOGx11YxUn9KDm7lBSLlRqg"};

        for (String cipher : ciphers) {
            System.out.println("cdnTag : " + cipher);
            System.out.println(parseCdnTag(decrypt(cipher, key, iv)));
            System.out.println("----------------------------------------");
        }

    }

    // 解密
    public static byte[] decrypt(String cipherStr, String key, String iv) throws Exception {
        byte[] keyHexBytes = hexStringToBytes(key);
        byte[] ivHexBytes = hexStringToBytes(iv);

        byte[] cipherText = cipherStr.getBytes(StandardCharsets.UTF_8);
        byte[] cipherTextBytes = Base64.getUrlDecoder().decode(cipherText);

        // Create SecretKeySpec
        SecretKey secretKeySpec = new SecretKeySpec(keyHexBytes, "AES");
        SecretKeySpec keySpec = new SecretKeySpec(secretKeySpec.getEncoded(), "AES");

        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, ivHexBytes);

        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

        // Perform Decryption
        byte[] decryptedBytes = cipher.doFinal(cipherTextBytes);
        return decryptedBytes;
    }

    public static String parseCdnTag(byte[] decryptedCdnTagBytes) {
//        System.out.println("decryptedBytes length:" + decryptedCdnTagBytes.length);

        StringBuffer buffer = new StringBuffer();

        int[] versionPos = {0, 2};
        int[] lengthPos = {2, 4};
        int[] highBitPos = {4, 6};
        int[] dataMaskPos = {6, 8};
        int[] ctrlFlagPos = {8, 12};

        byte[] versionBytes = Arrays.copyOfRange(decryptedCdnTagBytes, versionPos[0], versionPos[1]);
        byte[] lengthBytes = Arrays.copyOfRange(decryptedCdnTagBytes, lengthPos[0], lengthPos[1]);
        byte[] highBitBytes = Arrays.copyOfRange(decryptedCdnTagBytes, highBitPos[0], highBitPos[1]);
        byte[] dataMaskBytes = Arrays.copyOfRange(decryptedCdnTagBytes, dataMaskPos[0], dataMaskPos[1]);
        byte[] ctrlFlag = Arrays.copyOfRange(decryptedCdnTagBytes, ctrlFlagPos[0], ctrlFlagPos[1]);

        String version = new BigInteger(1, versionBytes).toString(10);
        String length = new BigInteger(lengthBytes).toString(10);
        String flagHex = new BigInteger(1, ctrlFlag).toString(16);

        String highBit = new BigInteger(1, highBitBytes).toString(10);
        String flagBit = new BigInteger(1, ctrlFlag).toString(2);

//        System.out.println("version:" + version);
//        System.out.println("length:" + length);
        System.out.println("high_bit:" + highBit);
        System.out.println("flag_hex:" + flagHex);
//        System.out.println("no prefix flag_bin_length:" + flagBit.length());

        if (flagBit.length() != 32) {
            int zeroCount = 32 - flagBit.length();
            String prefix = "";
            for (int i = 0; i < zeroCount; i++) {
                prefix += "0";
            }
            flagBit = prefix.concat(flagBit);
        }

        System.out.println("flag_bin:" + flagBit);
        System.out.println("flag_bin_length:" + flagBit.length());

        String flagBitPrefix = flagBit.substring(0, Integer.parseInt(highBit));
//        System.out.println("flag_bit_prefix:" + flagBitPrefix);
        char[] charArray = flagBitPrefix.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
//            System.out.println(charArray[i]);
            if (charArray[i] == 49) {
                buffer.append((i + 1) + ":0").append(";");
            }
        }

        String dataMaskBinStr = new BigInteger(1, dataMaskBytes).toString(2);
        String dataMask = new BigInteger(1, dataMaskBytes).toString(16);

        System.out.println("data_mask_bin:" + dataMaskBinStr);
        System.out.println("data_mask_hex:" + dataMask);
        List<Integer> list = getIndexHighAnd(Integer.parseInt(new BigInteger(1, dataMaskBytes).toString(10)));

        int HEAD_SIZE = 12;
        //int[] MASK_LEN = {4, 2, 2, 1, 1};
        int[] MASK_POS = {0, 4, 6, 8, 9};

       /* for (int i = 0; i < dataMaskBinStr.length(); i++) {
            byte[] dataByte = Arrays.copyOfRange(decryptedCdnTagBytes, HEAD_SIZE + MASK_POS[i], HEAD_SIZE + MASK_POS[i + 1]);
            String dataValue = new BigInteger(1, dataByte).toString(10);
            System.out.println("data_" + i + " : " + dataValue);
//            buffer.append((i + 100) + ":" + dataValue).append(";");

//            if (dataByte.length != 1) {
//                String dataValue = new BigInteger(1, dataByte).toString(10);
//                System.out.println("data_" + i + " : " + dataValue);
//                buffer.append(i+":"+dataValue).append(";");
//            } else {
//                System.out.println("data_" + i + " : " + dataByte[0]);
//                buffer.append(i+":"+dataByte[0]).append(";");
//            }
        }*/

        for (Integer i : list) {
            byte[] dataByte = Arrays.copyOfRange(decryptedCdnTagBytes, HEAD_SIZE + MASK_POS[i], HEAD_SIZE + MASK_POS[i + 1]);
            String dataValue = new BigInteger(1, dataByte).toString(10);
            System.out.println("data_" + i + " : " + dataValue);
            buffer.append((i + 100) + ":" + dataValue).append(";");
        }

        String parseResult = buffer.toString();
        return parseResult.substring(0,parseResult.length()-1);
    }

    //  16进制字符串转换为字节数组
    public static byte[] hexStringToBytes(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
        }
        return baKeyword;
    }

    //crtlFlag 从高位判断，data块从低位判断
    // https://leetcode-cn.com/problems/number-of-1-bits/solution/wei-1de-ge-shu-by-leetcode/
    public static List<Integer> getIndexHighAnd(int n) {
        List<Integer> list = new ArrayList<>();
        int mask = 1;
        for (int i = 0; i < 32; i++) {
            if ((n & mask) != 0) {
                list.add(i);
            }
            mask <<= 1;
        }
        return list;
    }

    // 加密
    public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception {
        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

        // Perform Encryption
        byte[] cipherText = cipher.doFinal(plaintext);

        return cipherText;
    }

    // 16进制字符串转换为字符串
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
        }
        s = new String(baKeyword, StandardCharsets.UTF_8);
        return s;
    }

    //crtlFlag 从高位判断，data块从低位判断
    // https://leetcode-cn.com/problems/number-of-1-bits/solution/wei-1de-ge-shu-by-leetcode/
    public static List<Integer> getIndexLowAnd(int n) {
        List<Integer> list = new ArrayList<>();
        int mask = 1;
        for (int i = 0; i < 32; i++) {
            if ((n & mask) != 0) {
                list.add(i);
            }
            mask <<= 1;
        }
        return list;
    }

    // https://www.jb51.net/article/122480.htm
    public static int bytesToInt(byte b[]) {
        return b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16 | (b[0] & 0xff) << 24;
    }


    @Test
    public void demo01Test() {
        // high_bit:3
//        flag_int:-1610612736
//        flag_bin:10100000000000000000000000000000

        // high_bit:2
//        flag_int:-1073741824
//        flag_bin:11000000000000000000000000000000

        // high_bit:2
//        flag_int:1073741824
//        flag_bin:1000000000000000000000000000000

        // high_bit:2
//        flag_int:-2147483648
//        flag_bin:10000000000000000000000000000000

        int[] ints = {-1610612736, -1073741824, 1073741824, -2147483648};
        int[] length = {3, 2, 2, 2};
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ParseCdnTagTool.getIndexHighAnd(ints[i]));
        }
    }

    @Test
    public void Demo02Test() {
        String flagBit = "10000000000000";
        System.out.println(flagBit.length());
        if (flagBit.length() != 32) {
            int zeroCount = 32 - flagBit.length();
            String prefix = "";
            for (int i = 0; i < zeroCount; i++) {
                prefix += "0";
            }
            flagBit = prefix.concat(flagBit);
        }
        System.out.println(flagBit.length());
        System.out.println(flagBit);
    }
}
