package com.tiuon.moneymanager.mapper;

import com.tiuon.moneymanager.dto.ProfileDto;
import com.tiuon.moneymanager.entity.ProfileEntity;

public class ProfileMapper {

    public static ProfileEntity toEntity(ProfileDto profileDto) {
        return ProfileEntity.builder()
                .id(profileDto.getId())
                .fullName(profileDto.getFullName())
                .email(profileDto.getEmail())
                .password(profileDto.getPassword())
                .profileImageUrl(profileDto.getProfileImageUrl())
                .createdAt(profileDto.getCreatedAt())
                .updatedAt(profileDto.getUpdatedAt())
                .build();
    }

    public static ProfileDto toDto(ProfileEntity profileEntity) {
        return ProfileDto.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())
                .profileImageUrl(profileEntity.getProfileImageUrl())
                .createdAt((profileEntity.getCreatedAt()))
                .updatedAt(profileEntity.getUpdatedAt())
                .build();
    }
}
