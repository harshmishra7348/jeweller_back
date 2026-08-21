package com.example.tea.Services;
import com.example.tea.Model.UserMST;
import com.example.tea.Repository.UserMSTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
@Service
public class UserMSTConfigureService implements UserDetailsService{

        @Autowired
        private UserMSTRepository userRepository;  // Assuming you have a JPA repository for UserMST

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Fetch the user from your repository or database
            UserMST user = userRepository.findByEmailAndIsActiveTrue(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Admin users (admin = true) get ADMIN; end customers get USER.
            String authority = (user.getAdmin() != null && user.getAdmin()) ? "ADMIN" : "USER";
            return User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())  // Ensure the password is properly encrypted
                    .authorities(authority)
                    .build();
        }
}

