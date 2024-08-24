package com.pavel_fomchenkov.tasktracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = {"performers"})
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq")
    @SequenceGenerator(name = "task_id_seq", sequenceName = "task_id_seq", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "author_id", nullable = false)
    @ManyToOne
    private User author;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "finish_date")
    private Date finishDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;


    @JoinTable(name = "task_performers", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonProperty("performers")
    private Collection<User> performers = new HashSet<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private Collection<Comment> comments = new ArrayList<>();
}
