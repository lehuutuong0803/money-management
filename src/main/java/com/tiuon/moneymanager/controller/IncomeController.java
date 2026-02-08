package com.tiuon.moneymanager.controller;

import com.tiuon.moneymanager.dto.ExpenseDto;
import com.tiuon.moneymanager.dto.IncomeDto;
import com.tiuon.moneymanager.service.IIcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IIcomeService iIcomeService;

    @PostMapping
    public ResponseEntity<IncomeDto> addExpense(@RequestBody IncomeDto incomeDto) {
        IncomeDto savedIncomeDto = iIcomeService.addIncome(incomeDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedIncomeDto);
    }

    @GetMapping
    public ResponseEntity<List<IncomeDto>> getIncomes() {
        List<IncomeDto> incomeDtoList = iIcomeService.getCurrentMonthExpensesForCurrentUser();
        return ResponseEntity.status(HttpStatus.OK).body(incomeDtoList);
    }

    @DeleteMapping("/{incomeId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long incomeId) {
        iIcomeService.deleteIncome(incomeId);
        return ResponseEntity.noContent().build();
    }
}
