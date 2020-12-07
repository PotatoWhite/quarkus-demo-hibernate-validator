package me.potato.demo.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
  @NotBlank(message="Title is must not be blank")
  private String title;

  @NotBlank(message="Author must not be blank")
  private String author;

  @Min(message="Author has been very lazy", value=1)
  public  double pages;
}
