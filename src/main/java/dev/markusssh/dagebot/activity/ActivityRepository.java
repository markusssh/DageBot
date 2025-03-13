package dev.markusssh.dagebot.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {
}
