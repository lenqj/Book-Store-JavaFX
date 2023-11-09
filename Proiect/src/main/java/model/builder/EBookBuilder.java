package model.builder;


import model.book.ebook.EBook;

import java.time.LocalDate;

public class EBookBuilder {
    private final EBook book;
    public EBookBuilder() {
        book = new EBook();
    }
    public EBookBuilder setId(Long id){
        book.setId(id);
        return this;
    }
    public EBookBuilder setAuthor(String author){
        book.setAuthor(author);
        return this;
    }
    public EBookBuilder setTitle(String title){
        book.setTitle(title);
        return this;
    }
    public EBookBuilder setPublishedDate(LocalDate publishedDate){
        book.setPublishedDate(publishedDate);
        return this;
    }
    public EBookBuilder setFormat(String format){
        book.setFormat(format);
        return this;
    }
    public EBook build(){
        return book;
    }
}
