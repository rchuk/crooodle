package org.ukma.spring.crooodle.usersvc.utils.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
	@Value("${auth.keys.private}")
	Resource privateKeyPem;
	@Value("${auth.keys.public}")
	Resource publicKeyPem;

	@Bean @Order(1)
	SecurityFilterChain asSecurityFilterChain(HttpSecurity http) throws Exception {
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
			.oidc(Customizer.withDefaults());
		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
		http.cors(Customizer.withDefaults())
			.csrf(csrf -> csrf.ignoringRequestMatchers("/oauth2/token", "/oauth2/introspection"));

		return http.build();
	}

	@Bean @Order(2)
	SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/actuator/health").permitAll()
				.anyRequest().permitAll()
			)
			.formLogin(Customizer.withDefaults())
			.cors(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		var cfg = new CorsConfiguration();
		cfg.setAllowedOrigins(List.of("http://localhost:8080"));
		cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		cfg.setAllowedHeaders(List.of("*"));
		cfg.setAllowCredentials(true);

		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", cfg);

		return source;
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbc) {
		return new JdbcRegisteredClientRepository(jdbc);
	}

	@Bean
	public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbc, RegisteredClientRepository clients) {
		return new JdbcOAuth2AuthorizationService(jdbc, clients);
	}

	@Bean
	public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbc, RegisteredClientRepository clients) {
		return new JdbcOAuth2AuthorizationConsentService(jdbc, clients);
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		try {
			RSAPrivateKey priv;
			RSAPublicKey pub;
			try (InputStream is = privateKeyPem.getInputStream()) {
				priv = RsaKeyConverters.pkcs8().convert(is);
			}
			try (InputStream is = publicKeyPem.getInputStream()) {
				pub = RsaKeyConverters.x509().convert(is);
			}
			var rsa = new RSAKey.Builder(pub)
				.privateKey(priv)
				.keyID("auth-key-1")
				.build();
			return new ImmutableJWKSet<>(new JWKSet(rsa));
		} catch (Exception e) {
			throw new IllegalStateException("Failed to load RSA keys from PEM", e);
		}
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder(
			"", // TODO
			16,
			310_000,
			Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
		);
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		var dao = new DaoAuthenticationProvider(userDetailsService);
		dao.setPasswordEncoder(passwordEncoder);

		return new org.springframework.security.authentication.ProviderManager(dao);
	}
}
