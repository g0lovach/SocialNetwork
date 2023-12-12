package com.example.exampleauth.services;

import com.example.exampleauth.config.exceptions.UserNotExist;
import com.example.exampleauth.dtos.RegistrationUserDto;
import com.example.exampleauth.enities.CustomUserDetails;
import com.example.exampleauth.enities.User;
import com.example.exampleauth.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }





    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", username)
        ));
        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()),
                user.getId()
        );
    }

    @Transactional
    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }

    @Transactional
    public void addRoleToUser(Long userId, Long roleId) {
        if (userRepository.existsById(userId)){
            userRepository.findById(userId).get().addRole(roleService.getRoleById(roleId));
        }

    }

    @Transactional
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return;
        }
        throw new UserNotExist("User not exist");

    }

   public User getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            return user.get();
        }
        throw new UserNotExist("User not exist");


   }

    public User getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return user.get();
        }
        throw new UserNotExist("User not exist");


    }


}