package repository.book.cache;

import repository.book.BookRepository;

public abstract class BookRepositoryDecorator<T> implements BookRepository<T> {

    protected BookRepository<T> decoratedRepository;

    public BookRepositoryDecorator(BookRepository<T> bookRepository){
        this.decoratedRepository = bookRepository;
    }

}
