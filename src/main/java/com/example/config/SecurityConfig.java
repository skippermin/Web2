package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // http 요청에 대한 보안을 설정 한다. 페이지 권한 설정, 로그인페이지 설정, 로그아웃 메소드 등에 대한 설정을 작성.
    // WebSecurityConfigurerAdapter를 상속 받아서 메소드 오버라이딩을 통한 보안 설정을 커스터마이징 할 수 있습니다.
    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin()
                //로그인 페이지 URL
                .loginPage("/members/login")
                // 로그인 성공시 이동할 URL
                .defaultSuccessUrl("/")
                // 로그인 시 사용할 파라미터 이름으로 email을 지정
                .usernameParameter("email")
                // 로그인 실패시 이동할 url
                .failureUrl("/members/login/error")
                .and()
                .logout()
                //로그아웃 성공 시 이동할 URL을 설정한다.
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/")
        ;
            // 시큐리티 처리에 HTTPServletRequest를 이용한다는 것을 의미한다.
            // permitAll을 통해 모든 사용자가 인증 없이 해당 경로에 접근할 수 있도록 설정합니다. 메인페이지 회원 관련 URL 뒤에서 만들 상품 페이지,
            // 상품 이미지, 불러오는 경로가 이에 해당되며, /admin으로 시작하는 경로는 해당 계정이  admin role일 경우에만 접근 가능하도록 설정
        http.authorizeRequests()
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        ;
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;
    }
    // 비밀번호 데이터베이스에 그대로 저장하면 해킹당할 위험이 있기에 회원 정보 그대로 노출
    // 해시 함수를 이용하여 비밀번호를 암호화하여 저장한다.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //static 디렉터리의 하위 파일은 인증을 무시하도록 설정해야한다.
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }
}
