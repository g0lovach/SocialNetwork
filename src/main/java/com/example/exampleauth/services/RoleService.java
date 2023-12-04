package com.example.exampleauth.services;

import com.example.exampleauth.config.exceptions.RoleNotExist;
import com.example.exampleauth.enities.Role;
import com.example.exampleauth.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }

    public Role getRoleById(Long id) {
        if (roleRepository.existsById(id)) {
            return roleRepository.findById(id).get();
        }
        throw new RoleNotExist("Role not exist");
    }
}