package com.ssafy.queant.model.service;

public interface MemberService {
    String register(String email, String password, String name);
    String login(String id, String password) throws RuntimeException;
}
