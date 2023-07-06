package com.finalproject.cf.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "ROLE")
public class Role {

    @Id
    private String roleName;
    private String roleDescription;

}
