package com.example.managelibrary.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

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

	// view book
	@GetMapping("/books/view/{id}")
	public String viewBookForm(@PathVariable Long id, Model model) {
		model.addAttribute("book", bookService.getBookById(id));
		return "view_book";
	}

	// new book
	@GetMapping("/books/new")
	public String createBookForm(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		return "save_book";
	}

	@PostMapping("/books")
	public String saveBook(@ModelAttribute("book") @Valid Book book, BindingResult result, @RequestParam("fileImage") MultipartFile multipartFile)
			throws IOException {
		if (result.hasErrors()) {
            return "save_book";
        }
		
		if(!multipartFile.isEmpty()) {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		book.setPhotos(fileName);
		
		String uploadDir = "./book-photos/" + book.getId();

		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
		}
		bookService.saveBook(book);
		return "redirect:/books";
	}

	// update book
	@GetMapping("/books/edit/{id}")
	public String editBookForm(@PathVariable Long id, Model model) {
		model.addAttribute("book", bookService.getBookById(id));
		return "save_book";
	}

	@PostMapping("/books/{id}")
	public String updateBook(@PathVariable Long id, @ModelAttribute("book") Book book,
			@RequestParam("fileImage") MultipartFile multipartFile, Model model) throws IOException {
		Book existingBook = bookService.getBookById(id);
		existingBook.setId(id);
		existingBook.setTitle(book.getTitle());
		existingBook.setAuthor(book.getAuthor());
		existingBook.setDescribes(book.getDescribes());
		existingBook.setReleaseDate(book.getReleaseDate());
		existingBook.setNumberOfPages(book.getNumberOfPages());
		existingBook.setCategory(book.getCategory());
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			existingBook.setPhotos(fileName);
			String uploadDir = "./book-photos/" + existingBook.getId();
	
			Path uploadPath = Paths.get(uploadDir);
	
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try (InputStream inputStream = multipartFile.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ioe) {
				throw new IOException("Could not save image file: " + fileName, ioe);
			}
		}
		bookService.updateBook(existingBook);
		return "redirect:/books";
	}

	@GetMapping("/books/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookService.deleteBookById(id);
		return "redirect:/books";
	}
}
