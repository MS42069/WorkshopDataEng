package com.fhwedel.softwareprojekt.v1.model;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class User implements UserDetails {
    @Id @GeneratedValue private UUID id;

    @NotBlank(message = "no name was given")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "password was given")
    @Column()
    private String password;

    @NotBlank(message = "no displayName was given")
    @Column()
    private String displayName;

    public User(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
