package ru.technaxis.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.technaxis.library.entity.BookEntity;
import ru.technaxis.library.repository.BookRepository;

import java.util.List;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(LibraryApplication.class);
        var bookRepository = context.getBean(BookRepository.class);

        bookRepository.saveAll(List.of(
                new BookEntity(
                        1,
                        "\"Бойцовский клуб\"",
                        "Интриги. Хаос. Мыло.",
                        "Чак Паланик",
                        "978-5-17-016682-4",
                        1996,
                        true,
                        "FightClub.jpg"),
                new BookEntity(
                        2,
                        "\"Над пропастью во ржи\"",
                        "Герой повести современного американского прозаика подросток, только вступающий в жизнь, не хочет подчиняться ханжеским законам мира взрослых. С юношеской непримиримостью он восстает против них и сам остро переживает создаваемые конфликты.",
                        "Джером Сэлинджер",
                        "978-5-699-30534-6",
                        1951,
                        true,
                        "TheCatcherInTheRye.jpg"),
                new BookEntity(
                        3,
                        "\"Чапаев и Пустота\"",
                        "Действие книги происходит в 1919 году в дивизии Чапаева, в которой главный герой, поэт-декадент Петр Пустота, служит комиссаром, а также в наши дни, а также, как и всегда у Пелевина, в виртуальном пространстве",
                        "Виктор Пелевин",
                        "5-9560-0083-Х",
                        2003,
                        false,
                        "Chapaev.jpg")
        ));
    }
}
