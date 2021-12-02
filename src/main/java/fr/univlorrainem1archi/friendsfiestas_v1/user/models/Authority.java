package fr.univlorrainem1archi.friendsfiestas_v1.user.models;

import java.util.ArrayList;
import java.util.Arrays;

public class Authority {
    public static final ArrayList<String> USER_AUTHORITIES =new ArrayList<>(Arrays.asList("user:read","user:create"));
    public static final ArrayList<String> ADMIN_AUTHORITIES =new ArrayList<>(Arrays.asList("user:read","user:create","user:delete"));
}
