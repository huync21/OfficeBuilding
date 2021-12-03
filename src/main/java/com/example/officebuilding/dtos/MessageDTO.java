package com.example.officebuilding.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MessageDTO {

    private String message;

    public MessageDTO(String message) {
        this.message = message;
    }
}
