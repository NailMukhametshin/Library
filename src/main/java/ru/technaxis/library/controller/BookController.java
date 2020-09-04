package ru.technaxis.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.technaxis.library.dto.BookDto;
import ru.technaxis.library.service.BookServiceImpl;

@Controller
@RequestMapping("/")
public class BookController {
    private final BookServiceImpl service;

    public BookController(BookServiceImpl service) {
        this.service = service;
    }

    @GetMapping()
    public String getAll(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        model.addAttribute("items", service.getAll(page, size));
        model.addAttribute("pageList", service.paginationPageList(size));
        model.addAttribute("currentPageNumber", page);
        model.addAttribute("pageSize", size);
        return "all";
    }

    @GetMapping("/{id}/add")
    public String add(@PathVariable int id, Model model) {
        model.addAttribute("item", service.Empty());
        return "add";
    }

    @PostMapping("/{id}/add")
    public String add(
            @PathVariable int id,
            @ModelAttribute BookDto dto
    ) {
        service.add(dto);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute("item", service.getById(id));
        return "view";
    }

    @PostMapping("/{id}")
    public String checkReadAlready(
            @PathVariable int id,
            @ModelAttribute BookDto dto
    ) {
        service.checkReadAlready(dto);
        return "redirect:/{id}";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("item", service.getById(id));
        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable int id,
            @ModelAttribute BookDto dto
    ) {
        service.save(dto);
        return "redirect:/{id}";
    }

    @GetMapping("/{id}/remove")
    public String remove(
            @PathVariable int id,
            Model model
    ) {
        model.addAttribute("item", service.getById(id));
        return "remove";
    }

    @PostMapping("/{id}/remove")
    public String remove(
            @PathVariable int id
    ) {
        service.removeById(id);
        return "redirect:/";
    }

    @GetMapping(value = "/search", params = "description")
    public String search(@RequestParam String description, Model model) {
        model.addAttribute("description", description);
        model.addAttribute("items", service.findByDescription(description));
        return "all";
    }

}