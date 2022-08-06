package quangnnfx16178.ilovevn.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
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
    private String authenticationType;
    @Basic
    @Column(name = "reset_password_token", nullable = true, length = 30)
    private String resetPasswordToken;

    // BELOW IS ADDED MANUALLY BY QUANG

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Donation> donations;


    @Transient
    public String getProfilePhotoPath() {
        if (id == null || profilePhoto == null) return "/images/default-user.png";
        return "images/user-images/" + this.id + "/" + this.profilePhoto;
    }

    // END OF QUANG'S CODE


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public Set<Donation> getDonations() {
        return donations;
    }

    public void setDonations(Set<Donation> donations) {
        this.donations = donations;
    }
}
