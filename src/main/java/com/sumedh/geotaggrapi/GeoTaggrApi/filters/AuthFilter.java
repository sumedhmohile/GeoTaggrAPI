package com.sumedh.geotaggrapi.GeoTaggrApi.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends GenericFilterBean {

    @Value("${secretKey}")
    String secretKey;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpRequest.getHeader("Authorization");

        if(authHeader != null) {
            String[] authHeadArray = authHeader.split("Bearer ");
            if(authHeadArray.length > 1 && authHeadArray[1] != null) {
                String token = authHeadArray[1];

                try {
                    Claims claims = Jwts.parser().setSigningKey(secretKey)
                            .parseClaimsJws(token).getBody();
                    httpRequest.setAttribute("userId", claims.get("facebookId").toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Invalid authentication");
                    return;
                }

            } else {
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Malformed auth header");
                return;
            }
        } else {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Missing auth header");
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
