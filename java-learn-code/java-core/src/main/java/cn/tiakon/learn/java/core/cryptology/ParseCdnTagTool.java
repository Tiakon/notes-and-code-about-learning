package cn.tiakon.learn.java.core.cryptology;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

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
            parseCdnTag(decrypt(cipher, key, iv));
            System.out.println("----------------------------------------");
        }

    }

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

    public static void parseCdnTag(byte[] decryptedCdnTagBytes) {
        System.out.println("decryptedBytes length:" + decryptedCdnTagBytes.length);

        int[] versionPos = {0, 2};
        int[] lengthPos = {2, 4};
        int[] highBitPos = {4, 6};
        int[] dataMaskPos = {6, 8};
        int[] ctrlFlagPos = {8, 12};

        byte[] versionBytes = Arrays.copyOfRange(decryptedCdnTagBytes, versionPos[0], versionPos[1]);
        byte[] lengthBytes = Arrays.copyOfRange(decryptedCdnTagBytes, lengthPos[0], lengthPos[1]);
        byte[] highBitBytes = Arrays.copyOfRange(decryptedCdnTagBytes, highBitPos[0], highBitPos[1]);
        byte[] dataMaskBytes = Arrays.copyOfRange(decryptedCdnTagBytes, dataMaskPos[0], dataMaskPos[1]);
        byte[] ctrlflag = Arrays.copyOfRange(decryptedCdnTagBytes, ctrlFlagPos[0], ctrlFlagPos[1]);

        System.out.println("version:" + new BigInteger(1, versionBytes).toString(10));
        System.out.println("length:" + new BigInteger(lengthBytes).toString(10));
        System.out.println("high_bit:" + new BigInteger(1, highBitBytes).toString(10));
        System.out.println("flag_hex:" + new BigInteger(1, ctrlflag).toString(16));
        System.out.println("flag_bin:" + new BigInteger(1, ctrlflag).toString(2));

        String dataMaskBinStr = new BigInteger(1, dataMaskBytes).toString(2);
        System.out.println("data_mask_hex:" + new BigInteger(1, dataMaskBytes).toString(16));
        System.out.println("data_mask_bin:" + dataMaskBinStr);

        int HEAD_SIZE = 12;
        //int[] MASK_LEN = {4, 2, 2, 1, 1};
        int[] MASK_POS = {0, 4, 6, 8, 9};

        for (int i = 0; i < dataMaskBinStr.length(); i++) {
            byte[] dataByte = Arrays.copyOfRange(decryptedCdnTagBytes, HEAD_SIZE + MASK_POS[i], HEAD_SIZE + MASK_POS[i + 1]);
            if (dataByte.length != 1) {
                System.out.println("data_" + i + " : " + new BigInteger(1, dataByte).toString(10));
//                System.out.println("data_" + i + " : " + bytesToUShort(dataByte));
            } else {
                System.out.println("data_" + i + " : " + dataByte[0]);
            }
        }
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

    // 网络字节序转本地字节序
//    public static int bytesToUShort(byte b[]) {
//        return b[1] & 0xff | (b[0] & 0xff) << 8;
//    }

    @Test
    public void demo01Test() {
//        System.out.println(hexStringToString("4a06a14a7d573d0a157343ac1cb7d28b9561d9052887edbae228cbe945fe1607"));
    }

}
