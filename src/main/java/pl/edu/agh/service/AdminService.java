package pl.edu.agh.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.users.Admin;
import pl.edu.agh.model.users.Librarian;
import pl.edu.agh.model.users.Member;
import pl.edu.agh.model.users.User;
import pl.edu.agh.repository.users.AdminRepository;
import pl.edu.agh.repository.users.LibrarianRepository;
import pl.edu.agh.repository.users.MemberRepository;
import pl.edu.agh.repository.users.UserRepository;

import java.util.List;

@Service
public class AdminService {

    private final MemberRepository memberRepository;
    private final LibrarianRepository librarianRepository;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminService(MemberRepository memberRepository, LibrarianRepository librarianRepository, AdminRepository adminRepository, UserRepository userRepository) {
        this.memberRepository = memberRepository;
        this.librarianRepository = librarianRepository;
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }

    public void printAll() {
        System.out.println(
                "UserRepository: " + userRepository.findAll() + "\n" +
                        "MemberRepository: " + memberRepository.findAll() + "\n" +
                        "LibrarianRepository: " + librarianRepository.findAll() + "\n" +
                        "AdminRepository: " + adminRepository.findAll() + "\n"
        );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void changeMemberToAdmin(Member user) {
        memberRepository.delete(user);
        adminRepository.save(new Admin(user.getFirstName(), user.getLastName(), user.getEmail(), "000000000", 0, user.getPassword()));
    }

    public void changeMemberToLibrarian(Member user) {
        memberRepository.delete(user);
        librarianRepository.save(new Librarian(user.getFirstName(), user.getLastName(), user.getEmail(), 0, user.getPassword()));
    }

    public void changeAdminToMember(Admin user) {
        adminRepository.delete(user);
        memberRepository.save(new Member(user.getFirstName(), user.getLastName(), user.getEmail(), false, false, user.getPassword()));
    }

    public void changeAdminToLibrarian(Admin user) {
        adminRepository.delete(user);
        librarianRepository.save(new Librarian(user.getFirstName(), user.getLastName(), user.getEmail(), 0, user.getPassword()));
    }

    public void changeLibrarianToMember(Librarian user) {
        librarianRepository.delete(user);
        memberRepository.save(new Member(user.getFirstName(), user.getLastName(), user.getEmail(), false, false, user.getPassword()));
    }

    public void changeLibrarianToAdmin(Librarian user) {
        librarianRepository.delete(user);
        adminRepository.save(new Admin(user.getFirstName(), user.getLastName(), user.getEmail(), "000000000", 0, user.getPassword()));
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public Admin findByEmail(String email) {
        return this.adminRepository.findByEmail(email);
    }
    public User findAllUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public void addExample() {
        String password = "Haslo1234$";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        Admin admin = new Admin("Admin", "Admin", "admin@wp.pl", "123123123", 1, hashedPassword);
        adminRepository.save(admin);
    }

}