package com.tiuon.moneymanager.service;

import com.tiuon.moneymanager.dto.IncomeDto;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IIcomeService {
    IncomeDto addIncome(IncomeDto incomeDto);
    List<IncomeDto> getCurrentMonthExpensesForCurrentUser();
    void deleteIncome(Long incomeId);
    List<IncomeDto> getLatest5IncomeForCurrentUser();
    BigDecimal getTotalIncomeForCurrentUser();
    List<IncomeDto> filterIncomes(LocalDate startDate, LocalDate endDate, String keyword, Sort sort);
}
