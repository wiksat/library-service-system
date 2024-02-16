package pl.edu.agh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.users.Librarian;
import pl.edu.agh.repository.users.LibrarianRepository;

@Service
public class LibrarianService {
    private final LibrarianRepository librarianRepository;

    @Autowired
    public LibrarianService(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    public Librarian findByEmail(String email) {
        return this.librarianRepository.findByEmail(email);
    }

    public void addExample() {
        String password = "Haslo1234$";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        Librarian librarian = new Librarian("Librarian", "Librarian", "librarian@wp.pl", 2, hashedPassword);
        librarianRepository.save(librarian);
    }

}