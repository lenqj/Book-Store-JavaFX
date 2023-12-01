package repository.book;

import model.book.BookInterface;
import model.validator.Notification;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryMock implements BookRepository<BookInterface>{
    private final List<BookInterface> books;

    public BookRepositoryMock(){
        books = new ArrayList<>();
    }
    public List<BookInterface> findAll() {
        return books;
    }

    @Override
    public List<BookInterface> findAllSellableBooks(Boolean flag) {
        return null;
    }

    @Override
    public Notification<BookInterface> findById(Long findID) {
        return null;
        //return books.parallelStream().filter(it -> it.getId().equals(findID)).findFirst();
    }

    @Override
    public Notification<Boolean> save(BookInterface book) {
        return null;
        //return books.add(book);
    }
    public boolean badSave(BookInterface book) {
        return books.add(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }

    @Override
    public Notification<BookInterface> updateStock(BookInterface book, Long stock) {

        return null;
    }

    @Override
    public void updatePrice(BookInterface book, Long Price) {

    }

    @Override
    public Notification<Boolean> sell(BookInterface book) {
        return null;
    }

    @Override
    public Notification<Boolean> delete(BookInterface book) {
        return null;
    }

    @Override
    public Notification<BookInterface> update(BookInterface book, String author, String title, Date publishedDate, Long stock, Long price) {
        return null;
    }
}
