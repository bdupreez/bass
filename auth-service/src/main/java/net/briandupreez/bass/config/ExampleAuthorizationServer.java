package net.briandupreez.bass.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer
public class ExampleAuthorizationServer extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;

    @ConfigurationProperties(prefix = "auth.keystore")
    @Component
    public class KeystoreDetails {
        private String path;
        private String password;
        private String keyPair;

        public KeystoreDetails() {
        }

        public String getPath() {
            return path;
        }

        public void setPath(final String path) {
            this.path = path;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(final String password) {
            this.password = password;
        }

        public String getKeyPair() {
            return keyPair;
        }

        public void setKeyPair(final String keyPair) {
            this.keyPair = keyPair;
        }
    }


    @Autowired
    private KeystoreDetails keystoreDetails;

    @Autowired
    public ExampleAuthorizationServer(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(tokenStore());
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()
                .withClient("bass").secret("secret")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_USER", "ROLE_ADMIN")
                .scopes("read", "write")
                .autoApprove(true)
                .accessTokenValiditySeconds(60 * 60 * 24);
        // @formatter:on
    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        final KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource(keystoreDetails.getPath()), keystoreDetails.getPassword().toCharArray());
        final KeyPair keyPair = keyStoreKeyFactory.getKeyPair(keystoreDetails.getKeyPair());
        converter.setKeyPair(keyPair);
        return converter;
    }


    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

}