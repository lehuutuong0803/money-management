package com.tiuon.moneymanager.controller;

import com.tiuon.moneymanager.dto.ExpenseDto;
import com.tiuon.moneymanager.service.IExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final IExpenseService iExpenseService;

    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody ExpenseDto expenseDto) {
        ExpenseDto savedExpenseDto = iExpenseService.addExpense(expenseDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedExpenseDto);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getExpenses() {
        List<ExpenseDto> expenseDtoList = iExpenseService.getCurrentMonthExpensesForCurrentUser();
        return ResponseEntity.status(HttpStatus.OK).body(expenseDtoList);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        iExpenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }
}
