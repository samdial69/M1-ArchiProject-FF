package fr.univlorrainem1archi.friendsfiestas_v1.security.jwt;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000; //milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String ISSUER = "Friends-fiestas";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE = "Log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You don't have permission to access this page";
    public static final String[] PUBLIC_URLS = {"/api/v1/friends-fiestas/users/login","/api/v1/friends-fiestas/users/register","/user/resetpassword/**"};
}
