package edu.sjsu.cmpe272.simpleblog.client;

import lombok.Getter;
import lombok.Setter;

public class Api {

    static class User {
        private String user;
        private String publicKey;
        public User() {}

        public User(String user, String publicKey) {
            this.user = user;
            this.publicKey = publicKey;
        }

        // Getters
        public String getUser() {
            return user;
        }

        public String getPublicKey() {
            return publicKey;
        }

        // Setters
        public void setUser(String user) {
            this.user = user;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }
    }





}

