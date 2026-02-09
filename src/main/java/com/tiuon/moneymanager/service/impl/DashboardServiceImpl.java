package com.tiuon.moneymanager.service.impl;

import com.tiuon.moneymanager.dto.ExpenseDto;
import com.tiuon.moneymanager.dto.IncomeDto;
import com.tiuon.moneymanager.dto.RecentTransactionDto;
import com.tiuon.moneymanager.entity.ProfileEntity;
import com.tiuon.moneymanager.service.IDashboardService;
import com.tiuon.moneymanager.service.IExpenseService;
import com.tiuon.moneymanager.service.IIcomeService;
import com.tiuon.moneymanager.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements IDashboardService {
    private final IIcomeService iIcomeService;
    private final IExpenseService iExpenseService;
    private final IProfileService iProfileService;

    public Map<String, Object> getDashboardData() {
        ProfileEntity profile = iProfileService.getCurrentProfile();
        Map<String, Object> returnValue = new LinkedHashMap<>();
        List<IncomeDto> latestIncomes = iIcomeService.getLatest5IncomeForCurrentUser();
        List<ExpenseDto> latestExpenses = iExpenseService.getLatest5ExpenseForCurrentUser();
        List<RecentTransactionDto> recentTransactionDtos = Stream.concat(
                latestIncomes.stream().map(income ->
                        RecentTransactionDto.builder()
                                .id(income.getId())
                                .profileId(profile.getId())
                                .name(income.getName())
                                .amount(income.getAmount())
                                .date(income.getDate())
                                .type("income")
                                .createdAt(income.getCreatedAt())
                                .updatedAt(income.getUpdatedAt())
                                .build()),
                latestExpenses.stream().map(expense ->
                        RecentTransactionDto.builder()
                                .id(expense.getId())
                                .profileId(profile.getId())
                                .name(expense.getName())
                                .amount(expense.getAmount())
                                .date(expense.getDate())
                                .type("expense")
                                .createdAt(expense.getCreatedAt())
                                .updatedAt(expense.getUpdatedAt())
                                .build()))
                .sorted((a, b) -> {
                    int cmp = b.getDate().compareTo(a.getDate());
                    if (cmp == 0 && a.getCreatedAt() != null && b.getCreatedAt() != null) {
                        return b.getCreatedAt().compareTo(a.getCreatedAt());
                    }
                    return cmp;
                }).toList();
        returnValue.put("totalBalance", iIcomeService.getTotalIncomeForCurrentUser().subtract(iExpenseService.getTotalExpensesForCurrentUser()));
        returnValue.put("totalIncome", iIcomeService.getTotalIncomeForCurrentUser());
        returnValue.put("totalExpense", iExpenseService.getTotalExpensesForCurrentUser());
        returnValue.put("recent5Expenses", latestExpenses);
        returnValue.put("recent5Incomes", latestIncomes);
        returnValue.put("recentTransaction", recentTransactionDtos);
        return  returnValue;
    }
}
