package dev.markusssh.dagebot.activity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="ACTIVITIES")
public class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "activity_name", length = 50, nullable = false)
    private String name;

    @Column(name = "activity_start")
    private LocalDateTime start;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStart() {
        return start;
    }
}
