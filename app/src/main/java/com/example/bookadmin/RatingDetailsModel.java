package com.example.bookadmin;

public class RatingDetailsModel {
    String id;
    String comment;
    String review_person;
    String review_date;
    String rating;
    String book_id;

    RatingDetailsModel(String id, String comment, String review_person, String review_date, String rating, String book_id) {
        this.id = id;
        this.comment = comment;
        this.review_person = review_person;
        this.review_date = review_date;
        this.rating = rating;
        this.book_id = book_id;
    }

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getReview_person() {
        return review_person;
    }

    public String getReview_date() {
        return review_date;
    }

    public String getRating() {
        return rating;
    }

    public String getBook_id() {
        return book_id;
    }

}
