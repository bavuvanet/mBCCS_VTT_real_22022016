package com.viettel.bccs2.viettelpos.v2.rsa;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class Crypto {

    /**
     * Gen RSA couple key
     *
     * @param keySize
     * @return [] key: [0] = public key, [1] = private key
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String[] GenRSAKey(int keySize)
            throws NoSuchAlgorithmException, IOException {
        String[] array = new String[2];
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");

        kpg.initialize(keySize, new SecureRandom());
        KeyPair keys = kpg.generateKeyPair();

        RSAPrivateKey privateKey = (RSAPrivateKey) keys.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keys.getPublic();
        array[0] = genKey(publicKey);
        array[1] = genKey(privateKey);
        return array;
    }

    static String genKey(Key key) throws IOException {
        if (key == null) {
            throw new IllegalArgumentException("key is null.");
        }
        byte[] bKeyEncoded = key.getEncoded();
        byte[] b = DERtoString(bKeyEncoded);
        String rsaKey = new String(b);
        return rsaKey;
    }

    private static byte[] DERtoString(byte[] bytes) {
        ByteArrayOutputStream pemStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(pemStream);

        byte[] stringBytes = encodeBase64(bytes).getBytes();
        String encoded = new String(stringBytes);
        encoded = encoded.replace("\r", "");
        encoded = encoded.replace("\n", "");

        int i = 0;
        while ((i + 1) * 64 <= encoded.length()) {
            writer.print(encoded.substring(i * 64, (i + 1) * 64));
            i++;
        }
        if (encoded.length() % 64 != 0) {
            writer.print(encoded.substring(i * 64));
        }
        writer.flush();
        return pemStream.toByteArray();
    }

    /**
     * Encrypt data
     *
     * @param dataToEncrypt
     * @param pubCer
     * @param isFile
     * @return
     * @throws Exception
     */
    public static String Encrypt(String dataToEncrypt, String pubCer,
                                 Boolean isFile) throws Exception {
        RSAPublicKey _publicKey = LoadPublicKey(pubCer, isFile);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // "RSA/ECB/OAEPWithSHA1AndMGF1Padding"

        cipher.init(1, _publicKey);

        int keySize = _publicKey.getModulus().bitLength() / 8;
        int maxLength = keySize - 42;

        byte[] bytes = dataToEncrypt.getBytes();

        int dataLength = bytes.length;
        int iterations = dataLength / maxLength;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= iterations; i++) {
            byte[] tempBytes = new byte[dataLength - maxLength * i > maxLength ? maxLength
                    : dataLength - maxLength * i];
            System.arraycopy(bytes, maxLength * i, tempBytes, 0,
                    tempBytes.length);
            byte[] encryptedBytes = cipher.doFinal(tempBytes);

            encryptedBytes = reverse(encryptedBytes);
            sb.append(encodeBase64(encryptedBytes));
        }

        String sEncrypted = sb.toString();
        sEncrypted = sEncrypted.replace("\r", "");
        sEncrypted = sEncrypted.replace("\n", "");
        return sEncrypted;
    }

    /**
     * verify data
     *
     * @param dataToVerify
     * @param signedData
     * @param pubCer
     * @param isFile
     * @return
     * @throws Exception
     */
    public static boolean Verify(String dataToVerify, String signedData,
                                 String pubCer, Boolean isFile) throws Exception {
        RSAPublicKey _publicKey = LoadPublicKey(pubCer, isFile);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(_publicKey);
        signature.update(dataToVerify.getBytes(), 0,
                dataToVerify.getBytes().length);
        byte[] bSign = decodeBase64(signedData);
        boolean pass = signature.verify(bSign);

        return pass;
    }

    /**
     * Sign data
     *
     * @param dataToSign
     * @param privateKey
     * @param isFile
     * @return
     * @throws Exception
     */
    public static String Sign(String dataToSign, String privateKey,
                              Boolean isFile) throws Exception {

        RSAPrivateKey _privateKey = LoadPrivateKey(privateKey, isFile);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(_privateKey);
        signature.update(dataToSign.getBytes());

        byte[] bSigned = signature.sign();

        String sResult = encodeBase64(bSigned);

        sResult = sResult.replace("\r", "");
        sResult = sResult.replace("\n", "");
        return sResult;
    }

    /**
     * decrypt data
     *
     * @param dataEncrypted
     * @param privateKey
     * @param isFile
     * @return
     * @throws Exception
     */
    public static String Decrypt(String dataEncrypted, String privateKey,
                                 Boolean isFile) throws Exception {
        RSAPrivateKey _privateKey = LoadPrivateKey(privateKey, isFile);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // "RSA/ECB/OAEPWithSHA1AndMGF1Padding"

        cipher.init(2, _privateKey);
        dataEncrypted = dataEncrypted.replace("\r", "");
        dataEncrypted = dataEncrypted.replace("\n", "");
        int dwKeySize = _privateKey.getModulus().bitLength();
        int base64BlockSize = dwKeySize / 8 % 3 != 0 ? dwKeySize / 8 / 3 * 4
                + 4 : dwKeySize / 8 / 3 * 4;
        int iterations = dataEncrypted.length() / base64BlockSize;
        ByteBuffer bb = ByteBuffer.allocate(100000);
        for (int i = 0; i < iterations; i++) {
            String sTemp = dataEncrypted.substring(base64BlockSize * i,
                    base64BlockSize * i + base64BlockSize);
            byte[] bTemp = decodeBase64(sTemp);
            bTemp = reverse(bTemp);
            byte[] encryptedBytes = cipher.doFinal(bTemp);
            bb.put(encryptedBytes);
        }
        byte[] bDecrypted = bb.array();
        return new String(bDecrypted).trim();
    }

    /**
     * load private key from key string if load key from file: isFile = true,
     * else isFile = false
     *
     * @param key
     * @param isFile
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static RSAPrivateKey LoadPrivateKey(String key, Boolean isFile)
            throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        String sReadFile = "";
        if (isFile) {
            File file = new File(key);
            sReadFile = fullyReadFile(file);
        } else {
            sReadFile = key.trim();
        }
        if ((sReadFile.startsWith("-----BEGIN PRIVATE KEY-----"))
                && (sReadFile.endsWith("-----END PRIVATE KEY-----"))) {
            sReadFile = sReadFile.replace("-----BEGIN PRIVATE KEY-----", "");
            sReadFile = sReadFile.replace("-----END PRIVATE KEY-----", "");
            sReadFile = sReadFile.replace("\n", "");
            sReadFile = sReadFile.replace("\r", "");
            sReadFile = sReadFile.replace(" ", "");
        } else {
            // return null;
        }
        byte[] b = decodeBase64(sReadFile);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);

        KeyFactory factory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) factory.generatePrivate(spec);
    }

    /**
     * load public key from key string
     *
     * @param pubCer
     * @param isFile
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws CertificateException
     */
    private static RSAPublicKey LoadPublicKey(String pubCer, Boolean isFile)
            throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, CertificateException {
        RSAPublicKey publicKey = null;
        try {
            if (isFile) {
                File file = new File(pubCer);
                pubCer = fullyReadFile(file);
            } else {
                pubCer = pubCer.trim();
            }

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                    decodeBase64(pubCer));
            publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
        }
        return publicKey;
    }

    private static String fullyReadFile(File file) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        byte[] bytesOfFile = new byte[(int) file.length()];
        dis.readFully(bytesOfFile);
        dis.close();
        String sRead = new String(bytesOfFile);
        return sRead.trim();
    }

    private static String encodeBase64(byte[] dataToEncode) {
        return android.util.Base64.encodeToString(dataToEncode,
                android.util.Base64.DEFAULT);
    }

    private static byte[] decodeBase64(String dataToDecode) {
        return android.util.Base64.decode(dataToDecode,
                android.util.Base64.DEFAULT);
    }

    private static byte[] reverse(byte[] b) {
        int left = 0;
        int right = b.length - 1;

        while (left < right) {
            byte temp = b[left];
            b[left] = b[right];
            b[right] = temp;

            left++;
            right--;
        }
        return b;
    }
}
