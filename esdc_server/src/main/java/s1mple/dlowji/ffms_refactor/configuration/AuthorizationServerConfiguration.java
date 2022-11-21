package s1mple.dlowji.ffms_refactor.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import s1mple.dlowji.ffms_refactor.exceptions.CustomAccessDeniedException;
import s1mple.dlowji.ffms_refactor.exceptions.CustomAuthenEntryPointException;
import s1mple.dlowji.ffms_refactor.filters.JwtFilter;

import java.time.DayOfWeek;
import java.util.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationServerConfiguration {

	@Autowired
	@Lazy
	private JwtFilter jwtFilter;
	@Autowired
	private CustomAccessDeniedException customAccessDeniedException;

	@Autowired
	private CustomAuthenEntryPointException customAuthenEntryPointException;
	@Bean
	public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors(Customizer.withDefaults());
		http.authorizeRequests((auth) -> {
			auth.antMatchers("/auth/**").permitAll();
			auth.antMatchers(("/api/football_fields/**")).permitAll();
			auth.antMatchers(("/api/services/**")).permitAll();
			auth.anyRequest().authenticated();
		});
		http.addFilterAt(jwtFilter, BasicAuthenticationFilter.class);
		http.exceptionHandling((ex) -> {
			ex.accessDeniedHandler(customAccessDeniedException);
			ex.authenticationEntryPoint(customAuthenEntryPointException);
		});

		return http.build();
	}
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(List.of("*"));
		// Don't do this in production, use a proper list  of allowed origins
		config.setAllowedOrigins(Collections.singletonList("*"));
		config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(daoAuthenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
