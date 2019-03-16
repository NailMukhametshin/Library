package ru.technaxis.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.technaxis.library.entity.BookEntity;
import ru.technaxis.library.repository.BookRepository;

import java.util.List;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(LibraryApplication.class, args);
        var bookRepository = context.getBean(BookRepository.class);

        bookRepository.saveAll(List.of(new BookEntity(
                1,
                "'Бойцовский клуб'",
                "- Лишь утратив всё до конца, мы обретаем свободу. - Если можно проснуться в другом времени, и в другом месте, нельзя ли проснуться другим человеком? - Интриги. Хаос. Мыло.",
                "Чак Паланик",
                "978-5-17-016682-4",
                1996,
                false,
                "FightClub.jpg"
        )));
    }

}
