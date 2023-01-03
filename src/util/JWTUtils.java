package util;

import cn.hutool.crypto.asymmetric.RSA;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Map;

//JWT工具类，用于生成和验证JWT
public class JWTUtils {
    private static final String RSA_PRIVATE_KEY = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAnuooK5AyLhPKuQRb" +
            "dYauKHlRVj8BQ6Uj/2xT30DuZbPZasIT2DKo5ftL6sr7AIJA0t1A68Gy8H4Tail7" +
            "N3HjNQIDAQABAkAEt/s9X+i9iYTpfYGhucAOH5wUrYZCFfM9sF/FL05k4qhFaqEY" +
            "U6JbY7Aid4yj0t+ZVKcwc7tqJeftBm2WaMcBAiEAy/itii7Gx/+9UCDSl75zdML8" +
            "/74sucXL+08EBC3fCtUCIQDHc0b8gyzoIbKZ5NkyI91o2FPzrh5Uvh+JHkHMsvbm" +
            "4QIhAK37LyOH5LyhsnxPkvImXFsDG0SVlZbcVbLF4loiOncFAiAtvoO+cHuBNhFX" +
            "eIRUGqOyY0NF9V5hg0WKVlUzFKzKIQIgAa2hAidwUOQ9dhmk/lu2zpUi8yOZrxQ8" +
            "U7++OGf2Qqg=";
    private static final String RSA_PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJ7qKCuQMi4TyrkEW3WGrih5UVY/AUOl" +
            "I/9sU99A7mWz2WrCE9gyqOX7S+rK+wCCQNLdQOvBsvB+E2opezdx4zUCAwEAAQ==";

    /**
     * 生成token
     *
     * @param payload token携带的信息
     * @return token字符串
     */
    public static String getTokenRsa(Map<String, String> payload) {
        // 指定token过期时间为30天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);

        JWTCreator.Builder builder = JWT.create();
        // 构建payload
        payload.forEach(builder::withClaim);//payload.forEach((k, v) -> builder.withClaim(k, v));

        // 利用hutool创建RSA
        RSA rsa = new RSA(RSA_PRIVATE_KEY, null);
        // 获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) rsa.getPrivateKey();
        // 签名时传入私钥
        return builder.withExpiresAt(calendar.getTime()).sign(Algorithm.RSA256(null, privateKey));
    }

    /**
     * 解析token
     *
     * @param token token字符串
     * @return 解析后的token
     */
    public static DecodedJWT decodeRsa(String token) {
        // 利用hutool创建RSA
        RSA rsa = new RSA(null, RSA_PUBLIC_KEY);
        // 获取RSA公钥
        RSAPublicKey publicKey = (RSAPublicKey) rsa.getPublicKey();
        // 验签时传入公钥
        JWTVerifier jwtVerifier = JWT.require(Algorithm.RSA256(publicKey, null)).build();
        return jwtVerifier.verify(token);
    }
}
