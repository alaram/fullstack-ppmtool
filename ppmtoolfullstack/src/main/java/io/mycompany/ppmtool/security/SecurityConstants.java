package io.mycompany.ppmtool.security;

public class SecurityConstants {

    // Constants for Http filter
    public static final String SIGN_UP_URL_HOME ="/";
    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URLS = "h2-console/**";
    public static final String URL_IMAGES_ICO ="/favicon.ico";
    public static final String URL_IMAGES_PNG = "/**/*.png";
    public static final String URL_IMAGES_GIF = "/**/*.gif";
    public static final String URL_IMAGES_SVG = "/**/*.svg";
    public static final String URL_IMAGES_JPG = "/**/*.jpg";
    public static final String URL_DOC_TYPE_HTML = "/**/*.html";
    public static final String URL_DOC_TYPE_CSS = "/**/*.css";
    public static final String URL_DOC_TYPE_JS = "/**/*.js";

    // Constants for Configuration and setup of JWT
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 30_000;
}