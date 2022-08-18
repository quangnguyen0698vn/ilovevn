package quangnnfx16178.ilovevn.security;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import quangnnfx16178.ilovevn.entity.User;
import quangnnfx16178.ilovevn.repository.UserRepository;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired  private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("Không có tài khoản với email: " + email);

        return new MyUserDetails(user);
    }
}
