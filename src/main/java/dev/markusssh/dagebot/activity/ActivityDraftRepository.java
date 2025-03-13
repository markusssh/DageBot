package dev.markusssh.dagebot.activity;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ActivityDraftRepository extends CrudRepository<ActivityDraft, Long> {

    Optional<ActivityDraft> findByUserId(Long userId);
}
