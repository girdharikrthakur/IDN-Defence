package com.idn.backend.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Contact {

    private Long id;
    private String email;
    private String message;

}