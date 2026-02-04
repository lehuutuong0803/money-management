package com.tiuon.moneymanager.service.impl;

import com.tiuon.moneymanager.dto.ProfileDto;
import com.tiuon.moneymanager.entity.ProfileEntity;
import com.tiuon.moneymanager.mapper.ProfileMapper;
import com.tiuon.moneymanager.repository.ProfileRepository;
import com.tiuon.moneymanager.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {
    private final ProfileRepository profileRepository;

    public ProfileDto registerProfile(ProfileDto profileDto) {
        ProfileEntity newProfile = ProfileMapper.toEntity(profileDto);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile = profileRepository.save(newProfile);

        return ProfileMapper.toDto(newProfile);
    }
}
