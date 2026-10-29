package ru.Knyazev_Timofey.NauJava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.Knyazev_Timofey.NauJava.domain.Author;
import ru.Knyazev_Timofey.NauJava.domain.Book;
import ru.Knyazev_Timofey.NauJava.domain.Genre;

import java.util.List;

@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public BookRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Book> findByAuthorLastName(String lastName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = criteriaQuery.from(Book.class);

        Join<Book, Author> author = bookRoot.join("author", JoinType.INNER);
        Predicate authorPredicate = criteriaBuilder.equal(author.get("lastName"), lastName);
        criteriaQuery.select(bookRoot).where(authorPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Book> findByGenreName(String genreName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = criteriaQuery.from(Book.class);
        Join<Book, Genre> genre = bookRoot.join("genre", JoinType.INNER);

        Predicate genrePredicate = criteriaBuilder.equal(genre.get("name"), genreName);
        criteriaQuery.select(bookRoot).where(genrePredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Book> findByPublicationYearBetweenAndIsAvailableTrue(Integer from, Integer to) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = criteriaQuery.from(Book.class);
        Predicate yearPredicate = criteriaBuilder.between(bookRoot.get("publicationYear"), from, to);
        Predicate availablePredicate = criteriaBuilder.isTrue(bookRoot.get("isAvailable"));
        criteriaQuery.select(bookRoot).where(criteriaBuilder.and(yearPredicate, availablePredicate));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Book> findAllAvailableWithAuthorAndGenre() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = criteriaQuery.from(Book.class);
        bookRoot.fetch("author", JoinType.INNER);
        bookRoot.fetch("genre", JoinType.INNER);
        Predicate availablePredicate = criteriaBuilder.isTrue(bookRoot.get("isAvailable"));
        criteriaQuery.select(bookRoot).where(availablePredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}