package quangnnfx16178.ilovevn.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class DonationDTO {
    private Integer id;
    private Integer project_id;
    private String fullName;
    private Integer amount;
    private String transRefNo;
    private String message;
    private StateType state;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Integer user_id;

    public DonationDTO(Donation donation) {
        this.id = donation.getId();
        this.project_id = donation.getProject().getId();
        this.fullName = donation.getFullName();
        this.amount = donation.getAmount();
        this.transRefNo = donation.getTransRefNo();
        this.message = donation.getMessage();
        this.state = donation.getState();
        this.createdTime = donation.getCreatedTime();
        this.updatedTime = donation.getUpdatedTime();
        this.user_id = donation.getUser() != null ? donation.getUser().getId() : null;
    }
}
