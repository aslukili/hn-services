package org.loukili.hnuser.service;

import lombok.RequiredArgsConstructor;
import org.loukili.hnuser.dto.HnUserRequest;
import org.loukili.hnuser.dto.HnUserResponse;
import org.loukili.hnuser.entity.HnUser;
import org.loukili.hnuser.event.NewFollowerEvent;
import org.loukili.hnuser.exception.UserNotFoundException;
import org.loukili.hnuser.repository.HnUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HnUserServiceImpl implements HnUserService{
    private final HnUserRepository hnUserRepository;
    private final KafkaTemplate<String, NewFollowerEvent> kafkaTemplate;

    @Override
    public List<HnUserResponse> getAll() {
        List<HnUser> users = hnUserRepository.findAll();
        return users.stream()
                .map(HnUser::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<HnUserResponse> getAllByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HnUser> submissions = hnUserRepository.findAll(pageable);
        return submissions.stream()
                .map(HnUser::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public HnUserResponse saveUser(HnUserRequest userRequest) {
        return hnUserRepository.save(userRequest.toUser()).toResponse();
    }

    @Override
    public Optional<HnUser> findById(Long userId) {
        return hnUserRepository.findById(userId);
    }

    @Override
    public Optional<HnUser> findByUsername(String userUsername) {
        return hnUserRepository.findByUsername(userUsername);
    }

    @Override
    public HnUserResponse editUser(Long userId, HnUserRequest userRequest) {
        HnUser user = hnUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
        if (userRequest.getUsername() != null) {
            user.setUsername(userRequest.getUsername());
        }
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.getFullName() != null) {
            user.setFullName(userRequest.getFullName());
        }
        if (userRequest.getAbout() != null) {
            user.setAbout(userRequest.getAbout());
        }
        return hnUserRepository.save(user).toResponse();
    }
    @Override
    public boolean deleteUser(Long userId) {
        HnUser user = hnUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        user.setNotBaned(false);
        return true;
    }

    @Override
    public boolean followUser(Long followerId, Long followeeId) {
        Optional<HnUser> userOptional = hnUserRepository.findById(followerId);
        Optional<HnUser> followeeOptional = hnUserRepository.findById(followeeId);

        if (userOptional.isPresent() && followeeOptional.isPresent()) {
            HnUser user = userOptional.get();
            HnUser followee = followeeOptional.get();

            user.getFollowing().add(followee);
            followee.getFollowers().add(user);

            hnUserRepository.save(user);
            hnUserRepository.save(followee);
            // TODO: send notification to followee
            // produce a kafka topic to notify the followee that he has a new follower
            kafkaTemplate.send("newFollowerTopic", new NewFollowerEvent(followee.getUsername(), user.getUsername() + " followed you"));
            return true;
        }

        return false;
    }

    @Override
    public boolean unfollowUser(Long followerId, Long followeeId) {
        Optional<HnUser> userOptional = hnUserRepository.findById(followerId);
        Optional<HnUser> followeeOptional = hnUserRepository.findById(followeeId);

        if (userOptional.isPresent() && followeeOptional.isPresent()) {
            HnUser user = userOptional.get();
            HnUser followee = followeeOptional.get();

            user.getFollowing().remove(followee);
            followee.getFollowers().remove(user);

            hnUserRepository.save(user);
            hnUserRepository.save(followee);

            return true;
        }

        return false;
    }

    @Override
    public boolean incrementKarma(String username) {
        // get the user by username
        Optional<HnUser> userOptional = hnUserRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            HnUser user = userOptional.get();
            user.setKarma(user.getKarma()+1);
            hnUserRepository.save(user);
            return true;
        }
        return false;
    }
}
