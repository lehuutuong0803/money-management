package com.tiuon.moneymanager.mapper;

import com.tiuon.moneymanager.dto.ExpenseDto;
import com.tiuon.moneymanager.dto.IncomeDto;
import com.tiuon.moneymanager.entity.CategoryEntity;
import com.tiuon.moneymanager.entity.IncomeEntity;
import com.tiuon.moneymanager.entity.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {

    public static IncomeEntity toEntity(IncomeDto incomeDto, ProfileEntity profileEntity,
                                        CategoryEntity categoryEntity) {
        return IncomeEntity.builder()
                .icon(incomeDto.getIcon())
                .name(incomeDto.getName())
                .amount(incomeDto.getAmount())
                .date(incomeDto.getDate())
                .category(categoryEntity)
                .profile(profileEntity)
                .build();
    }

    public static IncomeDto toDto(IncomeEntity incomeEntity) {
        return IncomeDto.builder()
                .name(incomeEntity.getName())
                .icon(incomeEntity.getIcon())
                .id(incomeEntity.getId())
                .categoryId(incomeEntity.getCategory() != null ? incomeEntity.getCategory().getId() : null)
                .categoryName(incomeEntity.getCategory() != null ? incomeEntity.getCategory().getName() : null)
                .amount(incomeEntity.getAmount())
                .date(incomeEntity.getDate())
                .createdAt(incomeEntity.getCreatedAt())
                .updatedAt(incomeEntity.getUpdatedAt())
                .build();
    }
}
