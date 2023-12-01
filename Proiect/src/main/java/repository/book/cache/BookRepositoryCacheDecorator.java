package repository.book.cache;

import model.book.BookInterface;
import model.validator.Notification;
import repository.Cache;
import repository.book.BookRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecorator<T> extends BookRepositoryDecorator<T> {
    private Cache<T> cache;

    public BookRepositoryCacheDecorator(BookRepository<T> bookRepository, Cache<T> cache){
        super(bookRepository);
        this.cache = cache;
    }
    public List<T> findAll() {
        if(cache.hasResult()){
            return cache.load();
        }
        List<T> books = decoratedRepository.findAll();
        cache.save(books);
        return books;
    }

    @Override
    public List<T> findAllSellableBooks(Boolean flag) {
        return null;
    }

    public Notification<T> findById(Long id) {
        return decoratedRepository.findById(id);
    }

    public Notification<Boolean> save(T book) {
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    public boolean badSave(T book) {
        return false;
    }

    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }

    public Notification<T> updateStock(T book, Long stock) {
        return null;
    }

    public void updatePrice(T book, Long Price) {

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
