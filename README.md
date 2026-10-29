Library System

Практическая работа №5

В все интерфейсы репозиториев (кроме служебных) была добавлена аннотация @RepositoryRestResource.
Благодаря этому Spring Data REST автоматически создал эндпоинты для CRUD операций над сущностями. Теперь доступны стандартные http-методы: GET, POST, PUT, PATCH, DELETE.

Для кастомных методов репозиториев, созданных в прошлой практической работе, были написаны собственные REST-контроллеры в пакете controller

Был реализован веб контроллер BookViewController и html шаблон bookList.html в папке templates с использованием Thymeleaf.
Страница доступна по адресу http://localhost:8080/view/books/list
На странице отображается таблица со списком всех книг.

Для работы с ошибками создан класс ApiException по примеру из методички к практической для стандартного ответа об ошибке. Создан обработчик GlobalExceptionHandler с аннотацией @RestControllerAdvice.
Он перехватывает исключения и возвращает клиенту ответы единого формата:
IllegalArgumentException - 400 Bad Request
IllegalStateException - 409 Conflict
EntityNotFoundException - 404 Not Found
Exception - 500 Internal Server Error (для всех остальных ошибок)

Доступен интерфейс Swagger UI благодаря зависимости springdoc-openapi-starter-webmvc-ui.
Он автоматом документирует  эндпоинты, которые сгенерированы spring data rest и методы из @RestController.

В проект добавлена миграция liquibase test-data.xml, которая автоматически наполняет БД тестовыми данными(5 книг, 3 автора, 3 жанра, 5 пользователей, 3 выдачи).

Запуск
Для запуска приложения сначала нужно создать файл .env. Его можно скопировать из .env.example
После этого нужно выполнить в консоли команду:
docker-compose up -d
которая поднимет контейнер с PostgreSQL. Затем можно запустить само приложение.

Приложение доступно по адресу http://localhost:8080
HTML страница со списком книг доступна по адресу http://localhost:8080/view/books/list
Swagger UI доступен по адресу по умолчанию http://localhost:8080/swagger-ui/index.html

