package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.SecurityConfig;
import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.example.umc3_teamproject.config.resTemplate.ResponseTemplateStatus.EMPTY_JWT;
import static com.example.umc3_teamproject.config.resTemplate.ResponseTemplateStatus.INVALID_JWT;

@Service
public class JwtService {

    /*
    JWT 생성
    @param userIdx
    @return String
     */
    public String createJwt(Long memberId){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("memberId",memberId)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365))) // 만료기간 -> 약 1년
                .signWith(SignatureAlgorithm.HS256, SecurityConfig.JWT_SECRET_KEY)
                .compact();
    }
    /*
   JWT 생성
   @param reviewIdx
   @return String
    */
    public String createReviewJwt(Long reviewIdx){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("reviewIdx",reviewIdx)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365))) // 만료기간 -> 약 1년
                .signWith(SignatureAlgorithm.HS256, SecurityConfig.JWT_SECRET_KEY)
                .compact();
    }
    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /*
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
    public int getUserIdx() throws ResponseException{
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new ResponseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(SecurityConfig.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new ResponseException(INVALID_JWT);
        }

        // 3. userIdx 추출
        return claims.getBody().get("memberId",Integer.class);  // jwt 에서 userIdx를 추출합니다.
    }
    /*
    JWT에서 reviewIdx 추출
    @return int
    @throws BaseException
     */
    public int getReviewIdx() throws ResponseException {
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new ResponseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(SecurityConfig.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new ResponseException(INVALID_JWT);
        }

        // 3. userIdx 추출
        return claims.getBody().get("reviewIdx",Integer.class);  // jwt 에서 userIdx를 추출합니다.
    }

}