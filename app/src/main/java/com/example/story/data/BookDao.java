    package com.example.story.data;

    import androidx.room.Dao;
    import androidx.room.Delete;
    import androidx.room.Insert;
    import androidx.room.Query;
    import androidx.room.Update;

    import java.util.List;

    @Dao
    public interface BookDao {
        @Insert
        void insertBook(Book book);

        @Query("SELECT * FROM Book WHERE CatID = :catId")
        List<Book> getBooksByCategory(String catId);

        @Query("SELECT * FROM Book")
        List<Book> getAllBooks();

        @Query("SELECT * FROM Book WHERE BookID = :bookId LIMIT 1")
        Book getBookById(String bookId);
        @Delete
        void deleteBook(Book book);
        @Update
        void updateBook(Book book);
    }
