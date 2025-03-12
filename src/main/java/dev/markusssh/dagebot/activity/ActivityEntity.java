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

    @Column(name = "group_chat", nullable = false)
    private String groupChat;

    @Column(name = "activity_owner", nullable = false)
    private String owner;

    @Column(name = "activity_members", nullable = false)
    @ElementCollection
    private ArrayList<String> members;

    @Column(name = "activity_max_members", nullable = false)
    private int maxMembers;

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

    public String getGroupChat() {
        return groupChat;
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public int getMaxMembers() {
        return maxMembers;
    }
}
