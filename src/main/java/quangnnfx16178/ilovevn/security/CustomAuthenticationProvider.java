package quangnnfx16178.ilovevn.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import quangnnfx16178.ilovevn.entity.UserDTO;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        MyUserDetails user = (MyUserDetails) userDetailsService.loadUserByUsername(username);
        if (user.isEnabled() && passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    user,
                    password,
                    user.getAuthorities());
        }
        else
        if (!user.isEnabled()) {
            log.error("Tài khoản với email: " + username + " đang bị vô hiệu hóa!");
            throw new BadCredentialsException("Tài khoản với email: " + username + " đang bị vô hiệu hóa!");
        }
        else
        {
            throw new BadCredentialsException("Email hoặc mật khẩu không đúng!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
