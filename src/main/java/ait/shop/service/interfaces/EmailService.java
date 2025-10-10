package ait.shop.service.interfaces;

import ait.shop.model.dto.UserRegistrationDTO;
import ait.shop.model.entity.User;

public interface EmailService {

    void sendConfirmationEmail(User user);
}
