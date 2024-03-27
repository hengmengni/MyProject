package com.example.demo.dto;

import java.util.List;

public record CustomerResponse(
        int id, String name, String sex, long phone, List<ProductResponse> products) {

}
