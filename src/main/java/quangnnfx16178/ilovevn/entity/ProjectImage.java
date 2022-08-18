package quangnnfx16178.ilovevn.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "project_images")
public class ProjectImage implements Comparable<ProjectImage> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    private Project project;

    @Basic
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    public ProjectImage(Integer id, Project project, String fileName) {
        this.id = id;
        this.project = project;
        this.fileName = fileName;
    }

    public ProjectImage(Project project, String fileName) {
        this.project = project;
        this.fileName = fileName;
    }


    @Transient
    public String getImagePath() {
        if (id == null || fileName == null) return "/images/default-project-image.png";
        return "/images/project-images/" + this.project.getId() + "/" + this.fileName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectImage that = (ProjectImage) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(project, that.project)) return false;
        return Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(ProjectImage that) {
        return Integer.compare(this.getId(), that.getId());
    }
}
