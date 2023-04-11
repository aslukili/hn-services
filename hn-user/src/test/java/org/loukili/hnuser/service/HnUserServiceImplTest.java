package org.loukili.hnuser.service;

import org.junit.jupiter.api.Test;
import org.loukili.hnuser.entity.HnUser;
import org.loukili.hnuser.event.NewFollowerEvent;
import org.loukili.hnuser.repository.HnUserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


@SpringBootTest
class HnUserServiceImplTest {
    @Mock
    private HnUserRepository hnUserRepository;

    @Mock
    private KafkaTemplate<String, NewFollowerEvent> kafkaTemplate;

    @InjectMocks
    private HnUserServiceImpl followUserService;

    @Test
    public void followUser_Successful() {
        HnUser follower = new HnUser();
        follower.setId(1L);
        follower.setUsername("follower");

        HnUser followee = new HnUser();
        followee.setId(2L);
        followee.setUsername("followee");

        Mockito.when(hnUserRepository.findById(1L)).thenReturn(Optional.of(follower));
        Mockito.when(hnUserRepository.findById(2L)).thenReturn(Optional.of(followee));

        Mockito.when(hnUserRepository.save(any(HnUser.class))).thenReturn(null);
        Mockito.doNothing().when(kafkaTemplate).send(eq("newFollowerTopic"), any(NewFollowerEvent.class));

        boolean result = followUserService.followUser(1L, 2L);

        assertTrue(result);
        assertTrue(follower.getFollowing().contains(followee));
        assertTrue(followee.getFollowers().contains(follower));
        Mockito.verify(hnUserRepository, Mockito.times(2)).save(any(HnUser.class));
        Mockito.verify(kafkaTemplate, Mockito.times(1)).send(eq("newFollowerTopic"), any(NewFollowerEvent.class));
    }

    @Test
    public void followUser_FollowerNotFound() {
        Mockito.when(hnUserRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(hnUserRepository.findById(2L)).thenReturn(Optional.of(new HnUser()));

        boolean result = followUserService.followUser(1L, 2L);

        assertFalse(result);
        Mockito.verify(hnUserRepository, Mockito.times(0)).save(any(HnUser.class));
        Mockito.verify(kafkaTemplate, Mockito.times(0)).send(eq("newFollowerTopic"), any(NewFollowerEvent.class));
    }

    @Test
    public void followUser_FolloweeNotFound() {
        Mockito.when(hnUserRepository.findById(1L)).thenReturn(Optional.of(new HnUser()));
        Mockito.when(hnUserRepository.findById(2L)).thenReturn(Optional.empty());

        boolean result = followUserService.followUser(1L, 2L);

        assertFalse(result);
        Mockito.verify(hnUserRepository, Mockito.times(0)).save(any(HnUser.class));
        Mockito.verify(kafkaTemplate, Mockito.times(0)).send(eq("newFollowerTopic"), any(NewFollowerEvent.class));
    }
}
