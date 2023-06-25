import java.nio.charset.StandardCharsets;
import java.security.*;

public class Sh256withRSA {
    public static void main(String[] args) {
        try {
            // Create Key Pair
            KeyPair keyPair = generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            String data = "Test Data!";

            // Hash the data
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedData = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            //Sign Data
            byte[] signedHash = signData(hashedData, privateKey);

            //Check signature
            boolean isValid = verifySignature(hashedData, signedHash, publicKey);

            // Print the result
            System.out.println("Data: " + data);
            //System.out.println("Hashed Data : " + new String(hashedData, StandardCharsets.UTF_8));
            //System.out.println("Signed Data" + new String(signedHash, StandardCharsets.UTF_8));
            System.out.println("Signature valid: " + isValid);

        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] signData(byte[] hashedData, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(hashedData);
        return signature.sign();
    }

    private static boolean verifySignature(byte[] hashedData, byte[] signedHash, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(publicKey);
        verifier.update(hashedData);
        return verifier.verify(signedHash);
    }
}
