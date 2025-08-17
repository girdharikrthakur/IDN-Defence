package com.idn.backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_AUTH")
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTH_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String passweord;

}
