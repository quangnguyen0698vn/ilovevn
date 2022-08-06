package quangnnfx16178.ilovevn.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import quangnnfx16178.ilovevn.project.Project;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "donations")
public class Donation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Basic
    @Column(name = "account_number", nullable = false, length = 15)
    private String accountNumber;
    @Basic
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
    @Basic
    @Column(name = "amount", nullable = false, precision = 0)
    private Integer amount;
    @Basic
    @Column(name = "trans_ref_no", nullable = false, length = 15)
    private String transRefNo;
    @Basic
    @Column(name = "message", nullable = true, length = 5000)
    private String message;
    @Basic
    @Column(name = "state", nullable = false, length = 20)
    private String state;
    @Basic
    @Column(name = "created_time", nullable = true, updatable=false)
    @CreationTimestamp
    private Timestamp createdTime;
    @Basic
    @Column(name = "updated_time", nullable = true)
    @UpdateTimestamp
    private Timestamp updatedTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Donation donation = (Donation) o;

        if (!Objects.equals(id, donation.id)) return false;
        if (!Objects.equals(project, donation.project)) return false;
        if (!Objects.equals(accountNumber, donation.accountNumber))
            return false;
        if (!Objects.equals(fullName, donation.fullName)) return false;
        if (!Objects.equals(amount, donation.amount)) return false;
        if (!Objects.equals(transRefNo, donation.transRefNo)) return false;
        if (!Objects.equals(message, donation.message)) return false;
        if (!Objects.equals(state, donation.state)) return false;
        if (!Objects.equals(createdTime, donation.createdTime))
            return false;
        if (!Objects.equals(updatedTime, donation.updatedTime))
            return false;
        return Objects.equals(user, donation.user);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (transRefNo != null ? transRefNo.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (updatedTime != null ? updatedTime.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTransRefNo() {
        return transRefNo;
    }

    public void setTransRefNo(String transRefNo) {
        this.transRefNo = transRefNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
