package com.example.transfersystem.controller;

import com.example.exercises.transfersystem.transfer_request_response.*;
import com.example.transfersystem.helper.AccountHelper;
import com.example.transfersystem.helper.AccountsData;
import com.example.transfersystem.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.xml.sax.SAXException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.List;


@Controller
public class TransferSystemControllerImpl implements TransferSystemController {

    @Autowired
    private AccountService accountService;


    @Override
    public AccountsData getAll() {
        AccountsData data = new AccountsData();
        List<AccountHelper> allAccounts =  accountService.getAllAccounts();
        data.setAccounts(allAccounts);
        return data;
    }

    public TransferRequest unmarshall(String xmlString) throws JAXBException, IOException, SAXException {
        JAXBContext context = JAXBContext.newInstance(TransferRequest.class);
        Unmarshaller us = context.createUnmarshaller();
        SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File xsd = new File("src/main/resources/xsd/transfer-request-response.xsd");
        Schema schema = sf.newSchema(xsd);
        us.setSchema(schema);
        return (TransferRequest) us
                .unmarshal(new StringReader(xmlString));
    }

    public String marshal(TransferRequest request, String outcome) throws JAXBException, IOException {
        TransferResponse response = new TransferResponse();
        response.setCurrency(request.getCurrency());
        response.setAction(request.getAction());
        response.setQuantity(request.getQuantity());
        response.setTargetAccountNumber(request.getTargetAccountNumber());
        response.setOutcome(OutcomeType.valueOf(outcome));

        JAXBContext context = JAXBContext.newInstance(TransferResponse.class);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        mar.marshal(response, sw);

        return sw.toString();
    }


    @Override
    public String transferSystemRequest(String xml, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        TransferRequest request = unmarshall(xml);
        String outcome = accountService.actionsOnAccount(request);
        String xmlResponse = marshal(request, outcome);

        return xmlResponse;
    }
}
