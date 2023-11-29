package repository.book;

import model.book.BookInterface;
import model.validator.Notification;

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
    public List<BookInterface> findAllSellableBooks() {
        return null;
    }

    @Override
    public Notification<BookInterface> findById(Long findID) {
        return null;
        //return books.parallelStream().filter(it -> it.getId().equals(findID)).findFirst();
    }

    @Override
    public boolean save(BookInterface book) {
        return books.add(book);
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
}
