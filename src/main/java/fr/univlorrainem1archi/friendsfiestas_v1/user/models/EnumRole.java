package fr.univlorrainem1archi.friendsfiestas_v1.user.models;

import java.util.List;

import static fr.univlorrainem1archi.friendsfiestas_v1.user.models.Authority.ADMIN_AUTHORITIES;
import static fr.univlorrainem1archi.friendsfiestas_v1.user.models.Authority.USER_AUTHORITIES;

public enum EnumRole {
    ROLE_USER(USER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES);

    private final List<String> authorities;

    EnumRole(List<String> authorities){
        this.authorities = authorities;
    }
    public List<String> getAuthorities(){
        return authorities;
    }
}
