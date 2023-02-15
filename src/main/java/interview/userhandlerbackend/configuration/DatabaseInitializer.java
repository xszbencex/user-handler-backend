package interview.userhandlerbackend.configuration;

import interview.userhandlerbackend.dto.UserDTO;
import interview.userhandlerbackend.model.Role;
import interview.userhandlerbackend.repository.UserRepository;
import interview.userhandlerbackend.service.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    private final UserRepository userRepository;
    private final UserService userService;

    public DatabaseInitializer(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostConstruct
    public void initAccount() {
        if (!userRepository.existsByUsername("admin")) {
            UserDTO admin = new UserDTO();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@email.com");
            admin.setRoles(new ArrayList<>(List.of(Role.ADMIN)));

            userService.signUp(admin);
        }
        if (!userRepository.existsByUsername("client")) {
            UserDTO client = new UserDTO();
            client.setUsername("client");
            client.setPassword("client");
            client.setEmail("client@email.com");
            client.setRoles(new ArrayList<>(List.of(Role.PUBLIC)));

            userService.signUp(client);
        }
    }
}
