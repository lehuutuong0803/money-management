package com.tiuon.moneymanager.service;

import com.tiuon.moneymanager.dto.ExpenseDto;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IExpenseService {
    ExpenseDto addExpense(ExpenseDto expenseDto);
    List<ExpenseDto> getCurrentMonthExpensesForCurrentUser();
    void deleteExpense(Long expenseId);
    List<ExpenseDto> getLatest5ExpenseForCurrentUser();
    BigDecimal getTotalExpensesForCurrentUser();
    List<ExpenseDto> filterExpenses(LocalDate startDate, LocalDate endDate, String keyword, Sort sort);
    List<ExpenseDto> getExpensesForUserOnDate(Long profileId, LocalDate date);
}
