package edu.sjsu.cmpe272.simpleblog.server;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByOrderByMessageIdDesc();
    List<Message> findAllByOrderByMessageIdAsc();
    List<Message> findByMessageIdBetween(int startId, int endId);
}



