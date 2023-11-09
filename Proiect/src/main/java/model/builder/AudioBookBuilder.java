package model.builder;

import model.book.audiobook.AudioBook;

import java.time.LocalDate;

public class AudioBookBuilder {
    private final AudioBook book;
    public AudioBookBuilder() {
        book = new AudioBook();
    }
    public AudioBookBuilder setId(Long id){
        book.setId(id);
        return this;
    }
    public AudioBookBuilder setAuthor(String author){
        book.setAuthor(author);
        return this;
    }
    public AudioBookBuilder setTitle(String title){
        book.setTitle(title);
        return this;
    }
    public AudioBookBuilder setPublishedDate(LocalDate publishedDate){
        book.setPublishedDate(publishedDate);
        return this;
    }
    public AudioBookBuilder setRunTime(int runTime){
        book.setRunTime(runTime);
        return this;
    }
    public AudioBook build(){
        return book;
    }
}
