package com.example.exampleauth.services;

import com.example.exampleauth.config.exceptions.FriendshipNotCreated;
import com.example.exampleauth.config.exceptions.FriendshipNotExist;
import com.example.exampleauth.enities.Friendship;
import com.example.exampleauth.repositories.FriendshipRepository;
import com.example.exampleauth.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Stream;



@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    public Map<String, Long> getUserFriends(Long userId){
        List<Friendship> friendships = friendshipRepository.findUserFriendships(userId);
        Map<String, Long> friends = new HashMap<>();
         friendships.stream().flatMap(i-> Stream.of(i.getUserOne(), i.getUserTwo()))
                .filter(i-> !Objects.equals(i.getId(), userId)).forEach(i->friends.put(i.getUsername(),i.getId()));
        return friends;
    }

    @Transactional
    public void deleteFriend(Long exFriendId){
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userRepository.existsById(userId)
                && userRepository.existsById(exFriendId) && getUserFriends(userId).containsValue(exFriendId)) {
            friendshipRepository.deleteFriendship(userId, exFriendId);
            return;
        }
        throw new FriendshipNotExist("Friendship between "+ Long.toString(userId)
                +" and "+ Long.toString(exFriendId)+"not exist");

    }

    @Transactional
    public void addFriend(Long newFriendId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();

            if (userRepository.existsById(userId)
                    && userRepository.existsById(newFriendId)
                    && !getUserFriends(userId).containsValue(newFriendId)) {

                friendshipRepository.save(new Friendship(userRepository.findById(userId).get(),
                        userRepository.findById(newFriendId).get()));
                return;
            }
            throw new FriendshipNotCreated("Incorrect identifiers!");

    }

}
