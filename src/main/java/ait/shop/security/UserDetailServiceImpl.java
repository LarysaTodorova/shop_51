package ait.shop.security;

import ait.shop.exception.handling.exceptions.RegistrationException;
import ait.shop.model.dto.UserRegistrationDTO;
import ait.shop.model.entity.Role;
import ait.shop.model.entity.User;
import ait.shop.repository.RoleRepository;
import ait.shop.repository.UserRepository;

import ait.shop.service.interfaces.EmailService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    public UserDetailServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public void register(UserRegistrationDTO registrationDTO) {
        User user = userRepository.findByEmail(registrationDTO.getEmail()).orElse(null);

        if (user != null && user.isActive()) {
            throw new RegistrationException("This email already exists");
        }

        if (user == null) {
            user = new User();
            user.setEmail(registrationDTO.getEmail());

            Role userRole = roleRepository.findByTitle("ROLE_USER").orElseThrow(
                    () -> new RuntimeException("There is no ROLE_USER in the DB")
            );
            user.setRoles(Set.of(userRole));
        }

        user.setUsername(registrationDTO.getUsername());
        String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        emailService.sendConfirmationEmail(user);
    }
}
