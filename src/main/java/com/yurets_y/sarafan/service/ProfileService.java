package com.yurets_y.sarafan.service;

import com.yurets_y.sarafan.domain.User;
import com.yurets_y.sarafan.domain.UserSubscription;
import com.yurets_y.sarafan.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final UserDetailsRepo userDetailsRepo;

    @Autowired
    public ProfileService(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }

    public User changeSubscription(User subscriber, User channel) {
        List<UserSubscription> userSubscriptions = channel.getSubscribers()
                .stream()
                .filter(subscription -> subscription.getSubscriber().equals(subscriber))
                .collect(Collectors.toList());

        if(userSubscriptions.isEmpty()){
            UserSubscription subscription = new UserSubscription(channel,subscriber);
            channel.getSubscribers().add(subscription);
        }else {
            channel.getSubscribers().removeAll(userSubscriptions);
        }
        return userDetailsRepo.save(channel);
    }
}
