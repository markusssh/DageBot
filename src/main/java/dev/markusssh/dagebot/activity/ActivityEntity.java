package dev.markusssh.dagebot.activity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name="ACTIVITIES")
public class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "activity_name", length = 50, nullable = false)
    private String name;
    @Column(name = "activity_start", nullable = false)
    private LocalDateTime start;
    @Column(name = "activity_end", nullable = false)
    private LocalDateTime end;
    @Column(name = "activity_max_members", nullable = false)
    private int maxMembers;
    @Column(name = "activity_member", nullable = false)
    @ElementCollection
    private ArrayList<String> members = new ArrayList<>();

    @Column(name = "owner_id")
    private long ownerId;
    @Column(name = "group_id")
    private long groupId;
    @Column(name = "view_message_id")
    private long viewMessageId;

    public ActivityEntity() {
    }

    public ActivityEntity(String name,
                          LocalDateTime start,
                          LocalDateTime end,
                          int maxMembers,
                          long ownerId,
                          long groupId,
                          long viewMessageId,
                          String ownerUsername) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.maxMembers = maxMembers;
        this.ownerId = ownerId;
        this.groupId = groupId;
        this.viewMessageId = viewMessageId;
        this.members.add("@" + ownerUsername);
    }

    public long getId() {
        return id;
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

    public ArrayList<String> getMembers() {
        return members;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public long getGroupId() {
        return groupId;
    }

    public long getViewMessageId() {
        return viewMessageId;
    }
}
