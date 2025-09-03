package com.tierra.question.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tierra.question.domain.Question;
import com.tierra.question.domain.WeeklyTestQuestions;

public interface QuestionRepo extends JpaRepository<Question, UUID>,JpaSpecificationExecutor<Question>{
	
	
	@Query(value = "SELECT BIN_TO_UUID(question_id) \r\n"
			+ "FROM Question \r\n"
			+ "WHERE chapter =?1", nativeQuery = true)
	List<UUID> findByChapter(UUID chapterId);

	List<Question>findBySubjectSubjectId(UUID subjectId);

//	@Query (value = "SELECT DISTINCT BIN_TO_UUID(chapter),section From Question")
//	Map<UUID,String> findDistinctChapterAndSection();

//	@Query("SELECT DISTINCT BIN_TO_UUID(q.chapter.id), q.section FROM Question q WHERE q.chapter IS NOT NULL")
//	Map<UUID, String> findDistinctChapterAndSection();
	
	@Query("SELECT DISTINCT q.chapter.id, q.section FROM Question q WHERE q.chapter IS NOT NULL AND q.section IS NOT NULL")
	List<Object[]> findDistinctChapterAndSection();

	List<Question> findByChapterChapterId(UUID chapterId);



	

}
