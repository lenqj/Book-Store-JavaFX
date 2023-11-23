package model.book;

import java.time.LocalDate;

public interface BookInterface {
    Long getId();
    String getAuthor();
    String getTitle();
    LocalDate getPublishedDate();
    Long getStock();
    Long getPrice();
}
