package com.irdaislakhuafa.garbagepickupapi.models.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "\"user\"")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, length = 5000)
    private String address;

    @Column(nullable = false)
    @Builder.Default
    private int saldo = 0;

    @Column(nullable = false)
    @Builder.Default
    private int point = 0;

    @JoinColumn(nullable = false)
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @JoinColumn(nullable = false)
    @OneToOne
    private LastLocation lastLocation;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(v -> new SimpleGrantedAuthority(v.getName().toUpperCase()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return !this.isDeleted();
    }
}