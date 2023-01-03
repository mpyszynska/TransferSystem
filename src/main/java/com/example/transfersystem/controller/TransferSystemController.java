package com.example.transfersystem.controller;

import com.example.transfersystem.helper.AccountsData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/app/*")
public interface TransferSystemController {

    @RequestMapping(value = "/getAccounts", method = RequestMethod.GET)
    @ResponseBody
    AccountsData getAll();

    @RequestMapping(value = "/transferSystemRequest", method = RequestMethod.POST, produces = "text/xml")
    @ResponseBody
    String transferSystemRequest(@RequestBody String xml, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception;


}
