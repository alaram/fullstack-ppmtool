package io.mycompany.ppmtool.security;

import java.io.IOException;

import com.google.gson.Gson;

import io.mycompany.ppmtool.exceptions.InvalidLoginResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String JWT_ENTRY_POINT_CONTENT_TYPE = "application/json";

    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authException
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException authException) throws IOException {

        InvalidLoginResponse invalidLoginResponse = new InvalidLoginResponse();
        String jsonLoginResponse = new Gson().toJson(invalidLoginResponse);

        httpServletResponse.setContentType(JWT_ENTRY_POINT_CONTENT_TYPE);
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().print(jsonLoginResponse);
    }
}