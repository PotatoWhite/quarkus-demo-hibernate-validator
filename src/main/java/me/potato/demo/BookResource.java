package me.potato.demo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.potato.demo.model.Book;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/")
@RequiredArgsConstructor
public class BookResource {

  private final BookService bookService;
  private final Validator   validator;

  @Path("manual-validation")
  @POST
  public Result manualValidation(Book book) {
    Set<ConstraintViolation<Book>> violations=validator.validate(book);
    if(!violations.isEmpty())
      return new Result(violations);

    return new Result("Book is valid!");
  }

  @Path("endpoint-method-validation")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Result endPointValidation(@Valid Book book) {
    return new Result("Book is valid!");
  }

  @Path("service-method-validation")
  @POST
  public Result serviceMethodValidation(Book book) {
    try {
      bookService.validateBook(book);
      return new Result("Book is valid!");
    } catch(ConstraintViolationException e) {
      return new Result(e.getConstraintViolations());
    }
  }

  @Path("locale")
  @GET
  public Locale[] getAvailableLocales() {
    return Locale.getAvailableLocales();
  }

  @Getter
  @Setter
  private class Result {

    private Boolean success=false;
    private String  message;

    public Result(Set<? extends ConstraintViolation<?>> violations) {
      this.success=false;
      this.message=violations.stream()
                             .map(violation -> violation.getMessage())
                             .collect(Collectors.joining(", "));
    }

    public Result(String message) {
      this.success=true;
      this.message=message;
    }
  }
}
