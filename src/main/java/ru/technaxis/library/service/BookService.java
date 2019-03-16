package ru.technaxis.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.technaxis.library.dto.BookDto;
import ru.technaxis.library.entity.BookEntity;
import ru.technaxis.library.exception.TitleIsNullException;
import ru.technaxis.library.exception.DocumentNotFoundException;
import ru.technaxis.library.exception.UnsupportedFileContentTypeException;
import ru.technaxis.library.exception.UploadFileException;
import ru.technaxis.library.repository.BookRepository;


import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository repository;
    private final Path uploadPath;

    public BookService(
            BookRepository repository,
            @Value("${spring.resources.static-locations}") String uploadPath
    ) {
        this.repository = repository;
        this.uploadPath = Path.of(URI.create(uploadPath)).resolve("media");
        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BookEntity> getAll() {
        return repository.findAll();
    }

    public BookEntity getById(int id) {
        return repository.findById(id)
                .orElseThrow(DocumentNotFoundException::new);
    }

    public void checkReadAlready(BookDto item) {
        BookEntity entity = getById(item.getId());
        entity.setReadAlready(true);
        repository.save(entity);
    }

    public void save(BookDto item) {
        BookEntity entity = getById(item.getId());
        entity.setTitle(item.getTitle());
        entity.setDescription(item.getDescription());
        entity.setIsbn(item.getIsbn());
        entity.setPrintYear(item.getPrintYear());
        entity.setReadAlready(false);

        if (item.getTitle().length() == 0) {
            throw new TitleIsNullException();
        }

        repository.save(entity);
    }

    public void removeById(int id) {
        repository.deleteById(id);
    }

    public List<BookEntity> findByDescription(String description) {
        return repository.findAllByDescriptionContainsIgnoreCaseOrderByAuthorDesc(description);
    }

}