package repository.book;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository{
    private final List<Book> books;

    public BookRepositoryMock(){
        books = new ArrayList<>();
    }
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long findID) {
        return books.parallelStream().filter(it -> it.getId().equals(findID)).findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }
    public boolean badSave(Book book) {
        return books.add(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }
}
