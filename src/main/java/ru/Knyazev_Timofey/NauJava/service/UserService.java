package ru.Knyazev_Timofey.NauJava.service;

import ru.Knyazev_Timofey.NauJava.domain.User;

import java.util.List;

public interface UserService
{
    void createUser(Long id, String fullName, String email, String phoneNumber);
    User findById(Long id);
    void deleteById(Long id);
    void updateUserInfo(Long id, String fullName, String email, String phoneNumber);
    List<User> findAll();
}