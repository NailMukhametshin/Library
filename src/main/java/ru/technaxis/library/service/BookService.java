package ru.technaxis.library.service;

import ru.technaxis.library.dto.BookDto;
import ru.technaxis.library.entity.BookEntity;

import java.util.List;

public interface BookService {
    List<BookEntity> getAll();

    BookEntity getById(int id);

    void checkReadAlready(BookDto item);

    void save(BookDto item);

    BookEntity Empty();

    void add(BookDto item);

    void removeById(int id);

    List<BookEntity> findByDescription(String description);
}
