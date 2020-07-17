package com.boot.bootdemo.entity;

import java.util.Date;
import java.util.Objects;

public class Book {

    private String bookid;
    private String bookName;
    private String publisher;
    private Double price;
    private Date publishDate;

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookid='" + bookid + '\'' +
                ", bookName='" + bookName + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price=" + price +
                ", publishDate=" + publishDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getBookid(), book.getBookid()) &&
                Objects.equals(getBookName(), book.getBookName()) &&
                Objects.equals(getPublisher(), book.getPublisher()) &&
                Objects.equals(getPrice(), book.getPrice()) &&
                Objects.equals(getPublishDate(), book.getPublishDate());
    }

}
