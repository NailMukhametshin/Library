package ru.technaxis.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.technaxis.library.dto.BookDto;
import ru.technaxis.library.entity.BookEntity;
import ru.technaxis.library.exception.*;
import ru.technaxis.library.repository.BookRepository;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private final Path uploadPath;

    public BookServiceImpl(
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

    @Override
    public List<BookEntity> getAll(int page, int size) {
        Page<BookEntity> all = repository.findAll(PageRequest.of(page - 1, size));
        return all.getContent();
    }

    @Override
    public List<Integer> paginationPageList(int size) {
        ArrayList<Integer> integers = new ArrayList<>();
        int booksQuantity = repository.findAll().size();
        int pageQuantity;
        if (booksQuantity % size != 0){
            pageQuantity = booksQuantity / size + 1;
        }else{
            pageQuantity = booksQuantity / size;
        }
        for (int i = 1; i <= pageQuantity; i++) {
            integers.add(i);
        }
        return integers;
    }

    @Override
    public BookEntity getById(int id) {
        return repository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public void checkReadAlready(BookDto item) {
        BookEntity entity = getById(item.getId());
        entity.setReadAlready(true);
        repository.save(entity);
    }

    @Override
    public void save(BookDto item) {
        BookEntity entity = getById(item.getId());
        entity.setTitle(item.getTitle());
        entity.setDescription(item.getDescription());
        entity.setIsbn(item.getIsbn());
        entity.setPrintYear(item.getPrintYear());
        entity.setReadAlready(false);

        if (item.getTitle().length() == 0) {
            throw new InvalidLenghtException();
        }

        if (item.getDescription().length() >= 255) {
            throw new LongDescriptionException();
        }

        repository.save(entity);
    }

    @Override
    public BookEntity Empty() {
        return new BookEntity();
    }

    @Override
    public void add(BookDto item) {
        BookEntity entity = new BookEntity();
        entity.setTitle(item.getTitle());
        entity.setDescription(item.getDescription());
        entity.setAuthor(item.getAuthor());
        entity.setIsbn(item.getIsbn());
        entity.setPrintYear(item.getPrintYear());
        entity.setReadAlready(item.isReadAlready());

        if (item.getTitle().length() == 0) {
            throw new InvalidLenghtException();
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

    @Override
    public void removeById(int id) {
        repository.deleteById(id);
    }

    @Override
    public List<BookEntity> findByDescription(String description) {
        return repository.findAllByDescriptionContainsIgnoreCaseOrderByAuthorDesc(description);
    }

}