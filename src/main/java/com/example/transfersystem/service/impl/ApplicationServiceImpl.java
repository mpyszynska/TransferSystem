package com.example.transfersystem.service.impl;

import com.example.transfersystem.helper.AccountsData;
import com.example.transfersystem.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;


@Component
public class ApplicationServiceImpl {

    @Autowired
    private ApplicationArguments args;

    @Autowired
    private AccountService accountService;

    public static String PATH ="";

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String path = Arrays.stream(args.getSourceArgs()).findFirst().get();
        PATH = path;
        String json = readFileAsString(path);

        try {
            AccountsData data = mapper.readValue(json, AccountsData.class);
            accountService.saveAccountsDb(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
