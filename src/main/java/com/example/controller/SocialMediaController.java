package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public Account registerUser(@RequestBody Account account){
        return accountService.registerUser(account);
    }

    @PostMapping("login")
    public Account login(@RequestBody Account account){
        return accountService.login(account);
    }

    @PostMapping("messages")
    public Message postMessage(@RequestBody Message message){
        return messageService.postMessage(message);
    }

    @GetMapping("messages")
    public List<Message> getAllMessages(){
        return messageService.findAllMessages();
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> findMessageById(@PathVariable int message_id) {
        return ResponseEntity.ok().body(messageService.findMessageById(message_id));
    }

    @DeleteMapping("messages/{message_id}")
    public Integer deleteMessageById(@PathVariable int message_id){
        return messageService.deleteMessageById(message_id);
    }

    @PatchMapping("messages/{message_id}")
    public Integer editMessageById(@PathVariable int message_id, @RequestBody Message message){
        return messageService.updateMessageById(message_id, message);
    }

    @GetMapping("accounts/{account_id}/messages")
    public List<Message> getAllMessagesFromUser(@PathVariable int account_id){
        return messageService.getAllMessagesByPostedBy(account_id);
    }


}
