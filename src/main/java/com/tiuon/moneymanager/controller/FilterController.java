package com.tiuon.moneymanager.controller;

import com.tiuon.moneymanager.dto.ExpenseDto;
import com.tiuon.moneymanager.dto.FilterDto;
import com.tiuon.moneymanager.dto.IncomeDto;
import com.tiuon.moneymanager.service.IExpenseService;
import com.tiuon.moneymanager.service.IIcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterController {

    private final IExpenseService iExpenseService;
    private final IIcomeService iIcomeService;

    @PostMapping
    public ResponseEntity<?> filterTransactions(@RequestBody FilterDto filterDto) {
        LocalDate startDate = filterDto.getStartDate() != null ? filterDto.getStartDate() : LocalDate.MIN;
        LocalDate endDate = filterDto.getEndDate() != null ? filterDto.getEndDate() : LocalDate.now();
        String keyword = filterDto.getKeyword() != null ? filterDto.getKeyword() : "";
        String sortField = filterDto.getSortField() != null ? filterDto.getSortField() : "date";
        Sort.Direction direction = "desc".equalsIgnoreCase(filterDto.getSortOrder()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);
        if ("income".equalsIgnoreCase(filterDto.getType())) {
            List<IncomeDto> incomeDtoList = iIcomeService.filterIncomes(startDate, endDate, keyword, sort);
            return ResponseEntity.status(HttpStatus.OK).body(incomeDtoList);
        } else if ("expense".equalsIgnoreCase(filterDto.getType())) {
            List<ExpenseDto> expenseDtoList = iExpenseService.filterExpenses(startDate, endDate, keyword, sort);
            return ResponseEntity.status(HttpStatus.OK).body(expenseDtoList);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid type. Must be 'income' or 'expense");
        }
    }
}
