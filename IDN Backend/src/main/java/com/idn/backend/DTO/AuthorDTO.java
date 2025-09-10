package com.idn.backend.DTO;

import java.util.List;

import com.idn.backend.Model.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private Long id;
    private String name;
    private String email;
    private List<Post> posts;
}