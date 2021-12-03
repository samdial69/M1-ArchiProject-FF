package fr.univlorrainem1archi.friendsfiestas_v1.user.services;

import fr.univlorrainem1archi.friendsfiestas_v1.user.models.User;
import fr.univlorrainem1archi.friendsfiestas_v1.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Qualifier("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        User user = userRepository.findUserByPseudo(pseudo);
        if (user == null){
            throw new UsernameNotFoundException("User not found by pseudo "+pseudo);
        }else {
            return new UserDetailsImpl(user);
        }
    }
}
