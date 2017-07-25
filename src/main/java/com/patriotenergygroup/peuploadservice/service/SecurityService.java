package com.patriotenergygroup.peuploadservice.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}