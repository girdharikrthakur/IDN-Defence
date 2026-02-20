package com.idn.backend.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tags")
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "tag")
    private List<PostTag> postTags = new ArrayList<>();
}
