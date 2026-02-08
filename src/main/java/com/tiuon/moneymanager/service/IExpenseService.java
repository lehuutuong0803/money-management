package com.tiuon.moneymanager.service;

import com.tiuon.moneymanager.dto.ExpenseDto;

import java.util.List;

public interface IExpenseService {
    ExpenseDto addExpense(ExpenseDto expenseDto);
    List<ExpenseDto> getCurrentMonthExpensesForCurrentUser();
    void deleteExpense(Long expenseId);
}
