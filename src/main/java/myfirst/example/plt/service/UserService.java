package myfirst.example.plt.service;


import myfirst.example.plt.entity.AppUser;
import myfirst.example.plt.entity.enummration.Role;
import myfirst.example.plt.repository.UserRepository;
import myfirst.example.plt.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }
//    @param username
//    @param rawPasswor
//    @throws RuntimeException

    public void register(String username, String rawPassword){
        if(userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("Bu username bizda allaqachon mavjud");

        }
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));

        user.setRole(Role.ROLE_USER);
        user.setEnabled(true);

        userRepository.save(user);
    }

    public String login(String username, String rawPassword){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, rawPassword)
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtUtil.generateToken(userDetails);
    }
}
