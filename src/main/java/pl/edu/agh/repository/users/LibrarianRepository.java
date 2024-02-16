package pl.edu.agh.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.model.users.Librarian;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Integer> {
    Librarian findByEmail(String email);
}
