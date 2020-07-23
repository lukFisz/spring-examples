package luk.fisz.springsecurityjwtauthentication.security.jwt;

public class JwtProperties {
    public static final String SECRET = "secret";
    public static final int EXPIRATION_TIME = 432_000; // 5 days
    public static final String PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";

}
