package repository.book.cache;

import model.book.BookInterface;
import repository.Cache;
import repository.book.BookRepository;

import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecorator extends BookRepositoryDecorator {
    private Cache<BookInterface> cache;

    public BookRepositoryCacheDecorator(BookRepository bookRepository, Cache<BookInterface> cache){
        super(bookRepository);
        this.cache = cache;
    }
    public List<BookInterface> findAll() {
        if(cache.hasResult()){
            return cache.load();
        }
        List<BookInterface> books = decoratedRepository.findAll();
        cache.save(books);
        return books;
    }

    public Optional<BookInterface> findById(Long id) {
        return decoratedRepository.findById(id);
    }

    public boolean save(BookInterface book) {
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    @Override
    public boolean badSave(BookInterface book) {
        return false;
    }

    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }
}
