package ru.Knyazev_Timofey.NauJava.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;

    @Override
    public String toString() {
        return String.format("User{id=%d, name '%s', email: '%s', phoneNumber: %s}", id, fullName, email, phoneNumber);
    }
}