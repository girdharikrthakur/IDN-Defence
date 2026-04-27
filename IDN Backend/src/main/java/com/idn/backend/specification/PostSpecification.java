package com.idn.backend.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.idn.backend.dto.request.PostSearchRequest;
import com.idn.backend.entity.Post;

import jakarta.persistence.criteria.Predicate;

public class PostSpecification {

    public static Specification<Post> search(PostSearchRequest req) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Keyword search in title and content
            if (req.getKeyword() != null && !req.getKeyword().isEmpty()) {

                String[] keywords = req.getKeyword().toLowerCase().split(" ");

                for (String word : keywords) {

                    String pattern = "%" + word + "%";

                    Predicate p = cb.or(
                            cb.like(cb.lower(root.get("title")), pattern),
                            cb.like(cb.lower(root.get("content")), pattern),
                            cb.like(cb.lower(root.get("category")), pattern));

                    predicates.add(p);
                }

            }
            if (req.getCategory() != null && !req.getCategory().isEmpty()) {
                predicates.add(
                        cb.equal(cb.lower(root.get("category")), req.getCategory().toLowerCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));

        };
    }

}
