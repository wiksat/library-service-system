package pl.edu.agh.repository.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.model.books.Rating;
import pl.edu.agh.model.books.Title;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, Integer> {
    @Query("SELECT r FROM Rating r WHERE r.title.titleId = :titleId")
    List<Rating> findRatingsByTitleId(@Param("titleId") int titleId);

    @Query("SELECT COUNT(t) FROM Title t")
    Integer getAmountOfTitles();
}
