package com.wantedpreonboardingbackend.repository;

import com.wantedpreonboardingbackend.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select entity from Post entity join fetch entity.user")
    Page<Post> findAll(Pageable pageable);

    @Query("select entity from Post entity join fetch entity.user where entity.id = :id")
    Optional<Post> findById(Long id);
}
