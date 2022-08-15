package quangnnfx16178.ilovevn.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "full_name", nullable = false, length = 60)
    private String fullName;
    @Basic
    @Column(name = "email", nullable = false, length = 150)
    private String email;
    @Basic
    @Column(name = "password", nullable = false, length = 64)
    @JsonIgnore
    private String password;
    @Basic
    @Column(name = "address", nullable = true, length = 200)
    private String address;
    @Basic
    @Column(name = "phone_number", nullable = true, length = 15)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;
    @Basic
    @Column(name = "profile_photo", nullable = true, length = 255)
    private String profilePhoto;
    @Basic
    @Column(name = "authentication_type", nullable = true, length = 10)
    @JsonIgnore
    private String authenticationType;
    @Basic
    @Column(name = "reset_password_token", nullable = true, length = 30)
    @JsonIgnore
    private String resetPasswordToken;

    @Basic
    @Column(name = "enabled")
    private boolean enabled;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Donation> donations = new ArrayList<>();


    @Transient
    public String getProfilePhotoPath() {
        if (id == null || profilePhoto == null) return "/images/default-user.png";
        return "images/user-images/" + this.id + "/" + this.profilePhoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(fullName, user.fullName)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(address, user.address)) return false;
        if (!Objects.equals(phoneNumber, user.phoneNumber)) return false;
        if (!Objects.equals(role, user.role)) return false;
        if (!Objects.equals(profilePhoto, user.profilePhoto)) return false;
        if (!Objects.equals(authenticationType, user.authenticationType))
            return false;
        if (!Objects.equals(resetPasswordToken, user.resetPasswordToken))
            return false;
        return Objects.equals(donations, user.donations);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (profilePhoto != null ? profilePhoto.hashCode() : 0);
        result = 31 * result + (authenticationType != null ? authenticationType.hashCode() : 0);
        result = 31 * result + (resetPasswordToken != null ? resetPasswordToken.hashCode() : 0);
        result = 31 * result + (donations != null ? donations.hashCode() : 0);
        return result;
    }
}
