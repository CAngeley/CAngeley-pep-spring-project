package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageService {
    public MessageRepository messageRepository;
    public AccountService accountService;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService){
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    public Message postMessage(Message message){
        if(accountExists(message) && isMessageTextSatisfactory(message)){
            return messageRepository.save(message);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public boolean accountExists (Message message){
        return accountService.findUser(message.getPosted_by()) != null;
    }

    public boolean isMessageTextSatisfactory(Message message){
        return !message.getMessage_text().isEmpty() && message.getMessage_text().length() <= 255;
    }

    public List<Message> findAllMessages(){
        return messageRepository.findAll();
    }

    public Message findMessageById(int id){
        Optional<Message> messageOptional = messageRepository.findById(id);
        return messageOptional.orElse(null);
    }

    @Transactional
    public Integer deleteMessageById(int id){
        Optional<Message> messageOptional = messageRepository.findById(id);
        if(messageOptional.isPresent()){
            messageRepository.deleteById(id);
            return 1;
        } else {
            return null;
        }
    }

    @Transactional
    public Integer updateMessageById(int id, Message message){
        Message oldMessage = findMessageById(id);
        if(oldMessage != null && isMessageTextSatisfactory(message)){
            messageRepository.updateMessageById(id, message.getMessage_text());
            return 1;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<Message> getAllMessagesByPostedBy(int id){
        return messageRepository.findAllMessagesByPostedBy(id);
    }
}
