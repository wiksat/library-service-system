package pl.edu.agh.model.books;

import jakarta.persistence.*;
import pl.edu.agh.model.users.Member;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ratingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="title_id", referencedColumnName = "titleId")
    private Title title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "userId")
    private Member member;

    public Rating(Title title, Member member, int rate, String comment) {
        this.title = title;
        this.member = member;
        this.rate = rate;
        this.comment = comment;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Rating() {}
    private int rate;

    private String comment;
}
