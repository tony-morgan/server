package com.example.security.services;

import com.example.security.models.*;
import com.example.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.security.models.*;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class AuthenticationUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Wraps a {@link Role} role to {@link SimpleGrantedAuthority} objects
     *
     * @param role {@link String} of roles
     * @return list of granted authorities
     */
    public static Set<SimpleGrantedAuthority> getGrantedAuthorities(Role role) {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        Set<RolePermission> rolePermissions = new HashSet<>(role.getRolePermissions());
        for (RolePermission rolePermission : rolePermissions) {
            authorities.add(new SimpleGrantedAuthority(rolePermission.getPermission().getName()));
        }

        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        try {
            User user = userRepository.findByUsername(username);

            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            // adapt as needed
            return new CustomUserDetails(user.getUsername(),
                    user.getEncryptedPassword(), enabled, accountNonExpired,
                    credentialsNonExpired, accountNonLocked,
                    getAuthorities(user.getUserRoles()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a collection of {@link GrantedAuthority} based on a list of
     * roles
     *
     * @param userRoles the assigned roles of the user
     * @return a collection of {@link GrantedAuthority}
     */
    public Collection<? extends GrantedAuthority> getAuthorities(Collection<UserRole> userRoles) {

        Set<SimpleGrantedAuthority> authList = new TreeSet<>(
                new SimpleGrantedAuthorityComparator());

        for (UserRole userRole : userRoles) {
            authList.addAll(getGrantedAuthorities(userRole.getRole()));
        }

        return authList;
    }

    private static class SimpleGrantedAuthorityComparator implements
            Comparator<SimpleGrantedAuthority> {

        @Override
        public int compare(SimpleGrantedAuthority o1, SimpleGrantedAuthority o2) {
            return o1.equals(o2) ? 0 : -1;
        }
    }
}
