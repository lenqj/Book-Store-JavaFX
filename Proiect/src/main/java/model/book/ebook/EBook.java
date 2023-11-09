package model.book.ebook;

import model.book.Book;

public class EBook extends Book implements EBookInterface {
    private String format;
    public String toString(){
        return String.format("%s | format: %s", super.toString(), format);
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
}
