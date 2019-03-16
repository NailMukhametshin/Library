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

    public BookEntity getByIdOrEmpty(int id) {
        return repository.findById(id)
                .orElse(new BookEntity());
    }

    public void save(BookDto item) {
        BookEntity entity = getByIdOrEmpty(item.getId());
        entity.setTitle(item.getTitle());
        entity.setDescription(item.getDescription());
        entity.setAuthor(item.getAuthor());
        entity.setIsbn(item.getIsbn());
        entity.setPrintYear(item.getPrintYear());

        if (item.getTitle().length() == 0) {
            throw new TitleIsNullException();
        }

        MultipartFile file = item.getFile();
        if (!file.isEmpty() && file.getContentType() != null) {
            String ext;
            if (file.getContentType().equals(MediaType.IMAGE_PNG_VALUE)) {
                ext = ".png";
            } else if (file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)) {
                ext = ".jpg";
            } else {
                throw new UnsupportedFileContentTypeException();
            }

            String name = UUID.randomUUID().toString() + ext;

            try {
                file.transferTo(uploadPath.resolve(name));

                if (entity.getPath() != null) {
                    Files.deleteIfExists(uploadPath.resolve(entity.getPath()));
                }
            } catch (IOException e) {
                throw new UploadFileException();
            }

            entity.setPath(name);
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