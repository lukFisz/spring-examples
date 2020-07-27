package luk.fisz.springsecurityjwtauthentication.security.jwt;

public class JwtProperties {
    public static final String SECRET = "secret";
    public static final int EXPIRATION_TIME = 36_000_000; // 10 hours
    public static final String PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
}
