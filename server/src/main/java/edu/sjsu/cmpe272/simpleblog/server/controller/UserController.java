package edu.sjsu.cmpe272.simpleblog.server.controller;


import edu.sjsu.cmpe272.simpleblog.server.User;
import edu.sjsu.cmpe272.simpleblog.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User params) {
        String publicKey = params.getPublicKey().replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        userService.createUser(params.getUser(),publicKey);
        return ResponseEntity.ok(Map.of("message", "welcome"));
    }

    @PostMapping("/list")
    public ResponseEntity<?> listMessages() {
        Map<String, String> users = UserService.userPublicKeys;
        List<String> keyList = new ArrayList<>(users.keySet());
        return ResponseEntity.ok(keyList);
    }

    @GetMapping("/{username}/public-key")
    public ResponseEntity<?> getPublicKey(@PathVariable String username) {
        String publicKey = userService.getPublicKeyByUsername(username);
        if (publicKey == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "signature didn't match"));
        }
        return ResponseEntity.ok(Map.of("public-key", publicKey));
    }
}

