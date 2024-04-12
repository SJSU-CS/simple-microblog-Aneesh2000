package edu.sjsu.cmpe272.simpleblog.server;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserService {
    public static Map<String, String> userPublicKeys = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void createUser(String username, String publicKey) {
        userPublicKeys.put(username.toLowerCase(), publicKey);
    }

    public String getPublicKey(String username) {
        return userPublicKeys.get(username.toLowerCase());
    }


    // Utility methods to convert string to PublicKey and PrivateKey
    public static PublicKey getPublicKeyFromString(String key) throws Exception {
        try{
            byte[] byteKey = Base64.getDecoder().decode(key.getBytes());
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePublic(X509publicKey);
        }
        catch (Exception e){
            return null;
        }
    }

    public static PrivateKey getPrivateKeyFromString(String key) throws Exception {
        log.debug("getPrivateKeyFromString");
        byte[] byteKey = Base64.getDecoder().decode(key.getBytes());
        PKCS8EncodedKeySpec PKCS8privateKey = new PKCS8EncodedKeySpec(byteKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePrivate(PKCS8privateKey);
    }

    public String getPublicKeyByUsername(String username) {
        log.debug("getPublicKeyByUsername");
        if(userPublicKeys.containsKey(username)){
            return userPublicKeys.get(username);
        }
        return null;
    }
}
