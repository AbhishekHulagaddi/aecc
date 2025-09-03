package com.tierra.question.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.CusException;
import com.tierra.auth.utils.CustomMessage;
import com.tierra.auth.utils.IDGeneration;
import com.tierra.auth.utils.UserIdPrinciple;
import com.tierra.masterdata.domain.Chapter;
import com.tierra.masterdata.domain.Subject;
import com.tierra.masterdata.repo.ChapterRepo;
import com.tierra.masterdata.repo.SubjectRepo;
import com.tierra.question.domain.Question;
import com.tierra.question.domain.WeeklyTestQuestions;
import com.tierra.question.mapper.QuestionMapper;
import com.tierra.question.model.QuestionModel;
import com.tierra.question.model.SectionModel;
import com.tierra.question.repo.QuestionRepo;
import com.tierra.question.repo.WeeklyTestQuestionRepo;
import com.tierra.question.service.QuestionService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	QuestionMapper questionMapper;
	
	@Autowired
	ChapterRepo chapterRepo;
	
	@Autowired
	QuestionRepo questionRepo;
	
	@Autowired
	SubjectRepo subjectRepo;
	
	@Autowired
	UserIdPrinciple principle;
	
	@Autowired
	IDGeneration idGeneration;
	
	@Autowired
	WeeklyTestQuestionRepo weeklyTestQuestionRepo;
	

	@Override
	public BaseResponse createQuestion(QuestionModel questionModel) {
		Question question = questionMapper.convertModelToDomain(questionModel);
		question.setStatus(true);
		question.setCreatedDate(LocalDateTime.now());
		question.setCreatedBy(principle.getUserId());
		try {
		question = questionRepo.save(question);
		}catch(Exception e) {
			return new BaseResponse(question.getQuestionName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(question.getQuestionName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateQuestion(QuestionModel questionModel) {
		Question question = null;
		if(questionModel.getQuestionId()!=null)
			question = questionRepo.findById(questionModel.getQuestionId())
				.orElseThrow(()->new CusException(" Question Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for Question ", HttpStatus.NOT_FOUND);
		question.setQuestionName(questionModel.getQuestionName());
		Subject subject = subjectRepo.findById(questionModel.getSubjectId())
				.orElseThrow(() -> new CusException(" Subject Not Found For Question ", HttpStatus.NOT_FOUND));
		Chapter chapter = chapterRepo.findById(questionModel.getChapterId())
				.orElseThrow(() -> new CusException(" Chapter Not Found For Question ", HttpStatus.NOT_FOUND));
		question.setSubject(subject);
		question.setChapter(chapter);
		question.setModifiedDate(LocalDateTime.now());
		question.setModifiedBy(principle.getUserId());
		question = questionRepo.save(question);
		return new BaseResponse(question.getQuestionName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	@Override
	public BaseResponse deleteQuestion(QuestionModel questionModel) {
		Question question = null;
		if(questionModel.getQuestionId()!=null)
			question = questionRepo.findById(questionModel.getQuestionId())
				.orElseThrow(()->new CusException(" Question Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for Question ", HttpStatus.NOT_FOUND);
		question.setStatus(false);
		question.setModifiedDate(LocalDateTime.now());
		question.setModifiedBy(principle.getUserId());
		questionRepo.save(question);
		return new BaseResponse(question.getQuestionName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	@Override
	public QuestionModel findById(QuestionModel questionModel) {
		Question question = null;
		if(questionModel.getQuestionId()!=null)
			question = questionRepo.findById(questionModel.getQuestionId())
				.orElseThrow(()->new CusException(" Question Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for Question ", HttpStatus.NOT_FOUND);
		questionModel = questionMapper.convertDomainToModel(question);
		return questionModel;
	}

	@Override
	public Page<QuestionModel> getAllQuestion (String questionName, UUID subjectId,UUID chapterId,int section,  Pageable pageable)throws Exception{
		Page<Question> question = questionRepo.findAll(new Specification<Question>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (questionName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("questionName"), "%" + questionName + "%")));

				}
	            if (chapterId != null) {
	                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("chapter").get("id"), chapterId)));
	            }
	            if (subjectId != null) {
	                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("subject").get("id"), subjectId)));
	            }
	            if (section != 0) {
	            	predicates.add(criteriaBuilder.equal(root.get("section"), section));
	            }

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    return questionMapper.ConverDomainToModel(question);
	}

	@Override
	public SectionModel getDistinctAvailavleSections(QuestionModel questionModel) {
		List<Question> questionList =new ArrayList<Question>();
		if(questionModel.getSubjectId()!=null) {
			questionList = questionRepo.findBySubjectSubjectId(questionModel.getSubjectId());
		}else if (questionModel.getChapterId()!=null) {
			questionList = questionRepo.findByChapterChapterId(questionModel.getChapterId());			
		}
		
		if(!questionList.isEmpty()) {
//			List<Integer> distinctSections = questionList.stream()
//			        .map(Question::getSection)
//			        .filter(Objects::nonNull) // skip nulls
//			        .distinct()
//			        .sorted()
//			        .collect(Collectors.toList());
			
			List<Integer> distinctSections = questionList.stream()
			        .filter(q -> q.getSection() != 0)
			        .collect(Collectors.groupingBy(
			            Question::getSection,
			            Collectors.counting()
			        ))
			        .entrySet().stream()
			        .filter(entry -> entry.getValue() == 30)   // keep only sections with 30 questions
			        .map(Map.Entry::getKey)
			        .collect(Collectors.toList());
			
			
			SectionModel sectionModel = new SectionModel();
			sectionModel.setChapterId(questionList.get(0).getChapter().getChapterId());
			sectionModel.setSubjectId(questionList.get(0).getSubject().getSubjectId());
			sectionModel.setSubjectName(questionList.get(0).getSubject().getSubjectName());
			sectionModel.setSections(distinctSections);
			return sectionModel;
		}else {
			return null;
		}


		
	}

	@Override
	public List<QuestionModel> findWeeklyTestQuestions() {
		List<QuestionModel> weeklyTestQuestionsList = new ArrayList<>();
		LocalDateTime today = LocalDateTime.now();
		List<WeeklyTestQuestions> weeklyTestQuestions = weeklyTestQuestionRepo.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(today,today);
		weeklyTestQuestions.forEach(wtq->{
			weeklyTestQuestionsList.add(questionMapper.convertDomainToModel(wtq.getQuestion()));
		});
		return weeklyTestQuestionsList;
	}

	@Override
	public String addWeeklyTest(List<QuestionModel> questionModelList,LocalDateTime startDate,LocalDateTime endDate) {
		for(QuestionModel questionModel:questionModelList) {
			Question question = questionRepo.findById(questionModel.getQuestionId()).orElse(null);
			if(question==null) {
				break;
			}
			WeeklyTestQuestions weeklyTestQuestions = new WeeklyTestQuestions();
			weeklyTestQuestions.setQuestion(question);
			weeklyTestQuestions.setCreatedDate(LocalDateTime.now());
			weeklyTestQuestions.setStatus(true);
			weeklyTestQuestions.setStartDate(startDate);
			weeklyTestQuestions.setEndDate(endDate);
			weeklyTestQuestionRepo.save(weeklyTestQuestions);
		}
		return "Weekly Test Created Successfully!";
	}
	

}
