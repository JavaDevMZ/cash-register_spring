package com.javadevMZ.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="roles")
public class RoleEntity extends AbstractEntity<Byte>{

    @Column(name="name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Role role;

    public RoleEntity(Role role){
        this.role = role;
    }

    public RoleEntity(String roleName){
        new RoleEntity(Role.valueOf(roleName));
    }

    public RoleEntity(Byte id, Role role){
        setId(id);
        this.role = role;
    }

    @Override
    public String toString() {
        return role.toString();
    }
}
