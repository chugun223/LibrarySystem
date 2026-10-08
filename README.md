Library System

Консольное приложение для управления библиотекой. Ведётся учёт книг и пользователей: добавление, просмотр, обновление, удаление, выдача книг пользователям и возврат их.

Реализованы классы-сущности Book User 
Реализован слой доступа к данным BookRepository UserRepository 
Реализована бизнес-логика работы с сущностями BookService UserService 
Реализован консольный ввод для работы с функциями приложения console/CommandProcessor config/ConsoleConfig 
Классы и интерфейсы разделены по пакетам domain repository service console config 
После загрузки приложения в консоль выводятся имя и версия приложения 
Настроен мониторинг есть возможность получить метрики с помощью actuator

Команды консольного приложения

-user-add <id> <fullName> <email> <phone>
-user-get <id>
-user-list
-user-delete <id>
-user-update <id> <fullName> <email> <phone>
-book-add <id> <title> <author>
-book-get <id>
-book-list
-book-delete <id>
-book-update <id> <title> <author>
-book-take <bookId> <userId>
-book-return <bookId>
-book-by-user <userId>
-help
-exit