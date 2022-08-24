package quangnnfx16178.ilovevn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.entity.Donation;
import quangnnfx16178.ilovevn.entity.StateType;
import quangnnfx16178.ilovevn.repository.DonationRepository;
import quangnnfx16178.ilovevn.repository.ProjectRepository;

import javax.swing.plaf.nimbus.State;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;
    private final ProjectRepository projectRepository;

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
}
