package pl.edu.agh.repository.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.books.CoverType;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT COUNT(b) FROM Book b WHERE b.title.titleId = :titleId AND b.coverType = :coverType")
    Long countBooksByTitleIdAndCover(@Param("titleId") int titleId,
                                     @Param("coverType") CoverType coverType);

    @Query("SELECT COUNT(b) FROM Book b JOIN b.loan l " +
            "WHERE b.title.titleId = :titleId AND b.coverType = :coverType")
    Long countBorrowedBooksByTitleIdAndCover(@Param("titleId") int titleId,
                                             @Param("coverType") CoverType coverType);

    @Query("SELECT b FROM Book b WHERE b.title.titleId = :titleId " +
            "AND b.coverType = :coverType " +
            "AND NOT EXISTS (SELECT l.book FROM Loan l WHERE l.book = b)")
    List<Book> findAvailableBooksByTitleIdAndCoverType(@Param("titleId") int titleId,
                                                       @Param("coverType") CoverType coverType);

    @Query("SELECT COUNT(b) FROM Book b")
    Integer getAmountOfAllBooks();

}
