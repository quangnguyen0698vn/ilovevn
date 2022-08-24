package quangnnfx16178.ilovevn.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "charities")
@Getter
@Setter
public class Charity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    @Basic
    @Column(name = "short_description", nullable = true, length = 512)
//    @JsonIgnore
    private String shortDescription;
    @Basic
    @Column(name = "full_description", nullable = true, length = 4096)
    @JsonIgnore
    private String fullDescription;
    @Basic
    @Column(name = "logo", nullable = false, length = 255)
    private String logo;

    @Basic
    @Column(name = "account_number")
    private String accountNumber;

    @Formula("length(full_description) > 0")
    private Boolean havingDetails;
    // BELOW IS ADDED MANUALLY BY QUANG

    @Transient
    public String getCharityLogoPath() {
        if (id == null || logo == null) return "/images/default-charity.png";
        return "/images/charity-images/" + this.id + "/" + this.logo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Charity charity = (Charity) o;

        if (!Objects.equals(id, charity.id)) return false;
        if (!Objects.equals(name, charity.name)) return false;
        if (!Objects.equals(shortDescription, charity.shortDescription))
            return false;
        if (!Objects.equals(fullDescription, charity.fullDescription))
            return false;
        return Objects.equals(logo, charity.logo);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (fullDescription != null ? fullDescription.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        return result;
    }
}
