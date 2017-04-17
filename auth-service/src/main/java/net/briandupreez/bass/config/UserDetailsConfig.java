package net.briandupreez.bass.config;


import net.briandupreez.bass.user.CustomUserDetails;
import net.briandupreez.bass.user.User;
import net.briandupreez.bass.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class UserDetailsConfig {

    @Bean
    UserDetailsService userDetailsService(final UserRepository userRepository) {
        return new LocalUserDetailsService(userRepository);
    }

    class LocalUserDetailsService implements UserDetailsService {

        private final UserRepository userRepository;

        public LocalUserDetailsService(final UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
            final Optional<User> found = userRepository.findByUsername(username);
            return found.map(user -> new CustomUserDetails(found.get()))
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user: " + username));
        }
    }

}



