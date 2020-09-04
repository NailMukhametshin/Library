package ru.technaxis.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.technaxis.library.entity.BookEntity;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    List<BookEntity> findAllByDescriptionContainsIgnoreCaseOrderByAuthorDesc(String description);
}

