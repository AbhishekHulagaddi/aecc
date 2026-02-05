package com.tierra.result.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tierra.result.domain.Result;

public interface ResultRepo extends JpaRepository<Result, UUID>,JpaSpecificationExecutor<Result>{

	@Query(value = "SELECT * FROM result where answered_by=?1 and question=?2",nativeQuery = true)
	public Result isAlreadyAnswered(UUID answeredBy, UUID questionId);

	@Query(value = "SELECT r.* FROM result r "
            + "LEFT JOIN question q ON q.question_id = r.question "
            + "WHERE q.chapter = UUID_TO_BIN(?1) "
            + "AND q.section = ?2 "
            + "AND r.answered_by = UUID_TO_BIN(?3)",
      nativeQuery = true)
	public List<Result> findBychapterIdAndSectionAndAnsweredBy(String chapterId, int section, String answeredBy);
	
	@Query("SELECT DISTINCT q.section, q.subject.id,q.chapter.id FROM Result r "
	        + "LEFT JOIN r.question q WHERE r.answeredBy = :answeredBy "
	        + "AND q.subject IS NOT NULL AND q.section IS NOT NULL")
	public List<Object[]> findsectionAndSubjectAndAnsweredBy(@Param("answeredBy") UUID answeredBy);
	
	@Query(value="select distinct BIN_TO_UUID(answered_by) from tierra_aecc.result",nativeQuery = true)
	public List<UUID> findUsersAttendedTests();

//	@Query(value = "SELECT DISTINCT q.section FROM Result r "
//	        + "LEFT JOIN r.question q WHERE r.answeredBy = :answeredBy "
//	        + "AND q.chapter IS NOT NULL AND q.section IS NOT NULL AND"
//	        + " q.subject = UUID_TO_BIN(:subjectId)",nativeQuery = true)
//	public List<Integer> findSectionBySubjectSubjectIdAndAnsweredBy(@Param("subjectId")UUID subjectId,@Param("answeredBy")  UUID userId);
	
	@Query(value = "SELECT DISTINCT q.section " +
            "FROM result r " +
            "LEFT JOIN question q ON r.question = q.question_id " +
            "WHERE r.answered_by = :answeredBy " +
            "AND q.chapter IS NOT NULL " +
            "AND q.section IS NOT NULL " +
            "AND q.subject = :subjectId", 
    nativeQuery = true)
List<Integer> findSectionBySubjectSubjectIdAndAnsweredBy(
    @Param("subjectId") UUID subjectId,
    @Param("answeredBy") UUID userId);



	}

