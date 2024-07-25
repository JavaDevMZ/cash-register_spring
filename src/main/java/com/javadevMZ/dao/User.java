package com.javadevMZ.dao;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@ToString(exclude = "password")
public class User extends AbstractEntity<Long>{


    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name="role", referencedColumnName = "name", nullable = false)
    private RoleEntity role;


    public User(Long id, String email, RoleEntity role, String password) {
        setId(id);
        this.email = email;
        this.role = role;
        this.password = password;
    }

}
