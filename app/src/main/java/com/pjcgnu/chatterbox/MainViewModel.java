package com.pjcgnu.chatterbox;

public class MainViewModel {
    private String bookName;
    private String bookImage;
    private String bookWriter;
    private float bookRating;

    public MainViewModel(String bookNames, String bookImages, String bookWriters, float bookRatings){
        this.bookName = bookNames;
        this.bookImage = bookImages;
        this.bookWriter = bookWriters;
        this.bookRating = bookRatings;
    }

    public String getBookName()
    {
        return this.bookName;
    }

    public String getBookImage()
    {
        return this.bookImage;
    }

    public String getBookWriter()
    {
        return this.bookWriter;
    }

    public float getBookRating()
    {
        return this.bookRating;
    }
}
