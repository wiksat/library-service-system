package pl.edu.agh.repository.books;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.model.books.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
}
