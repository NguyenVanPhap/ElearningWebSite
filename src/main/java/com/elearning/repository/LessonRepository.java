
package com.elearning.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.elearning.entities.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
	List<Lesson> findByCourseid(Integer courseId);

	List<Lesson> findByLessonid(Integer lessonId);

}
