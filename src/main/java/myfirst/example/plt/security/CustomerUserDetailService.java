package myfirst.example.plt.security;

import myfirst.example.plt.entity.AppUser;
import myfirst.example.plt.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomerUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @param username
//    @return
//    @throws UsernameNotFoundException

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User topilmadi: " + username));
        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(Collections.singleton(
                        new SimpleGrantedAuthority(appUser.getRole().name())
                ))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!appUser.getEnabled())
                .build();
    }
}
