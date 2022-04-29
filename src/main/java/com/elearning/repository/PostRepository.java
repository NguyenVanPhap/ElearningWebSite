package com.elearning.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elearning.entities.Grammar;
import com.elearning.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findById(int id);

	@Query("select post FROM Post  post WHERE  post.Name LIKE CONCAT('%',:search,'%')")
	List<Post> search(@Param("search") String search);

	@Query("select post FROM Post  post WHERE post.Name ='' or post.Name LIKE CONCAT('%',:search,'%')")
	Page<Post> search4page(@Param("search") String search, Pageable pageable);

}
