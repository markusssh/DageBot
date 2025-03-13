package dev.markusssh.dagebot.activity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@RedisHash(value = "ActivityDraft", timeToLive = 43200)
public class ActivityDraft {

    @Id
    private String id;

    @Indexed
    private Long userId;
    private String userName;
    private Long groupId;
    private Integer viewMessageId;
    private Integer editMessageId;

    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private Integer maxMembers;

    private LocalDateTime createdAt;

    public ActivityDraft() {
    }

    public ActivityDraft(Long userId, String userName, Long groupId, Integer viewMessageId, Integer editMessageId) {
        this.userId = userId;
        this.userName = userName;
        this.groupId = groupId;
        this.viewMessageId = viewMessageId;
        this.editMessageId = editMessageId;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getEditMessageId() {
        return editMessageId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Integer getViewMessageId() {
        return viewMessageId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public Integer getMaxMembers() {
        return maxMembers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public void setMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
    }
}
