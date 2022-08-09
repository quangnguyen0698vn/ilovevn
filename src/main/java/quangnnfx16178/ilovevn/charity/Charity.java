package quangnnfx16178.ilovevn.charity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "charities")
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
    private String shortDescription;
    @Basic
    @Column(name = "full_description", nullable = true, length = 4096)
    private String fullDescription;
    @Basic
    @Column(name = "logo", nullable = false, length = 255)
    private String logo;

    // BELOW IS ADDED MANUALLY BY QUANG

    @Transient
    public String getCharityLogoPath() {
        if (id == null || logo == null) return "/images/default-charity.png";
        return "images/charity-images/" + this.id + "/" + this.logo;
    }

    // END OF QUANG'S CODE

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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
