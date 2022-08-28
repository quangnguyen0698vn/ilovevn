package quangnnfx16178.ilovevn.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import quangnnfx16178.ilovevn.security.MyUserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO implements UserDetails {
    private Integer id;
    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;
    private Role role;
    private String profilePhoto;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authories = new ArrayList<>();
        Role role = this.getRole();
        authories.add(new SimpleGrantedAuthority(role.getName()));
        return authories;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
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
        return this.enabled;
    }

    public String getProfilePhotoPath() {
        if (id == null || profilePhoto == null) return "/images/default-user.png";
        return "/images/user-images/" + this.id + "/" + this.profilePhoto;
    }

    public UserDTO(User aUser) {
        this.setId(aUser.getId());
        this.setFullName(aUser.getFullName());
        this.setEmail(aUser.getEmail());
        this.setAddress(aUser.getAddress());
        this.setPhoneNumber(aUser.getPhoneNumber());
        this.setRole(aUser.getRole());
        this.setProfilePhoto(aUser.getProfilePhoto());
        this.setEnabled(aUser.isEnabled());
    }

    public UserDTO(UserDetails user) {
        new UserDTO(((MyUserDetails) user).getUser());
    }
}
