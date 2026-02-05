package com.tiuon.moneymanager.service;

import com.tiuon.moneymanager.dto.ProfileDto;

public interface IProfileService {

    ProfileDto registerProfile(ProfileDto profileDto);
    boolean activateProfile(String activationToken);
}
