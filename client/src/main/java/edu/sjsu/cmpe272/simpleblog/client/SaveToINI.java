package edu.sjsu.cmpe272.simpleblog.client;


import org.ini4j.Wini;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveToINI {

    public static void saveToINIFile(String userid, String privateKey) throws IOException {
        String iniContent = "[UserDetails]\n" +
                "username=" + userid + "\n" +
                "privateKey="+privateKey+"\n";

//    System.out.println("ini content = "+iniContent+" "+userid);
        // Specify the file path
        String filePath = "mb.ini";

        // Write the content to the file

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(iniContent);
//            System.out.println("INI file has been created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String retrivePrivateKeyofUser(){
//        System.out.println("retrivePublicKeyofUser-------------------------------------------");
        String value = null;
        try {
            // Load the INI file
            Wini ini = new Wini(new File("mb.ini"));

            // Accessing values
            value = ini.get("UserDetails", "privateKey", String.class);
//            System.out.println("Value: " + value);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String retriveUserId(){
//        System.out.println("retriveUserID-------------------------------------------");
        String value = null;
        try {
            Wini ini = new Wini(new File("mb.ini"));

            value = ini.get("UserDetails", "username", String.class);
//            System.out.println("Value: " + value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }


}

