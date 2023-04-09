package org.loukili.hnuser.service;

import org.loukili.hnuser.dto.HnUserRequest;
import org.loukili.hnuser.dto.HnUserResponse;
import org.loukili.hnuser.entity.HnUser;

import java.util.List;
import java.util.Optional;

public interface HnUserService {
    List<HnUserResponse> getAll();

    List<HnUserResponse> getAllByPage(int page, int size);

    HnUserResponse saveUser(HnUserRequest userRequest);

    Optional<HnUser> findById(Long userId);

    Optional<HnUser> findByUsername(String userUsername);

    HnUserResponse editUser(Long userId, HnUserRequest userRequest);

    boolean deleteUser(Long userId);
    boolean followUser(Long followerId, Long followeeId);
    boolean unfollowUser(Long followerId, Long followeeId);

    boolean incrementKarma(String username);
}
