package org.loukili.hnuser.service;

import lombok.RequiredArgsConstructor;
import org.loukili.hnuser.dto.HnUserRequest;
import org.loukili.hnuser.dto.HnUserResponse;
import org.loukili.hnuser.entity.HnUser;
import org.loukili.hnuser.exception.UserNotFoundException;
import org.loukili.hnuser.repository.HnUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HnUserServiceImpl implements HnUserService{
    private final HnUserRepository hnUserRepository;
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
}
