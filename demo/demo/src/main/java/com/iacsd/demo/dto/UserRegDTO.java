package com.iacsd.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegDTO {
    private String name;
    private String email;
    private String password;
    private String shopName;
    private String address;
}
