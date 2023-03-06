package com.xdt.dataset_server.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.xdt.dataset_server.entity.TokenInfo;
import com.xdt.dataset_server.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XDT
 * @ClassName JwtUtil
 * @Description: TODO
 * @Date 2023/2/28 9:10
 **/
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /*秘钥*/
    private static final String SECRET = "HNU_XDT0916";

    /*过期时间*/
    private static final long EXPIRATION = 12000L; //单位为秒

    /*生成用户token， 设置token超时时间*/
    public static TokenInfo createToken(User user){

        long createTime = System.currentTimeMillis();
        Date createTimeDate = new Date(createTime);

        /*过期时间*/
        Date expireDate = new Date(createTime + EXPIRATION * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("type", "JWT");
        Date IssuedTime = new Date();
        String token = JWT.create()
                .withHeader(map)//添加头部
                .withClaim("uuid", user.getUuid())
                .withClaim("userName", user.getName())
                .withClaim("password", user.getPassword())
                .withExpiresAt(expireDate) //超时设置，设置过期的时间
                .withIssuedAt(IssuedTime) //签发时间
                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密

        //System.out.println("token length is" + token.length());
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUserUuid(user.getUuid());
        tokenInfo.setUserName(user.getName());
        tokenInfo.setToken(token);
        tokenInfo.setCreateTime(createTimeDate);
        tokenInfo.setExpiration(EXPIRATION);
        tokenInfo.setInvalidTime(expireDate);
        tokenInfo.setIssuedTime(IssuedTime);
        return tokenInfo;
    }

    /*校验token并解析token*/
    public static Map<String, Claim> verifyToken(String token){
        DecodedJWT jwt = null;
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);

            //获取负载中的属性值
            //String jwtToken = jwt.getToken();
            //System.out.println("jwtToken is " + jwtToken);
            //String jwtPayload = jwt.getPayload();
            //System.out.println("jwtPayload is " + jwtPayload);
            //String jwtgetSignature = jwt.getSignature();
            //System.out.println("jwtgetSignature is " + jwtgetSignature);
            //
            //String userUuid = jwt.getClaims().get("uuid").asString();
            //System.out.println("userUuid is " + userUuid);
            //
            //String password = jwt.getClaims().get("password").asString();
            //System.out.println("password is " + password);

        } catch (Exception e){
            logger.error(e.getMessage());
            logger.error("token 解码异常");
            //解码异常则抛出异常
            return null;
        }
        return jwt.getClaims();
    }
}
