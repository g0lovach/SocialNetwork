package com.example.exampleauth.repositories;

import com.example.exampleauth.enities.Friendship;
import com.example.exampleauth.enities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {


    @Query(value = "select f.* from Friendship f  " +
            "left join Users u on u.id = f.user_id1 or u.id = f.user_id2  " +
            "where u.id = :user_id", nativeQuery = true)
    List<Friendship> findUserFriendships(@Param("user_id") Long user_id);

    @Modifying
    @Query(value = "delete from Friendship f  "+
            "where f.user_id1 in (:user_id1,:user_id2) and f.user_id2 in (:user_id1,:user_id2)" , nativeQuery = true)
    void deleteFriendship(@Param("user_id1") Long userId, @Param("user_id2") Long friendId);

    @Modifying
    @Query(value = "insert into Friendship "+
            "values(:user_id1, :user_id2)" , nativeQuery = true)
    void addFriendship(@Param("user_id1") Long userId, @Param("user_id2") Long friendId);

}
