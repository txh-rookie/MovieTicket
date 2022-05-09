package com.serookie.movie.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/4
 */
@Component
public class JwtTokenUtils {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String secret="kevintam";
    private static final Long expiration=604800L;
    public static final String CLAIN_KEY_tokenHead="Bearer";
    public static final String CLAIM_KEY_HEADER="Authorization";

    /**
     * 根据用户信息生成JWT的token
     */
    public String createToken(String username,String id){
        Map<String,Object> maps=new HashMap<>();
        maps.put(CLAIM_KEY_USERNAME,username);
        maps.put("id",id);
        maps.put(CLAIM_KEY_CREATED,new Date());
        return createToken(maps);
    }

    /**
     * 根据token获取id
     * @param token
     * @return
     */
    public String getToken(String token){
        String id=null;
        try {
            Claims claims=getClaimsToken(token);
            id = (String) claims.get("id");
        } catch ( Exception e ) {
            id=null;
            e.printStackTrace();
        }
        return id;
    }
    /**
     * 根据负载生成token
     * @param maps
     * @return
     */
    private String createToken(Map<String,Object> maps){
        return Jwts.builder()
                .setClaims(maps)
                .setExpiration(createExpirationDate())
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    /**
     * 定义失效时间
     * @return
     */
    private Date createExpirationDate() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    /**
     * 从token获取用户名
     * @return
     */
    public String getUserNameToken(String token){
        String username=null;
        try {
            Claims claims=getClaimsToken(token);
            username = claims.getSubject();
        } catch ( Exception e ) {
            username=null;
            e.printStackTrace();
        }
        return username;
    }

    /**
     * 根据token获取负载
     * @param token
     * @return
     */
    private Claims getClaimsToken(String token) {
        Claims claims=null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch ( ExpiredJwtException e ) {
            e.printStackTrace();
        } catch ( UnsupportedJwtException e ) {
            e.printStackTrace();
        } catch ( MalformedJwtException e ) {
            e.printStackTrace();
        } catch ( SignatureException e ) {
            e.printStackTrace();
        } catch ( IllegalArgumentException e ) {
            e.printStackTrace();
        }
        return claims;
    }
    /**
     * 判断token是否有效
     * 根据token的里面的username和userDetails里的username是否一致和时间是否过期来判断
     */
    public boolean verificationToken(String token,String username){
        String newUsername = getUserNameToken(token);
        return newUsername.equals(username) && !isTokenExpiration(token);
    }
    /**
     * 判断token是否失效
     * @param token
     * @return
     */
    private boolean isTokenExpiration(String token) {
        Date expirationDate= getExpirationDateToken(token);
        return  expirationDate.before(new Date());
    }
    /**
     * 从token中获取失效时间
     * @param token
     * @return
     */
    private Date getExpirationDateToken(String token) {
        Claims claimsToken = getClaimsToken(token);
        return claimsToken.getExpiration();
    }

    /**
     * 判断token是否被刷新
     */
    public boolean onRefreshToken(String token){
        return !isTokenExpiration(token);
    }
    /**
     * 刷新token
     */
    public String refreshToken(String token){
        Claims claims = getClaimsToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return createToken(claims);
    }
}
