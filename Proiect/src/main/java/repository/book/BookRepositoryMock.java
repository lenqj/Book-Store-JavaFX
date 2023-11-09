package repository.book;

import model.book.Book;
import model.book.BookInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository<BookInterface>{
    private final List<BookInterface> books;

    public BookRepositoryMock(){
        books = new ArrayList<>();
    }
    public List<BookInterface> findAll() {
        return books;
    }

    @Override
    public Optional<BookInterface> findById(Long findID) {
        return books.parallelStream().filter(it -> it.getId().equals(findID)).findFirst();
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
}
