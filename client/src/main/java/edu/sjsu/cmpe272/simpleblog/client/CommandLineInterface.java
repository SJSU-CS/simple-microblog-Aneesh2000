package edu.sjsu.cmpe272.simpleblog.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.Callable;

import static edu.sjsu.cmpe272.simpleblog.client.GenerateKeyPair.encodePublicKeyToPEM;
import static edu.sjsu.cmpe272.simpleblog.client.SaveToINI.retrivePrivateKeyofUser;
import static edu.sjsu.cmpe272.simpleblog.client.SaveToINI.retriveUserId;


@Slf4j
@Component
@Command(name = "cli", mixinStandardHelpOptions = true, subcommands = {CommandLineInterface.Post.class, CommandLineInterface.CreateUser.class,CommandLineInterface.ListMessages.class})
public class CommandLineInterface {
    static String base_url = "http://localhost:8080/"; // base URL for the server


    @Component
    @Command(
            name = "list",
            mixinStandardHelpOptions = true,
            exitCodeOnExecutionException = 34)
    static class ListMessages implements Callable<Integer> {     // class for cli parameter - list messages
        @Option(
                names = "--starting",
                description = "starting message id",
                defaultValue = "0")
        private Integer starting_id;

        @Option(
                names = "--count",
                description = "limit of the no of messages",
                defaultValue = "10")
        private Integer limit;

        @Option(
                names = "--save attachment",
                description = "save-attachment of message from author",
                defaultValue = "false")
        private String save_attachment;

        @Override
        public Integer call() throws Exception {
            ListMessage listMessage = new ListMessage(limit,1);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(listMessage);
//          System.out.println(json);

            ApiService apiService = new ApiService();
            String response = String.valueOf(apiService.sendRawJsonPostRequest(base_url+"messages/list",json));
            System.out.println(response);
            return 9;
        }
    }

    @Component
    @Command(name = "create", mixinStandardHelpOptions = true, exitCodeOnExecutionException = 34)
    static class CreateUser implements Callable<Integer> {    // class for creating user

        @Option(
                names = "userid",
                required = true,
                description = "save-attachment of message from author",
                defaultValue = "false")
        private String user_id;
        @Override
        public Integer call() throws Exception {
            String public_key="";
//          if()
            Map<String,String> keys = GenerateKeyPair.generateRSAKeyPair();
            if (keys.containsKey("privateKey")) {
                SaveToINI.saveToINIFile(user_id, keys.get("privateKey"));
            }
//          System.out.println("public key ------------------------------\n"+keys.get("publicKey"));
            if(keys.containsKey("publicKey")){
                public_key = keys.get("publicKey");
            }
            String publicKeyPem = encodePublicKeyToPEM(public_key);
            Api.User user = new Api.User(user_id,publicKeyPem);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(user);
//          sendRawJsonPostRequest("localhost:8080/user/create",json);
            ApiService apiService = new ApiService();
            String response = String.valueOf(apiService.sendRawJsonPostRequest(base_url+"user/create",json));
            System.out.println(response);
            return 9;
        }
    }

    @Component
    @Command(name = "post", mixinStandardHelpOptions = true, exitCodeOnExecutionException = 34)
    static class Post implements Callable<Integer>{  // class for sending messages to server which are taken from cli
        @Option(
                names = "message",required = true,
                description = "message from author")
        private String message;
        @Option(
                names = "file-to-attach",
                description = "save-attachment of message from author",
                defaultValue = "false")
        private String file_to_attach;
        @Override
        public Integer call() throws Exception {
            log.debug("post method");
//            System.out.println("hiiii this is post");
//            Api.PostApi postApi = new Api.PostApi(message,file_to_attach);
            LocalDateTime dateTime = LocalDateTime.now();
            String author ;
            String privateKey = retrivePrivateKeyofUser();
            String username = retriveUserId();
            PostMessage postApi = new PostMessage(dateTime.toString(),username,message,file_to_attach,"");
            String signature = CreateSignature.createSignature(postApi,privateKey);
            postApi = new PostMessage(dateTime.toString(),username,message,file_to_attach,signature);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(postApi);
//            System.out.println(json);

//            String json = {
//                    "date": "2024-03-13T19:38-07:00",
//                    "author": "ben",
//                    "message": "hello world!",
//                    "attachment": "aSdlfkJ888oidfjwe+",
//                    "signature": "as/f32230FS+"
//            };

            ApiService apiService = new ApiService();
            String response = String.valueOf(apiService.sendRawJsonPostRequest(base_url+"messages/create",json));
            System.out.println(response);
            return 9;
        }
    }
}

