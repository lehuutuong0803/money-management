package com.tiuon.moneymanager.service.impl;

import com.tiuon.moneymanager.dto.ProfileDto;
import com.tiuon.moneymanager.entity.ProfileEntity;
import com.tiuon.moneymanager.mapper.ProfileMapper;
import com.tiuon.moneymanager.repository.ProfileRepository;
import com.tiuon.moneymanager.service.IEmailService;
import com.tiuon.moneymanager.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {
    private final ProfileRepository profileRepository;
    private final IEmailService iEmailService;

    public ProfileDto registerProfile(ProfileDto profileDto) {
        ProfileEntity newProfile = ProfileMapper.toEntity(profileDto);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile = profileRepository.save(newProfile);

        // send activation email
        String activationLink = "http://localhost:8080/api/v1.0/activate?token=" + newProfile.getActivationToken();
        String subject = "Activate your Money Manager Account";
        String body = "Click on the following link to activate your account: " + activationLink;
        iEmailService.sendEmail(newProfile.getEmail(), subject, body);

        return ProfileMapper.toDto(newProfile);
    }

    public boolean activateProfile(String activationToken) {
        return profileRepository.findByActivationToken(activationToken)
                .map(profileEntity -> {
                    profileEntity.setIsActive(true);
                    profileRepository.save(profileEntity);
                    return true;
                })
                .orElse(false);
    }
}
