package ru.Knyazev_Timofey.NauJava.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Book {
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.holderId = null;
    }

    private Long id;
    private String title;
    private String author;
    private Boolean isAvailable;
    private Long holderId;

    @Override
    public String toString() {
        return String.format("Book{id = %d, title: '%s', author: '%s', available=%s, takenId=%s}", id, title, author, isAvailable, holderId);
    }
}
