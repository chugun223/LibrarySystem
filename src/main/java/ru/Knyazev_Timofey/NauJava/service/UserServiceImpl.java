package ru.Knyazev_Timofey.NauJava.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Knyazev_Timofey.NauJava.config.InfoConfig;
import ru.Knyazev_Timofey.NauJava.domain.User;
import ru.Knyazev_Timofey.NauJava.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final InfoConfig infoConfig;

    @PostConstruct
    public void printAppInfo() {
        System.out.println(infoConfig.getAppName() + " | version=" + infoConfig.getAppVersion());
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository, InfoConfig infoConfig) {
        this.userRepository = userRepository;
        this.infoConfig = infoConfig;
    }

    @Override
    public void createUser(Long id, String fullName, String email, String phoneNumber) {
        User user = new User();
        user.setId(id);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        userRepository.create(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.read(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.delete(id);
    }

    @Override
    public void updateUserInfo(Long id, String fullName, String email, String phoneNumber) {
        User user = userRepository.read(id);
        if (user == null) {
            return;
        }
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        userRepository.update(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
