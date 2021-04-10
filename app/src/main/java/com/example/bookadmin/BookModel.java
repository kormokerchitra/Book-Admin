package com.example.bookadmin;

public class BookModel {

    String id;
    String book_name;
    String book_link;
    String category_id;
    String category_name;
    String rating;
    String reviewer_number;

    BookModel(String id, String book_name, String book_link, String category_id, String category_name, String rating, String reviewer_number) {
        this.id = id;
        this.book_name = book_name;
        this.book_link = book_link;
        this.category_id = category_id;
        this.category_name = category_name;
        this.rating = rating;
        this.reviewer_number = reviewer_number;
    }

    public String getId() {
        return id;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getBook_link() {
        return book_link;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getRating() {
        return rating;
    }

    public String getReviewer_number() {
        return reviewer_number;
    }
}
