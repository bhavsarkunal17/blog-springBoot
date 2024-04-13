package com.project.blog.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.function.DoubleToIntFunction;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */

    private final CustomerUserDetailsService customerUserDetailsService;
    private final JwtTokenHelper jwtTokenHelper;

    @Autowired
    public JwtAuthenticationFilter(CustomerUserDetailsService customerUserDetailsService, JwtTokenHelper jwtTokenHelper) {
        this.customerUserDetailsService = customerUserDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");
        System.out.println(bearerToken);
        String userName = null;
        String accessToken = null;

        if(bearerToken != null && bearerToken.startsWith("Bearer")){
            accessToken = bearerToken.substring(7);

            try {
                userName = this.jwtTokenHelper.getUsernameFromToken(accessToken);

                if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = this.customerUserDetailsService.loadUserByUsername(userName);

                    if(this.jwtTokenHelper.validateToken(accessToken,userDetails)){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    }
                }
            }catch (IllegalArgumentException ex){
                System.out.println("Illegal argument "+ ex.getMessage());
            }catch (JwtException ex){
                System.out.println("Jwt exception "+ex.getMessage());
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }


        }else{
            if(bearerToken == null){
                System.out.println("token is null");
            }else{
                System.out.println("token does not starts with bearer");
            }

        }

        filterChain.doFilter(request,response);



    }
}
