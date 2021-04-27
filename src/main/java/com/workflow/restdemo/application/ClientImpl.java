package com.workflow.restdemo.application;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientImpl implements Client {
    @Autowired @Qualifier("restfulWithSSL")
    private RestTemplate restful;
    
    public void doHttpGet(String url) {
        ResponseEntity<String> response = null;
        try {
            URI uri = new URI(url);
            response = restful.getForEntity(uri, String.class);
        } catch (Exception ex) {
            System.out.println(ExceptionUtils.getStackTrace(ex));
            return ;
        }
        System.out.println("response status code:" + response.getStatusCode());
        System.out.println("response header:" + response.getHeaders().entrySet());
        System.out.println("response status body:" + response.getBody());
    }

    @Override
    public void doHttpPost(String url, String body) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void doHttpDelete(String url) {
        // TODO Auto-generated method stub
        
    }
}
