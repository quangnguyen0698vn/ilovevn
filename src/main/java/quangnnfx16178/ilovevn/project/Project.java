package quangnnfx16178.ilovevn.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import quangnnfx16178.ilovevn.entity.Charity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
public class Project {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    @Basic
    @Column(name = "short_description", nullable = false, length = 5000)
    private String shortDescription;
    @Basic
    @Column(name = "full_description", nullable = false, length = 10000)
    private String fullDescription;
    @Basic
    @Column(name = "main_image", nullable = true, length = 255)
    private String mainImage;
    @Basic
    @Column(name = "created_time", nullable = true, updatable = false)
    @CreationTimestamp
    private Timestamp createdTime;
    @Basic
    @Column(name = "updated_time", nullable = true)
    @UpdateTimestamp
    private Timestamp updatedTime;

    @Basic
    @Column(name = "started_date", nullable = false, updatable = false)
    private Date startedDate;

    @Basic
    @Column(name = "expired_date", nullable = false, updatable = false)
    private Date expiredDate;
    @Basic
    @Column(name = "target_amount", nullable = false)
    private Long targetAmount;

    @ManyToOne
    @JoinColumn(name = "charity_id")
    private Charity charity;

    // BELOW IS ADDED MANUALLY BY QUANG


    //    https://www.baeldung.com/jpa-cascade-types
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id asc")
    private Set<ProjectImage> images;


    @Transient
    public String getImagePath() {
        if (id == null || mainImage == null) return "/images/default-project-image.png";
        return "/images/project-images/" + this.id + "/main/" + this.mainImage;
    }

    public boolean containsImageName(String fileName) {
        if (images == null) return false;
        return images.stream().anyMatch(image -> image.getFileName().equals(fileName));
    }

    // END OF QUANG'S CODE


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (!Objects.equals(id, project.id)) return false;
        if (!Objects.equals(name, project.name)) return false;
        if (!Objects.equals(shortDescription, project.shortDescription))
            return false;
        if (!Objects.equals(fullDescription, project.fullDescription))
            return false;
        if (!Objects.equals(mainImage, project.mainImage)) return false;
        if (!Objects.equals(createdTime, project.createdTime)) return false;
        if (!Objects.equals(updatedTime, project.updatedTime)) return false;
        if (!Objects.equals(startedDate, project.startedDate)) return false;
        if (!Objects.equals(expiredDate, project.expiredDate)) return false;
        if (!Objects.equals(targetAmount, project.targetAmount))
            return false;
        return Objects.equals(charity, project.charity);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (fullDescription != null ? fullDescription.hashCode() : 0);
        result = 31 * result + (mainImage != null ? mainImage.hashCode() : 0);
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (updatedTime != null ? updatedTime.hashCode() : 0);
        result = 31 * result + (startedDate != null ? startedDate.hashCode() : 0);
        result = 31 * result + (expiredDate != null ? expiredDate.hashCode() : 0);
        result = 31 * result + (targetAmount != null ? targetAmount.hashCode() : 0);
        result = 31 * result + (charity != null ? charity.hashCode() : 0);
        return result;
    }
}

