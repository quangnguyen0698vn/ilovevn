package quangnnfx16178.ilovevn.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.donation.Donation;
import quangnnfx16178.ilovevn.donation.DonationRepository;

import java.util.List;

@Service
public class DonationService {
    @Autowired
    private DonationRepository repo;

    public List<Donation> listAll() {
        return repo.findAll();
    }

    public List<Long> listAllProjectRaisedAmount() {
        return repo.findAllProjectRaisedAmount();
    }

    public List<Integer> listAllProjectIdSortedByRaisedAmountAsc() { return repo.findProjectIdSortByRaisedAmountAsc(); }
}
