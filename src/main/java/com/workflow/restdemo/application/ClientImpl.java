package com.workflow.restdemo.application;

import java.net.URI;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ClientImpl implements Client {
    @Autowired @Qualifier("restfulWithSSL")
    private RestTemplate restful;
    
    // List users
    @Override
    public void doHttpGet(String url) {
        HttpHeaders httpHeaders = buildHttpHeaders();
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (Exception ex) {
            System.out.println(ExceptionUtils.getStackTrace(ex));
            return ;
        }
        ResponseEntity<String> respEntity = 
                restful.exchange(uri, HttpMethod.GET, new HttpEntity(httpHeaders), String.class);
        //ResponseEntity<String> respEntity = restful.getForEntity(uri, String.class);
        showHttpDetial(respEntity);
    }
    
    @Override
    public void queryUserbyName(String url, String name) {
        UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(url);
        MultiValueMap<String, String> paramters = new LinkedMultiValueMap<String, String>();
        paramters.add("name", name);
        ucb.queryParams(paramters);
        System.out.println("ucb=" + ucb.toUriString());
        
        doHttpGet(ucb.toUriString());
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.ACCEPT_CHARSET, "utf-8");
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.setBearerAuth("ACCESS-TOKEN");
        httpHeaders.forEach((k,v) -> System.out.println(k + ":" + v));
        return httpHeaders;
    }
    
    // Create user
    @Override
    public void doHttpPost(String url, String body) {
        HttpHeaders httpHeaders = buildHttpHeaders();
        
        RequestEntity<String> reqEntity = 
                RequestEntity.method(HttpMethod.POST, url)
                .headers(httpHeaders).body(body, String.class);
        ResponseEntity<String> respEntity = restful.exchange(reqEntity, String.class);
        showHttpDetial(respEntity);
    }

    // Delete user
    @Override
    public void doHttpDelete(String url) {
        HttpHeaders httpHeaders = buildHttpHeaders();
        
        RequestEntity<Void> reqEntity = 
                RequestEntity.method(HttpMethod.DELETE, url)
                .headers(httpHeaders).build();
        ResponseEntity<String> respEntity = restful.exchange(reqEntity, String.class);
        showHttpDetial(respEntity);      
    }

    // Update user
    @Override
    public void doHttpPatch(String url, String body) {
        HttpHeaders httpHeaders = buildHttpHeaders();
        
        RequestEntity<String> reqEntity = 
                RequestEntity.method(HttpMethod.PATCH, url)
                .headers(httpHeaders).body(body, String.class);
        ResponseEntity<String> respEntity = restful.exchange(reqEntity, String.class);
        showHttpDetial(respEntity);
    }
    
    private void showHttpDetial(ResponseEntity<?> respEntity) {
        System.out.println("response status code:" + respEntity.getStatusCode());
        System.out.println("response header:" + respEntity.getHeaders().entrySet());
        System.out.println("response status body:" + respEntity.getBody());
    }
}
