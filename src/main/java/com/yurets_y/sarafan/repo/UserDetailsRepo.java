package com.yurets_y.sarafan.repo;

import com.yurets_y.sarafan.domain.User;
import com.yurets_y.sarafan.domain.UserSubscription;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDetailsRepo extends JpaRepository<User, String> {

    @Override
    @EntityGraph(attributePaths = {"subscriptions", "subscribers"})
    Optional<User> findById(String s);

}
