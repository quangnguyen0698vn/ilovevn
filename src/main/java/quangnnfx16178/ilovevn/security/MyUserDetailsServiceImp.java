package quangnnfx16178.ilovevn.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import quangnnfx16178.ilovevn.entity.User;
import quangnnfx16178.ilovevn.repository.UserRepository;

@Component
public class MyUserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private  UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Integer count = repo.countUserByEmail(email);
        if (count == 0)
            throw new UsernameNotFoundException("Không có tài khoản với email: " + email);
        User user = repo.findByEmail(email);
        return new MyUserDetails(user);
    }
}
