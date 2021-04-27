package com.workflow.restdemo.application;

public interface Client {
    void doHttpGet(String url);
    void doHttpPost(String url, String body);
    void doHttpDelete(String url);
}
