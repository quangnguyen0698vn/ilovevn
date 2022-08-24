package quangnnfx16178.ilovevn.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Đây là class Project - entity này được mapping với bảng projects của cơ sở dữ liệu, lưu trữ thông tin của từng dự án như
 * tên, mô tả ngắn, mô tả đầy đủ, số tiền muốn quyên góp, số tiền đã quyên góp được, ngày bắt đầu, ngày kết thúc, lưu trữ
 * tên các hình ảnh mô tả dự án,...
 * Sử dụng Project Lombok để tránh boilerplate code cho getter, setter và constructor
 * @Author Nguyễn Ngọc Quang
 */
@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(name = "Project.findByProjectIdWithLocking", query = "SELECT p FROM Project p WHERE p.id=:id", lockMode = LockModeType.PESSIMISTIC_WRITE)
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
    @JsonIgnore
    private String fullDescription;
    @Basic
    @Column(name = "main_image", nullable = true, length = 255)
    private String mainImage;
    @Basic
    @Column(name = "created_time", nullable = true, updatable = false)
    @CreationTimestamp
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Basic
    @Column(name = "updated_time", nullable = true)
    @UpdateTimestamp
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Basic
    @Column(name = "started_date", nullable = false, updatable = true)
    @Temporal(TemporalType.DATE)
    private Date startedDate;

    @Basic
    @Column(name = "expired_date", nullable = false, updatable = true)
    @Temporal(TemporalType.DATE)
    private Date expiredDate;

    @Basic
    @Column(name = "raised_amount", nullable = false)
    private Long raisedAmount = 0L;

    @Basic
    @Column(name = "number_donations")
    private Integer numberOfDonations;

    @Basic
    @Column(name = "target_amount", nullable = false)
    private Long targetAmount;

    @Formula(value = "raised_amount / target_amount")
    private Double raisedPercentage;

    @ManyToOne
    @JoinColumn(name = "charity_id")
    private Charity charity;

    /**
     *  Cách mapping như dưới đây đảm bảo mỗi khi dự án được xóa khỏi bảng projects thì thông tin liên quan cũng được xóa
     *  khỏi bẳng project_images
     *  Tham khảo thêm tại https://www.baeldung.com/jpa-cascade-types
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ProjectImage> images = new HashSet<>();


    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @OrderBy("id asc")
    @JsonIgnore
    private List<Donation> donations = new ArrayList<>();

    public Long getNumberOfDaysLeft() {
        if (this.startedDate == null || this.expiredDate == null) return -1L;
        long diffInMillies = Math.abs(this.expiredDate.getTime() - this.startedDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }

    /**
     * So sánh ngày bắt đầu thực hiên quyên góp với ngày hôm nay
     * @return true nếu dự án đã bắt đầu quyên góp, false nếu dự án chưa được bắt đầu
     */
    public boolean isAlreadyStarted() {
        if (this.startedDate == null) return false;
        java.util.Date date = new java.util.Date();
        int compare = date.compareTo(this.startedDate);
        return compare >= 0;
    }


    /**
     * Hàm trả về path đến hình ảnh chính của dự án (href = origin + path)
     * Hình ảnh chính là hình ảnh sẽ hiển thị khi list dự án ở landing page
     * @return  path đến hình ảnh chính của dự án
     */
    public String getImagePath() {
        if (id == null || mainImage == null) return "/images/default-project-image.png";
        return "/images/project-images/" + this.id + "/main/" + this.mainImage;
    }


    /**
     * Kiểm tra xem filename có nằm trong danh sách hình ảnh minh họa của dự án
     * @param fileName: tên file hình ảnh (ví dụ "example.png")
     * @return  true hoặc false
     */
    public boolean containsImageName(String fileName) {
        if (images == null) return false;
        return images.stream().anyMatch(image -> image.getFileName().equals(fileName));
    }

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

