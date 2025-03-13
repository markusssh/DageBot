package dev.markusssh.dagebot.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityDraftRepository activityDraftRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository,
                           ActivityDraftRepository activityDraftRepository) {
        this.activityRepository = activityRepository;
        this.activityDraftRepository = activityDraftRepository;
    }

    public void createActivityDraft(
            long userId,
            String userName,
            long groupId,
            int viewMessageId,
            int editMessageId
    ) {
        ActivityDraft draft = new ActivityDraft(userId, userName, groupId, viewMessageId, editMessageId);
        activityDraftRepository.save(draft);
    }

    public ActivityDraft getActivityDraft(Long userId) {
        Optional<ActivityDraft> activityDraftOptional = activityDraftRepository.findByUserId(userId);
        return activityDraftOptional.orElse(null);
    }

    public void updateActivityDraft(ActivityDraft activityDraft) {
        activityDraftRepository.save(activityDraft);
    }
}
