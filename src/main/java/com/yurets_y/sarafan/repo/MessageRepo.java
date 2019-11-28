package com.yurets_y.sarafan.repo;

import com.yurets_y.sarafan.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long>{

}
