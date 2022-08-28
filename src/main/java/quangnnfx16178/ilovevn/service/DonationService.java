package quangnnfx16178.ilovevn.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.extract.spi.ExtractionContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.entity.Donation;
import quangnnfx16178.ilovevn.entity.Project;
import quangnnfx16178.ilovevn.entity.StateType;
import quangnnfx16178.ilovevn.entity.User;
import quangnnfx16178.ilovevn.exception.DonationNotFoundException;
import quangnnfx16178.ilovevn.repository.DonationRepository;
import quangnnfx16178.ilovevn.repository.ProjectRepository;

import javax.swing.plaf.nimbus.State;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;
    private final ProjectRepository projectRepository;

    private final EmailServiceImpl emailService;

    public List<Donation> listAll() {
        return donationRepository.findAll();
    }

    public Donation saveNewDonation(Donation donation) {
        Donation persistedDonation = donationRepository.save(donation);
        Long increasedAmount = 1L * persistedDonation.getAmount();
        return persistedDonation;
    }

    public Integer countAllByState(StateType state) {
        return donationRepository.countAllByState(state);
    }

    public Long totalRaisedAmount(StateType state) {
        return donationRepository.sumAllByState(state);
    }

    public Integer countAll() {
        return donationRepository.countAll();
    }

    public Page<Donation> listByPage(Integer pageNum, Integer pageSize, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        if (keyword != null && !keyword.equals("")) {
            return donationRepository.findAll(keyword, pageable);
        }

        return donationRepository.findAll(pageable);
    }

    @Transactional
    public void changeState(Integer id, StateType state) throws DonationNotFoundException {
        Integer count = donationRepository.countById(id);
        if (count == null || count == 0) {
            throw new DonationNotFoundException("Không tồn tại giao dịch quyên góp với mã số: " + id);
        }
        Donation donation = donationRepository.findById(id).get();
        if (donation.getState() == StateType.ACCEPTED) {
            throw new IllegalArgumentException("Không thể thay đổi giao dịch có trạng thái " + donation.getState());
        }
        donation.setState(state);

        if (state == StateType.ACCEPTED) {
            Project project = donation.getProject();
            project.addDonation(donation);
            projectRepository.save(project);
        }

        donationRepository.save(donation);

        if (state == StateType.ACCEPTED) {
            if (donation.getUser() != null)
                emailService.sendDonateAcceptedStateEmail(donation);
        } else if (state == StateType.REJECTED) {
            if (donation.getUser() != null)
                emailService.sendDonateRejectedStateEmail(donation);
        }
    }
}
