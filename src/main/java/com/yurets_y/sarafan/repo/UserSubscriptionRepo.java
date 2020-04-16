package com.yurets_y.sarafan.repo;

import com.yurets_y.sarafan.domain.User;
import com.yurets_y.sarafan.domain.UserSubscription;
import com.yurets_y.sarafan.domain.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepo extends JpaRepository<UserSubscription,UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User subscriber);
}
