package com.tiuon.moneymanager.service;

import com.tiuon.moneymanager.dto.IncomeDto;

import java.util.List;

public interface IIcomeService {
    IncomeDto addIncome(IncomeDto incomeDto);
    List<IncomeDto> getCurrentMonthExpensesForCurrentUser();
    void deleteIncome(Long incomeId);
}
