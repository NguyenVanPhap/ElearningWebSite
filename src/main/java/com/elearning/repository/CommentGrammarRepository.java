package com.elearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elearning.entities.CommentGrammar;
import com.elearning.entities.Grammar;

@Repository
public interface CommentGrammarRepository extends JpaRepository<CommentGrammar, Integer> {

	List<CommentGrammar> findByGrammar(Grammar grammar);

//	CommentGrammar addComment(CommentGrammar c);
}
