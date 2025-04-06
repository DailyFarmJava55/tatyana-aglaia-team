package telran.dayli_farm.security;

import static telran.dayli_farm.api.ApiConstants.*;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import telran.dayli_farm.security.login.LoginRoleFilter;
import telran.dayli_farm.security.service.RevokedTokenService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final RevokedTokenService revokedTokenService;
	private final LoginRoleFilter loginRoleFilter;


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.cors(Customizer.withDefaults())
		.csrf(csrf -> csrf.disable())
		
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						// .requestMatchers("/swagger-ui/**", "/v3/**").permitAll()

						.requestMatchers(FARMER_REGISTER, FARMER_LOGIN, FARMER_REFRESH_TOKEN,
								CUSTOMER_REGISTER, CUSTOMER_LOGIN, CUSTOMER_REFRESH_TOKEN, GET_ALL_SURPRISE_BAGS,
								"/swagger-ui/**", "/v3/**", "/surprise_bag/**", "/orders/**").permitAll()
						.requestMatchers(HttpMethod.PUT, "/surprise_bag/*/decrement").permitAll()
						
						
						
					//	.requestMatchers("/farmer/**").hasRole("FARMER")
					//	.requestMatchers("/customer/**").hasRole("CUSTOMER")
						.anyRequest().authenticated())
						
				.addFilterBefore(loginRoleFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JwtAuthFilter(jwtService, userDetailsService, revokedTokenService),
					UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService((UserDetailsService) userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(provider);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowedOrigins(List.of("http://localhost:8000"));
	    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    config.setAllowedHeaders(List.of("*"));
	    config.setAllowCredentials(true); 

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}
	
}
