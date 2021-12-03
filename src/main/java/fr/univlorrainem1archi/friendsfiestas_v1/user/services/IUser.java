package fr.univlorrainem1archi.friendsfiestas_v1.user.services;

import fr.univlorrainem1archi.friendsfiestas_v1.security.exception.EmailExistException;
import fr.univlorrainem1archi.friendsfiestas_v1.security.exception.UsernameExistException;
import fr.univlorrainem1archi.friendsfiestas_v1.user.models.RequestBodyUser;
import fr.univlorrainem1archi.friendsfiestas_v1.user.models.User;

import java.util.List;

public interface IUser {
    List<User> getUsers();
    boolean existById(Long idUser);
    User findUserByPseudo(String pseudo);
    User findUserByEmail(String email);
    List<Object> login(RequestBodyUser user);
    User register(RequestBodyUser user) throws EmailExistException, UsernameExistException;
}
