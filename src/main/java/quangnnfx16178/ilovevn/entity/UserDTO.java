package quangnnfx16178.ilovevn.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Integer id;
    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;
    private Role role;
    private String profilePhoto;
    private boolean enabled;


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
}
