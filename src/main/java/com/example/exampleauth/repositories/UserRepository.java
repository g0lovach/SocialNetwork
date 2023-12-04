package com.example.exampleauth.repositories;

import com.example.exampleauth.enities.Role;
import com.example.exampleauth.enities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Modifying
    @Query(value = "INSERT INTO  users_roles values(:user_id, :role_id)", nativeQuery = true)
    void addUserRole(@Param("user_id") Long UserId, @Param("role_id") Long roleId);
}