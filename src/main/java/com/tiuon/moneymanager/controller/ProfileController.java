package com.tiuon.moneymanager.controller;

import com.tiuon.moneymanager.dto.ProfileDto;
import com.tiuon.moneymanager.service.IProfileService;
import com.tiuon.moneymanager.service.impl.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final IProfileService iProfileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDto> registerProfile(@RequestBody ProfileDto profileDto) {
        ProfileDto registeredProfileDto = iProfileService.registerProfile(profileDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfileDto);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String token){
        boolean isActivated = iProfileService.activateProfile(token);
        if (isActivated) {
            return ResponseEntity.status(HttpStatus.OK).body("Successfully activate your profile!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Actication token not found or already used!");
        }

    }
}
