package com.pavel_fomchenkov.tasktracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = {"tasks", "authorities", "accountNonExpired", "accountNonLocked",
        "credentialsNonExpired", "credentialsNonExpired", "enabled"})
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @JsonProperty("tasks")
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "performers")
    private Collection<Task> tasks = new HashSet<>();

    @Override
    @JsonProperty("authorities")
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonProperty("accountNonExpired")
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonProperty("accountNonLocked")
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonProperty("credentialsNonExpired")
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonProperty("enabled")
    public boolean isEnabled() {
        return true;
    }
}
