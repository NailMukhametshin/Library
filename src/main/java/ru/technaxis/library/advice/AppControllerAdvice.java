package ru.technaxis.library.advice;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.technaxis.library.exception.*;

@ControllerAdvice
public class AppControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public String handleBookNotFound(Model model) {
        model.addAttribute("message", "Документ не найден");
        return "error";
    }

    @ExceptionHandler(UploadFileException.class)
    public String handleUploadFile(Model model) {
        model.addAttribute("message", "Не удалось загрузить файл");
        return "error";
    }

    @ExceptionHandler(UnsupportedFileContentTypeException.class)
    public String handleUnsupportedFileContentType(
            Model model) {
        model.addAttribute("message", "Расширение файла не поддерживается");
        return "error";
    }

    @ExceptionHandler(InvalidLenghtException.class)
    public String handleInvalidLenght(
            Model model) {
        model.addAttribute("message", "Пустое поле 'Название книги'");
        return "error";
    }

    @ExceptionHandler(LongDescriptionException.class)
    public String handleLongDescription(
            Model model) {
        model.addAttribute("message", "Описание больше 255 символов");
        return "error";
    }
}
