package model.book.audiobook;

import model.book.Book;

public class AudioBook extends Book implements AudioBookInterface {
    private int runTime;
    public String toString(){
        return String.format("%s | runtime: %s", super.toString(), runTime);
    }
    public int getRunTime() {
        return runTime;
    }
    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }
}
