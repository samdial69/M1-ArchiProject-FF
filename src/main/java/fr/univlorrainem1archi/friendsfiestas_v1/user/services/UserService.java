package fr.univlorrainem1archi.friendsfiestas_v1.user.services;

import fr.univlorrainem1archi.friendsfiestas_v1.user.models.User;
import fr.univlorrainem1archi.friendsfiestas_v1.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserService implements IUser{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
