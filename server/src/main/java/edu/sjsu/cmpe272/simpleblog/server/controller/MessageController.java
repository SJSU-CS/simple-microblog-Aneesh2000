package edu.sjsu.cmpe272.simpleblog.server.controller;


import edu.sjsu.cmpe272.simpleblog.server.Message;
import edu.sjsu.cmpe272.simpleblog.server.MessageRepository;
import edu.sjsu.cmpe272.simpleblog.server.UserService;
import edu.sjsu.cmpe272.simpleblog.server.VerifySignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createMessage(@RequestBody Message message) throws Exception {
        log.debug("Create message ="+message);
        PublicKey publicKey = UserService.getPublicKeyFromString(UserService.userPublicKeys.get(message.getAuthor()));

        if(publicKey!=null && VerifySignature.verifySignature(message,publicKey) ){
//            System.out.println("verfy signature "+VerifySignature.verifySignature(message,publicKey));
            Message savedMessage = messageRepository.save(message);
            return ResponseEntity.ok(Map.of("message-id", savedMessage.getMessageId()));
        }
        else{
            log.error("failed to create message ="+message);
            return ResponseEntity.ok(Map.of("error", "failed to create message"));
        }


    }

    @PostMapping("/list")
    public ResponseEntity<?> listMessages(@RequestBody Map<String, Object> params) {
        log.debug("list messages, params ="+params);
        Integer limit = (Integer) params.getOrDefault("countnumber", 10);
//        Integer next = (Integer) params.getOrDefault("next",-1);
        Integer starting_id = (Integer) params.getOrDefault("starting_id",-1);
        if (limit > 20) {
            return ResponseEntity.badRequest().body("Error: Limit cannot be greater than 20");
        }
        List<Message> messages;
        if(starting_id>=0){
            messages = messageRepository.findByMessageIdBetween(starting_id-limit,limit);
        }
        else{
            messages=messageRepository.findAllByOrderByMessageIdDesc();
            if (messages.size() > limit) {
                messages = messages.subList(starting_id, limit);
            }
        }
        return ResponseEntity.ok(messages);
    }
}

