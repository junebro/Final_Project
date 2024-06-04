package com.final_project.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String userId;
    private String oldPassword;
    private String newPassword;
}