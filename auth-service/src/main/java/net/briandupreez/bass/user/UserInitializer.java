package net.briandupreez.bass.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties(prefix = "auth.local")
class UserInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private HashMap<String, List<String>> users;

    UserInitializer(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(final String... args) throws Exception {
        getUsers().forEach((username, roles) -> {
            final ArrayList<Role> result = roles.stream().map(Role::new).collect(Collectors.toCollection(ArrayList::new));
            userRepository.save(new User(username, "password", result));
        });
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public HashMap<String, List<String>> getUsers() {
        return users;
    }

    public void setUsers(final HashMap<String, List<String>> users) {
        this.users = users;
    }
}
