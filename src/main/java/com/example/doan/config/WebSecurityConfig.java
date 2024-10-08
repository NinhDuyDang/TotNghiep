package com.example.doan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImp();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register/**", "/reset_password", "/change_password", "/confirmed",
                        "/enter_password",
                        "/**/*.js", "/**/*.css")
                .permitAll() // Các trang này cho phép truy cập mà không cần xác thực
                .antMatchers("/admin/**") // Chỉ cho phép truy cập vào các trang admin cho người dùng có quyền ADMIN
                .hasRole("ADMIN") // Kiểm tra người dùng có vai trò ADMIN
                .anyRequest()
                .authenticated() // Bất kỳ yêu cầu nào khác đều cần xác thực
                .and()
                .formLogin()
                .loginPage("/login") // Đặt trang đăng nhập tùy chỉnh
                .permitAll() // Cho phép tất cả mọi người truy cập trang đăng nhập
                .and()
                .logout()
                .permitAll(); // Cho phép tất cả mọi người thực hiện logout
    }

}
