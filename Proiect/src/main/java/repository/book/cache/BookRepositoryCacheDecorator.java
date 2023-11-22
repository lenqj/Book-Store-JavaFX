package repository.book.cache;

import repository.Cache;
import repository.book.BookRepository;

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

    public Optional<T> findById(Long id) {
        return decoratedRepository.findById(id);
    }

    public boolean save(T book) {
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    @Override
    public boolean badSave(T book) {
        return false;
    }

    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }
}
