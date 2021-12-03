package fr.univlorrainem1archi.friendsfiestas_v1.user.services;

import fr.univlorrainem1archi.friendsfiestas_v1.security.exception.EmailExistException;
import fr.univlorrainem1archi.friendsfiestas_v1.security.exception.UsernameExistException;
import fr.univlorrainem1archi.friendsfiestas_v1.security.jwt.JwtTokenProvider;
import fr.univlorrainem1archi.friendsfiestas_v1.security.jwt.SecurityConstant;
import fr.univlorrainem1archi.friendsfiestas_v1.user.models.*;
import fr.univlorrainem1archi.friendsfiestas_v1.user.repository.RoleRepo;
import fr.univlorrainem1archi.friendsfiestas_v1.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserService implements IUser{
    private final UserRepository userRepository;
    private final RoleRepo roleRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepo roleRepo, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepo = roleRepo;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean existById(Long idUser) {
        return userRepository.existsById(idUser);
    }

    public User getUser(Long idUser) {
        return userRepository.findById(idUser).orElseThrow();
    }

    @Override
    public User register(RequestBodyUser user) throws EmailExistException, UsernameExistException {
        Role role = new Role(null, EnumRole.ROLE_USER);
        roleRepo.save(role);
        validateUsernameAndEmail(StringUtils.EMPTY,user.getPseudo(),user.getEmail());
        String encodePassword = encodePassword(user.getPassword());

        User userConvert = this.convert(user);
        userConvert.setPassword(encodePassword);
        userConvert.setRoles(List.of(role));
        userRepository.save(userConvert);
        log.info("User register by pseudo {}",user.getPseudo());
        return userConvert;

    }

    private User convert(RequestBodyUser user) {
        UserDTO userDTO = userMapper.to(user);
        return this.userMapper.to(userDTO);
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public List<Object> login(RequestBodyUser user) {
        authenticate(user.getPseudo(),user.getPassword());
         User loginUser = this.findUserByPseudo(user.getPseudo());
        if (loginUser == null){
        throw new UsernameNotFoundException("Not user found by pseudo "+user.getPseudo());
    }
        UserDetailsImpl userDetails = new UserDetailsImpl(loginUser);
        HttpHeaders headers = getJWTHeader(userDetails);
        return List.of(loginUser,headers);
    }

    private void authenticate(String pseudo, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(pseudo,password));
    }

    private HttpHeaders getJWTHeader(UserDetailsImpl userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstant.JWT_TOKEN_HEADER,jwtTokenProvider.generateJwtToken(userDetails));
        return headers;
    }

    private User validateUsernameAndEmail(String currentPseudo,String pseudo,String email) throws UsernameNotFoundException, UsernameExistException, EmailExistException {
        if(StringUtils.isNotBlank(currentPseudo)){
            User currentUser = findUserByPseudo(currentPseudo);
            if (currentUser == null){
                throw new UsernameNotFoundException("No user found by pseudo" + currentPseudo);
            }
            User userByPseudo = findUserByPseudo(pseudo);
            if (userByPseudo != null && !currentUser.getId().equals(userByPseudo.getId())){
                throw new UsernameExistException("username already exist");
            }
            User userByEmail = findUserByEmail(email);
            if (userByEmail != null && !currentUser.getId().equals(userByEmail.getId())){
                throw new EmailExistException("username already exist");
            }
            return currentUser;
        }else {
            User userByPseudo = findUserByPseudo(pseudo);
            if (userByPseudo != null){
                throw new UsernameExistException("username already exist");
            }
            User userByEmail = findUserByEmail(email);
            if (userByEmail != null){
                throw new EmailExistException("username already exist");
            }
            return null;
        }
    }

    @Override
    public User findUserByPseudo(String pseudo) {
        return userRepository.findUserByPseudo(pseudo);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


}
