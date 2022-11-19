package com.example.managelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.managelibrary.dto.UserDto;
import com.example.managelibrary.entity.Book;
import com.example.managelibrary.service.BookService;

@Controller
@SessionAttributes("userdto")
public class BookController {
	@Autowired
	private BookService bookService;

	@ModelAttribute("userdto")
	public UserDto userDto() {
		return new UserDto();
	}

	@GetMapping("/books")
	public String listbooks(@ModelAttribute("userdto") UserDto userDto, Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "/books";
	}
	
	//new book
	@GetMapping("/books/new")
	public String createProdcutForm(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		return "save_book";
	}

	@PostMapping("/books")
	public String saveBook(@ModelAttribute("book") Book book) {
		bookService.saveBook(book);
		return "redirect:/books";
	}
	// view book
	@GetMapping("/books/view/{id}")
	public String viewBookForm(@PathVariable Long id, Model model) {
		model.addAttribute("book", bookService.getBookById(id));
		return "view_book";
	}
	//update book
	@GetMapping("/books/edit/{id}")
	public String editBookForm(@PathVariable Long id, Model model) {
		model.addAttribute("book", bookService.getBookById(id));
		return "save_book";
	}

	@PostMapping("/books/{id}")
	public String updateBook(@PathVariable Long id, @ModelAttribute("book") Book book, Model model) {
		Book existingBook = bookService.getBookById(id);
		existingBook.setId(id);
		existingBook.setTitle(book.getTitle());
		existingBook.setAuthor(book.getAuthor());
		existingBook.setDescribes(book.getDescribes());
		existingBook.setReleaseDate(book.getReleaseDate());
		existingBook.setNumberOfPages(book.getNumberOfPages());
		existingBook.setCategory(book.getCategory());
		bookService.updateBook(existingBook);
		return "redirect:/books";
	}

	@GetMapping("/books/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookService.deleteBookById(id);
		return "redirect:/books";
	}
}
