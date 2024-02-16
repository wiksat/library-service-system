package pl.edu.agh.model.extra;

import pl.edu.agh.model.books.Title;

import java.util.Date;

public record LoanDetails(Title title, Date startLoanDate, Date endLoanDate, int loanId) {
}
