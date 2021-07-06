package com.model.bcp.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;

@Controller
public class JwtFilter extends GenericFilterBean {

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {

    	// Obtenemos el token que viene en el encabezado de la peticion
        String token = ((HttpServletRequest) request).getHeader("Authorization");
        String[] list_roles = null;

        if (token != null) {
            String username = Jwts.parser()
                    .setSigningKey(JwtUtil.KEYSECRET) //la clave secreta esta declara en JwtUtil
                    .parseClaimsJws(token) //este metodo es el que valida
                    .getBody()
                    .getSubject();        //extraigo el nombre de usuario del token

	        /*De manera "sucia" vamos a obtener los roles del usuario*/
	        String[] roles= {"ROLE_USER", "ROLE_ADMIN", "ROLE_DBA"};
//	        list_roles = new String[roles.size()];
	        list_roles = new String[3];
	        int i = 0;
	        for (Object rol : roles) {
	        	list_roles[i] = rol.toString();
	        	i++;
	        }
        }
        Authentication authentication = JwtUtil.getAuthentication((HttpServletRequest)request, list_roles);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }
}
