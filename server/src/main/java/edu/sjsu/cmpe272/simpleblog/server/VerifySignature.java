package edu.sjsu.cmpe272.simpleblog.server;

import lombok.extern.slf4j.Slf4j;

import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

@Slf4j
public class VerifySignature {
    public static boolean verifySignature(Message message,  PublicKey publicKey) throws Exception {
        // verifying Signature of the message with the public key of the author
        log.debug("verifySignature");
        String signature = message.getSignature();
        byte[] decodedSignature = Base64.getDecoder().decode(signature);
        String data =message.getDate() + message.getAuthor() + message.getMessage() + message.getAttachment();
        Signature sig = Signature.getInstance("SHA256withRSA"); // using SHA256RSA for Signing and verifying
        sig.initVerify(publicKey);
        sig.update(data.getBytes());
        return sig.verify(decodedSignature);
    }
}

