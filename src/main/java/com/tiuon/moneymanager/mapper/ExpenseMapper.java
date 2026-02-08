package com.tiuon.moneymanager.mapper;

import com.tiuon.moneymanager.dto.ExpenseDto;
import com.tiuon.moneymanager.entity.CategoryEntity;
import com.tiuon.moneymanager.entity.ExpenseEntity;
import com.tiuon.moneymanager.entity.ProfileEntity;

public class ExpenseMapper {

    public static ExpenseEntity toEntity(ExpenseDto expenseDto, ProfileEntity profileEntity,
                                         CategoryEntity categoryEntity) {
        return ExpenseEntity.builder()
                .icon(expenseDto.getIcon())
                .name(expenseDto.getName())
                .amount(expenseDto.getAmount())
                .date(expenseDto.getDate())
                .category(categoryEntity)
                .profile(profileEntity)
                .build();
    }

    public static ExpenseDto toDto(ExpenseEntity expenseEntity) {
        return ExpenseDto.builder()
                .name(expenseEntity.getName())
                .icon(expenseEntity.getIcon())
                .id(expenseEntity.getId())
                .categoryId(expenseEntity.getCategory() != null ? expenseEntity.getCategory().getId() : null)
                .categoryName(expenseEntity.getCategory() != null ? expenseEntity.getCategory().getName() : null)
                .amount(expenseEntity.getAmount())
                .date(expenseEntity.getDate())
                .createdAt(expenseEntity.getCreatedAt())
                .updatedAt(expenseEntity.getUpdatedAt())
                .build();
    }
}
