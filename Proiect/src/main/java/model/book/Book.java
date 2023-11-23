package model.book;

import java.time.LocalDate;

// POJO - Plain Old Java Object
//Java Bean
public class Book implements BookInterface {
    private Long id;
    private String author;
    private String title;
    private LocalDate publishedDate;
    private Long stock;
    private Long price;
    public Long getId() {
        return id;
    }
    public String toString(){
        return String.format("#%s - Book author: %s | title: %s | Published Date: %s", id, author, title, publishedDate);
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDate getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
