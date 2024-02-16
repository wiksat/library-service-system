package pl.edu.agh.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.model.users.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByEmail(String email);
}
