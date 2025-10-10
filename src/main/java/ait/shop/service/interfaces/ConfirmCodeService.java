package ait.shop.service.interfaces;

import ait.shop.model.dto.UserRegistrationDTO;
import ait.shop.model.entity.User;

public interface ConfirmCodeService {

    String generateConfirmCode(User user);
}
