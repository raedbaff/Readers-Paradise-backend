package com.example.books.mappers;

import com.example.books.models.Book;
import com.example.books.payload.Response.BookMetaDataResponse;

public class BookMapper {
    public static BookMetaDataResponse toBookMetaDataResponse(Book book) {
        BookMetaDataResponse response = new BookMetaDataResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setDescription(book.getDescription());
        response.setGenre(book.getGenre());
        response.setBorrowPrice(book.getBorrowPrice());
        response.setBuyPrice(book.getBuyPrice());
        response.setCoverPhoto(book.getCoverPhoto());
        response.setLanguage(book.getLanguage());
        response.setPublishYear(book.getPublishYear());
        response.setTotalPages(book.getTotalPages());
        return response;
    }

}
