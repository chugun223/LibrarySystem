package ru.Knyazev_Timofey.NauJava.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.Knyazev_Timofey.NauJava.domain.Book;
import ru.Knyazev_Timofey.NauJava.domain.User;

import java.util.List;

@Component
public class UserRepository implements UserCrudRepository{
    private final List<User> userContainer;

    @Autowired
    public UserRepository(List<User> userContainer){
        this.userContainer = userContainer;
    }

    @Override
    public void create(User user) {
        userContainer.add(user);
    }

    @Override
    public User read(Long id) {
        return userContainer.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < userContainer.size(); i++) {
            if (userContainer.get(i).getId().equals(user.getId())) {
                userContainer.set(i, user);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        userContainer.removeIf(b -> b.getId().equals(id));
    }

    public List<User> findAll() {
        return userContainer;
    }
}
