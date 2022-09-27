package com.iacsd.demo.dto;


import com.iacsd.demo.model.Account;
import com.iacsd.demo.model.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    public class UserInfoDTO {
        private String uid;
        private String name;
        private String email;
        private String shopName;
        private String shopAddress;

        public static UserInfoDTO from(AppUser appUser, Account acc) {
            UserInfoDTO dto = new UserInfoDTO();
            dto.uid = appUser.getUid();
            dto.name = appUser.getFullName();
            dto.email = appUser.getEmail();
            dto.shopName = acc.getAccName();
            dto.shopAddress = acc.getAddress();
            return dto;
        }
    }


