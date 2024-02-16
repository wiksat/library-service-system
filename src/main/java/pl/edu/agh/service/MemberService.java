package pl.edu.agh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.users.Member;
import pl.edu.agh.repository.users.MemberRepository;
import pl.edu.agh.validator.UserValidator;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findByEmail(String email) {
        return this.memberRepository.findByEmail(email);
    }

    public String addUser(String firstName, String lastName, String email, String password) {
        if (!UserValidator.isFirstNameValid(firstName)) {
            return "Niepoprawne imie";
        }

        if (!UserValidator.isLastNameValid(lastName)) {
            return "Niepoprawne nazwisko";
        }

        if (!UserValidator.isMailValid(email)) {
            return "Niepoprawny mail";
        }

        if (!UserValidator.isPasswordStrong(password)) {
            return "Haslo musi zawierac co najmniej:\n" +
                    "8 znaków, 1 cyfre, 1 duza litere,\n" +
                    "1 mala litere, 1 znak specjalny";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        Member member = new Member(firstName, lastName, email, false, false, hashedPassword);
        try {
            memberRepository.save(member);
            return "Uzytkownik zostal dodany";
        } catch (Exception e) {
            return "Adres e-mail jest juz zajety";
        }
    }

    public void addExample() {
        String password = "Haslo1234$";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        Member member = new Member("Member", "Member", "member@wp.pl", false, false, hashedPassword);
        memberRepository.save(member);
    }
}