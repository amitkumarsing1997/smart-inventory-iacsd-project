package com.iacsd.demo.security;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.LinkedHashMap;
import java.util.Map;
@Data
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private Map<String, Object> moreInfo = new LinkedHashMap<>();

    public void addAdditionalInfo(String key, Object obj){
        moreInfo.put(key,obj);
    }


    //by amit

//    private final String jwt;
//    public AuthResponse(String jwt) {
//
//        this.jwt=jwt;
//    }
//    public String getJwt() { return jwt ;}
}
