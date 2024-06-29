package edu.sjsu.cmpe272.simpleblog.client;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class GenerateKeyPair {
    public static Map<String,String> generateRSAKeyPair(){
        try {
            Map<String,String> keys = new HashMap<>();
            // Initialize the Key Pair Generator
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            // Generate the Key Pair
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Get the public and private keys from the key pair
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Convert to String format (for example, Base64) to print or store
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

//            System.out.println("Public Key: " + publicKeyString);
//            System.out.println("Private Key: " + privateKeyString);
            keys.put("publicKey",publicKeyString);
            keys.put("privateKey",privateKeyString);
            return keys;

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            return null;
        }

    }

    public static PrivateKey convertStringToPrivateKey(String keyStr, String algorithm) throws Exception {

        // Base64 decode the data
        byte[] encoded = Base64.getDecoder().decode(keyStr);

        // Create a key factory
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        // Generate the private key from the decoded bytes
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return keyFactory.generatePrivate(keySpec);
    }



    public static String encodePublicKeyToPEM(String base64Encoded) {
        // Get the encoded version of the public key

        // Properly format the encoded key in PEM format
        StringBuilder pemFormat = new StringBuilder();
        pemFormat.append("-----BEGIN PUBLIC KEY-----\n");

        // Split the encoded string into multiple lines
        int index = 0;
        while (index < base64Encoded.length()) {
            int endIndex = Math.min(index + 64, base64Encoded.length());
            pemFormat.append(base64Encoded.substring(index, endIndex));
            pemFormat.append("\n");
            index = endIndex;
        }

        pemFormat.append("-----END PUBLIC KEY-----");
        return pemFormat.toString();
    }
}

