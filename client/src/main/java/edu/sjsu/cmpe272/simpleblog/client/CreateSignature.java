package edu.sjsu.cmpe272.simpleblog.client;

import java.security.*;
import java.util.Base64;

import static edu.sjsu.cmpe272.simpleblog.client.GenerateKeyPair.convertStringToPrivateKey;


public class CreateSignature {

    public static String createSignature(PostMessage postMessage, String privateKey)
            throws Exception {
        // Create a String with no spaces for signature
//        String data = "2024-03-13T19:38-07:00" + "ben" + "hello world!" + "aSdlfkJ888oidfjwe+";
        String data = postMessage.date+postMessage.author+postMessage.message+postMessage.attachment;
        //Sign the data
        Signature signature = Signature.getInstance("SHA256withRSA");
        PrivateKey pk = convertStringToPrivateKey(privateKey,"RSA");
        signature.initSign(pk);
        signature.update(data.getBytes());
        byte[] digitalSignature = signature.sign();

        //Encode the signature
        String encodedSignature = Base64.getEncoder().encodeToString(digitalSignature);

//        System.out.println("Signature: " + encodedSignature);
        return encodedSignature;
    }
}

