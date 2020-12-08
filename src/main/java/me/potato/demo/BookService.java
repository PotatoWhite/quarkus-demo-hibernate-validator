package me.potato.demo;

import me.potato.demo.model.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;

@ApplicationScoped
public class BookService {

  public void validateBook(@Valid Book book){

  }
}
