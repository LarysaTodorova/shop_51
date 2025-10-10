package ait.shop.service.implService;

import ait.shop.model.dto.UserRegistrationDTO;
import ait.shop.model.entity.ConfirmationCode;
import ait.shop.model.entity.User;
import ait.shop.repository.ConfirmCodeRepository;
import ait.shop.service.interfaces.ConfirmCodeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmCodeServiceImpl implements ConfirmCodeService {

    private final ConfirmCodeRepository repository;

    public ConfirmCodeServiceImpl(ConfirmCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generateConfirmCode(User user) {
        LocalDateTime expired = LocalDateTime.now().plusMinutes(5);
        String value = UUID.randomUUID().toString();
        ConfirmationCode code = new ConfirmationCode(value, expired, user);
        repository.save(code);
        return value;
    }
}
