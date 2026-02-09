package com.tiuon.moneymanager.service.impl;

import com.tiuon.moneymanager.dto.ExpenseDto;
import com.tiuon.moneymanager.entity.CategoryEntity;
import com.tiuon.moneymanager.entity.ExpenseEntity;
import com.tiuon.moneymanager.entity.ProfileEntity;
import com.tiuon.moneymanager.mapper.ExpenseMapper;
import com.tiuon.moneymanager.repository.CategoryRepository;
import com.tiuon.moneymanager.repository.ExpenseRepository;
import com.tiuon.moneymanager.service.ICategoryService;
import com.tiuon.moneymanager.service.IExpenseService;
import com.tiuon.moneymanager.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements IExpenseService {
    private final ICategoryService iCategoryService;
    private final ExpenseRepository expenseRepository;
    private final IProfileService iProfileService;
    private final CategoryRepository categoryRepository;


    // save Expense instance
    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        ProfileEntity profile = iProfileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(expenseDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category doesn't exist"));
        ExpenseEntity newExpenseEntity = ExpenseMapper.toEntity(expenseDto, profile, category);
        newExpenseEntity = expenseRepository.save(newExpenseEntity);
        return ExpenseMapper.toDto(newExpenseEntity);
    }

    // retrieve all expenses for the current month/based on the start date and end date
    public List<ExpenseDto> getCurrentMonthExpensesForCurrentUser() {
        ProfileEntity profile = iProfileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<ExpenseEntity> expenseEntityList = expenseRepository.findByProfileIdAndDateBetween(profile.getId(),
                                                                                                    startDate, endDate);
        return expenseEntityList.stream().map(ExpenseMapper::toDto).toList();
    }

    // delete expense by id for current user
    public void deleteExpense(Long expenseId) {
        ProfileEntity profileEntity = iProfileService.getCurrentProfile();
        ExpenseEntity expenseEntity = expenseRepository.findById(expenseId).orElseThrow(
                () -> new RuntimeException("Expense doesn't exist")
        );
        if (!expenseEntity.getProfile().getId().equals(profileEntity.getId())) {
            throw new RuntimeException("Unauthorized to delete this expense");
        }
        expenseRepository.delete(expenseEntity);
    }

    // Get latest 5 expenses for current user
    public List<ExpenseDto> getLatest5ExpenseForCurrentUser() {
        ProfileEntity profile = iProfileService.getCurrentProfile();
        List<ExpenseEntity> expenseEntityList = expenseRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return expenseEntityList.stream().map(ExpenseMapper::toDto).toList();
    }

    // Get total expenses for current user
    public BigDecimal getTotalExpensesForCurrentUser() {
        ProfileEntity profile = iProfileService.getCurrentProfile();
        BigDecimal totalExpenses = expenseRepository.findTotalExpenseByProfileId(profile.getId());
        return  totalExpenses != null ? totalExpenses : BigDecimal.ZERO;
    }

    // Filter expenses
    public List<ExpenseDto> filterExpenses(LocalDate startDate, LocalDate endDate, String keyword, Sort sort) {
        ProfileEntity profile = iProfileService.getCurrentProfile();
        List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
                profile.getId(), startDate, endDate, keyword, sort);
        return list.stream().map(ExpenseMapper::toDto).toList();
    }

    // Notifications
    public List<ExpenseDto> getExpensesForUserOnDate(Long profileId, LocalDate date) {
        List<ExpenseEntity> expenseEntityList = expenseRepository.findByProfileIdAndDate(profileId, date);
        return expenseEntityList.stream().map(ExpenseMapper::toDto).toList();
    }

}
