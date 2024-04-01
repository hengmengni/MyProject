package com.example.demo.dto;

import java.util.List;

public record CustomerResponse(
                int id, String name, String sex, String phone, List<ProductResponse> products) {

}
