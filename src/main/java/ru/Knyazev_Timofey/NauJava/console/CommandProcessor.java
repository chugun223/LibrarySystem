package ru.Knyazev_Timofey.NauJava.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.Knyazev_Timofey.NauJava.domain.Book;
import ru.Knyazev_Timofey.NauJava.domain.User;
import ru.Knyazev_Timofey.NauJava.service.BookService;
import ru.Knyazev_Timofey.NauJava.service.UserService;

@Component
public class CommandProcessor {

    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public CommandProcessor(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    public void processCommand(String input) {
        String[] cmd = input.split(" ");
        try {
            switch (cmd[0]) {
                case "user-add" -> {
                    userService.createUser(Long.valueOf(cmd[1]), cmd[2], cmd[3], cmd[4]);
                    System.out.println("Пользователь добавлен.");
                }
                case "user-get" -> {
                    User u = userService.findById(Long.valueOf(cmd[1]));
                    System.out.println(u != null ? u : "Пользователь не найден.");
                }
                case "user-list" -> userService.findAll().forEach(System.out::println);
                case "user-delete" -> {
                    userService.deleteById(Long.valueOf(cmd[1]));
                    System.out.println("Пользователь удалён.");
                }
                case "user-update" -> {
                    userService.updateUserInfo(Long.valueOf(cmd[1]), cmd[2], cmd[3], cmd[4]);
                    System.out.println("Данные обновлены.");
                }
                case "book-add" -> {
                    bookService.addBook(Long.valueOf(cmd[1]), cmd[2], cmd[3]);
                    System.out.println("Книга добавлена.");
                }
                case "book-get" -> {
                    Book b = bookService.findBookById(Long.valueOf(cmd[1]));
                    System.out.println(b != null ? b : "Книга не найдена.");
                }
                case "book-list" -> bookService.findAll().forEach(System.out::println);
                case "book-delete" -> {
                    bookService.deleteBook(Long.valueOf(cmd[1]));
                    System.out.println("Книга удалена.");
                }
                case "book-update" -> {
                    bookService.updateBook(Long.valueOf(cmd[1]), cmd[2], cmd[3]);
                    System.out.println("Книга обновлена.");
                }
                case "book-take" -> {
                    boolean ok = bookService.takeBook(Long.valueOf(cmd[1]), Long.valueOf(cmd[2]));
                    System.out.println(ok ? "Книга выдана." : "Не удалось выдать книгу.");
                }
                case "book-return" -> {
                    boolean ok = bookService.returnBook(Long.valueOf(cmd[1]));
                    System.out.println(ok ? "Книга возвращена." : "Не удалось вернуть книгу.");
                }
                case "book-by-user" ->
                        bookService.findBooksByUser(Long.valueOf(cmd[1])).forEach(System.out::println);

                case "help" -> System.out.println("Команды:" +
                            "\nuser-add <id> <fullName> <email> <phone>" +
                            "\nuser-get <id>" +
                            "\nuser-list" +
                            "\nuser-delete <id>" +
                            "\nuser-update <id> <fullName> <email> <phone>" +
                            "\nbook-add <id> <title> <author>" +
                            "\nbook-get <id>" +
                            "\nbook-list" +
                            "\nbook-delete <id>" +
                            "\nbook-update <id> <title> <author>" +
                            "\nbook-take <bookId> <userId>" +
                            "\nbook-return <bookId>" +
                            "\nbook-by-user <userId>" +
                            "\nhelp" +
                            "\nexit ");

                default -> System.out.println("Неизвестная команда. Введите 'help'.");
            }
        } catch (Exception e) {
            System.out.println("ошибка: " + e.getMessage());
        }
    }
}

