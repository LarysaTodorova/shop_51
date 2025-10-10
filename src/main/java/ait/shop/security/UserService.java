package ait.shop.security;

import ait.shop.model.dto.UserRegistrationDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(UserRegistrationDTO registrationDTO);
}
