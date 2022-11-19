package com.example.managelibrary.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.managelibrary.entity.Book;
import com.example.managelibrary.reponsitory.BookReponsitory;
import com.example.managelibrary.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
    private BookReponsitory bookReponsitory;

    @Override
    public List<Book> getAllBooks() {
        return bookReponsitory.findAll();
    }

    @Override
    public Book saveBook(Book book) {
        return bookReponsitory.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookReponsitory.findById(id).get();
    }

    @Override
    public Book updateBook(Book book) {
        return bookReponsitory.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookReponsitory.deleteById(id);
    }
}
