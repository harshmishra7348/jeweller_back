package com.example.tea.Services;

import com.example.tea.Configuration.JwtTokenUtil;
import com.example.tea.Configuration.PasswordHash;
import com.example.tea.Model.UserMST;
import com.example.tea.Repository.UserMSTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserMSTService {
    @Autowired
    private UserMSTRepository userMSTRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public UserMST getById(Long userMSTId){
        return userMSTRepository.findById(userMSTId).orElseThrow(()->new RuntimeException("User not found"));
    }
    public UserMST getByEmail(String email){
        return userMSTRepository.findByEmailAndIsActiveTrue(email).orElseThrow(()->new RuntimeException("User not found."));
    }
    public List<UserMST> getAll(Boolean active){
        if(active){
            return userMSTRepository.findByIsActiveTrue();
        }else{
            return userMSTRepository.findAll();
        }
    }
    /** Public registration (website). Can never create an admin - the flag is forced false. */
    public UserMST create(UserMST userMST){
        userMST.setAdmin(false);
        return register(userMST);
    }

    /** Admin-created user. Honours the admin flag, so an admin can create another admin. */
    public UserMST createByAdmin(UserMST userMST){
        if(userMST.getAdmin()==null){
            userMST.setAdmin(false);
        }
        return register(userMST);
    }

    private UserMST register(UserMST userMST){
        validateEmail(userMST.getEmail());
        if(userMST.getPhoneNumber()==null || userMST.getPhoneNumber().trim().isEmpty()){
            throw new RuntimeException("Phone is required.");
        }
        // Reject a second account on the same email.
        if(userMSTRepository.existsByEmailAndIsActiveTrue(userMST.getEmail())){
            throw new RuntimeException("A user already exists with this email.");
        }
        validatePassword(userMST.getPassword());
        userMST.setPassword(new PasswordHash().encode(userMST.getPassword()));
        return userMSTRepository.save(userMST);
    }

    public UserMST update(UserMST userMST){
        UserMST byId = getById(userMST.getId());
        validateEmail(userMST.getEmail());
        if(userMST.getPhoneNumber()==null || userMST.getPhoneNumber().trim().isEmpty()){
            throw new RuntimeException("Phone is required.");
        }
        // The email must stay unique - it may not belong to a different active user.
        userMSTRepository.findByEmailAndIsActiveTrue(userMST.getEmail())
                .filter(existing -> !existing.getId().equals(userMST.getId()))
                .ifPresent(existing -> {
                    throw new RuntimeException("Another user already exists with this email.");
                });
        // Keep the existing admin flag if the caller did not send one.
        if(userMST.getAdmin()==null){
            userMST.setAdmin(byId.getAdmin());
        }
        // Password is optional on update: if omitted, keep the existing hash.
        // If a new raw password is supplied, validate its strength and hash it - never store it raw.
        if(userMST.getPassword()==null || userMST.getPassword().isEmpty()){
            userMST.setPassword(byId.getPassword());
        }else{
            validatePassword(userMST.getPassword());
            userMST.setPassword(new PasswordHash().encode(userMST.getPassword()));
        }
        return userMSTRepository.save(userMST);
    }

    /** Basic email presence + format check. */
    private void validateEmail(String email){
        if(email==null || email.trim().isEmpty()){
            throw new RuntimeException("Email is required.");
        }
        if(!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
            throw new RuntimeException("Please enter a valid email address.");
        }
    }

    /**
     * Enforces a strong password: at least 8 characters with an uppercase letter,
     * a lowercase letter, a digit, a special character and no spaces.
     */
    private void validatePassword(String password){
        if(password==null || password.length()<8){
            throw new RuntimeException("Password must be at least 8 characters long.");
        }
        if(!password.matches(".*[A-Z].*")){
            throw new RuntimeException("Password must contain at least one uppercase letter.");
        }
        if(!password.matches(".*[a-z].*")){
            throw new RuntimeException("Password must contain at least one lowercase letter.");
        }
        if(!password.matches(".*\\d.*")){
            throw new RuntimeException("Password must contain at least one digit.");
        }
        if(!password.matches(".*[^A-Za-z0-9].*")){
            throw new RuntimeException("Password must contain at least one special character.");
        }
        if(password.contains(" ")){
            throw new RuntimeException("Password must not contain spaces.");
        }
    }

    // ----- Customer self-service profile -----

    /** The currently logged-in user, resolved from the JWT. */
    public UserMST getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth==null || auth.getName()==null || "anonymousUser".equals(auth.getName())){
            throw new RuntimeException("Please login to continue.");
        }
        return getByEmail(auth.getName());
    }

    /**
     * Update the logged-in user's own details. Only name, address and phone can change here -
     * email and the admin/merchant role are intentionally immutable for self-service.
     */
    public UserMST updateOwnProfile(UserMST incoming){
        UserMST current = getCurrentUser();
        if(incoming.getPhoneNumber()==null || incoming.getPhoneNumber().trim().isEmpty()){
            throw new RuntimeException("Phone is required.");
        }
        current.setName(incoming.getName());
        current.setAddress(incoming.getAddress());
        current.setPhoneNumber(incoming.getPhoneNumber());
        // email, admin, merchant and password are deliberately NOT touched here.
        return userMSTRepository.save(current);
    }

    /** Change the logged-in user's password after verifying the current one. */
    public UserMST changePassword(String oldPassword, String newPassword){
        UserMST current = getCurrentUser();
        if(oldPassword==null || !new PasswordHash().matches(oldPassword, current.getPassword())){
            throw new RuntimeException("Your current password is incorrect.");
        }
        validatePassword(newPassword);
        current.setPassword(new PasswordHash().encode(newPassword));
        return userMSTRepository.save(current);
    }

    public Boolean deleteById(Long userMSTId){
        UserMST userMST = getById(userMSTId);
        userMST.setActive(false);
        // Save directly - going through update() would re-hash the already-hashed password.
        userMSTRepository.save(userMST);
        return true;
    }

    public String login(String userName,String password){
        Optional<UserMST> userMST = userMSTRepository.findByEmailAndIsActiveTrue(userName);
        if(userMST.isPresent()){
            if(new PasswordHash().matches(password,userMST.get().getPassword())){
                return jwtTokenUtil.generateToken(userName);
            }else{
                throw new RuntimeException("Your Password is invalid.");
            }
        }
        else{
            throw new RuntimeException("User not found.");
        }

    }
}
