package quangnnfx16178.ilovevn.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import quangnnfx16178.ilovevn.entity.Role;
import quangnnfx16178.ilovevn.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authories = new ArrayList<>();
        Role role = user.getRole();
        authories.add(new SimpleGrantedAuthority(role.getName()));
        return authories;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public User getUser() { return this.user; }

    public String getEmail() {
        return this.user.getEmail();
    }

    public String getFullName() {
        return this.user.getFullName();
    }

    public String getProfilePhotoPath() {
        return this.user.getProfilePhotoPath();
    }

    public boolean hasRole(String roleName) {
        return user.hasRole(roleName);
    }

}
