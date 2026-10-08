package ru.Knyazev_Timofey.NauJava.repository;

import ru.Knyazev_Timofey.NauJava.domain.User;

public interface UserCrudRepository
{
    void create(User user);
    User read(Long id);
    void update(User user);
    void delete(Long id);
}
