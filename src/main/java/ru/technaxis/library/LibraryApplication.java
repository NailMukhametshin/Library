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
                        "Chapaev.jpg"),
                new BookEntity(
                        4,
                        "\"Ночной дозор\"",
                        "Мы — Иные.\n" +
                                "Мы создаём Ночной Дозор,\n" +
                                "Чтобы силы Света следили за силами Тьмы.\n" +
                                "Мы — Иные.\n" +
                                "Мы создаём Дневной Дозор,\n" +
                                "Чтобы силы Тьмы следили за силами Света.\n" +
                                "Время решит за нас.",
                        "Сергей Лукьяненко",
                        "5-237-01511-5",
                        1999,
                        false,
                        "NightGuard.jpg"),
                new BookEntity(
                        5,
                        "\"Скотный двор\"",
                        "Изданная в 1945 году сатирическая антиутопия, Джорджа Оруэлла. В повести изображена эволюция состояния животных, изгнавших со скотного двора его предыдущего владельца, от безграничной свободы к диктатуре свиньи по кличке Наполеон.",
                        "Джордж Оруэлл",
                        "978-5-17-083387-0",
                        1949,
                        false,
                        "AnimalFarm.jpg"),
                new BookEntity(
                        6,
                        "\"Танатонавты\"",
                        "Эти господа — летчики-испытатели, которые отправляются на тот свет… Та-на-то-нав-ты. От греческого «танатос» — смерть и «наутис» — мореплаватель. Танатонавты",
                        "Бернард Вербер",
                        "5-9578-3418-1",
                        2006,
                        false,
                        "Thanatonautes.jpg"),
                new BookEntity(
                        7,
                        "\"Любовь к жизни\"",
                        "Канадская тайга, золото, волк и любовь к жизни...",
                        "Джек Лондон",
                        "978-5-699-21911-7",
                        1905,
                        false,
                        "LoveOfLive.jpg")
        ));
    }
}
