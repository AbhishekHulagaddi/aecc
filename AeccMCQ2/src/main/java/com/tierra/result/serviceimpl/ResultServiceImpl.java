package com.tierra.result.serviceimpl;

import java.time.LocalDateTime;
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

import com.tierra.auth.domain.User;
import com.tierra.auth.model.UserModel;
import com.tierra.auth.service.UserService;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.CusException;
import com.tierra.auth.utils.CustomMessage;
import com.tierra.auth.utils.UserIdPrinciple;
import com.tierra.masterdata.domain.Chapter;
import com.tierra.masterdata.domain.Subject;
import com.tierra.masterdata.model.ChapterModel;
import com.tierra.masterdata.model.SubjectModel;
import com.tierra.masterdata.repo.ChapterRepo;
import com.tierra.masterdata.repo.SubjectRepo;
import com.tierra.masterdata.service.ChapterService;
import com.tierra.masterdata.service.SubjectService;
import com.tierra.question.domain.Question;
import com.tierra.question.model.SectionModel;
import com.tierra.question.model.ViewAllQuestionModel;
import com.tierra.question.repo.QuestionRepo;
import com.tierra.result.domain.Result;
import com.tierra.result.mapper.ResultMapper;
import com.tierra.result.model.DashBoardModel;
import com.tierra.result.model.ResultModel;
import com.tierra.result.model.UserResultDisplayModel;
import com.tierra.result.model.ViewAllResultModel;
import com.tierra.result.repo.ResultRepo;
import com.tierra.result.service.ResultService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class ResultServiceImpl implements ResultService{
	
	@Autowired
	private ResultMapper resultMapper;
	
	@Autowired
	private ResultRepo resultRepo;
	
	@Autowired
	private QuestionRepo questionRepo;
	
	@Autowired
	private UserIdPrinciple principle;
	
	@Autowired
	private ChapterService chapterService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SubjectRepo subjectRepo;
	
	@Autowired
	private ChapterRepo chapterRepo;
	

	@Override
	public BaseResponse createResult(ResultModel resultModel) {
		Result result = resultMapper.convertModelToDomain(resultModel);
		Result oldresult = resultRepo.isAlreadyAnswered(principle.getUserId(), resultModel.getQuestionId());
		if(oldresult!=null) {
			result.setResultId(oldresult.getResultId());
		}
		result.setStatus(true);
		result.setCreatedDate(LocalDateTime.now());
		result.setCreatedBy(principle.getUserId());
		result.setAnsweredAt(LocalDateTime.now());
		result.setAnsweredBy(principle.getUserId());
		if(result.getAnswered()==null) {
			result.setCorrect(false);
			result.setSkipped(true);
		}else {
			result.setCorrect(result.getAnswered().equals(result.getQuestion().getCorrectAnswer()));
			result.setSkipped(false);
		}
		try {
		result = resultRepo.save(result);
		}catch(Exception e) {
			return new BaseResponse("result"+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse("result"+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}
	
	@Override
	public UserResultDisplayModel createResults(List<ResultModel> resultModels) {
	    List<Result> results = new ArrayList<>();

	    for (ResultModel resultModel : resultModels) {
	        Result result = resultMapper.convertModelToDomain(resultModel);

	        // Check if user already answered this question
	        Result oldResult = resultRepo.isAlreadyAnswered(principle.getUserId(), resultModel.getQuestionId());
	        if (oldResult != null) {
	            result.setResultId(oldResult.getResultId());
	        }

	        result.setStatus(true);
	        result.setCreatedDate(LocalDateTime.now());
	        result.setCreatedBy(principle.getUserId());
	        result.setAnsweredAt(LocalDateTime.now());
	        result.setAnsweredBy(principle.getUserId());

	        if (result.getAnswered() == null) {
	            result.setCorrect(false);
	            result.setSkipped(true);
	        } else {
	            result.setCorrect(result.getAnswered().equals(result.getQuestion().getCorrectAnswer()));
	            result.setSkipped(false);
	        }

	        results.add(result);
	    }

	    try {
	        resultRepo.saveAll(results);
	    } catch (Exception e) {
	    
	    }
	    
	    Question question = questionRepo.findById(resultModels.get(0).getQuestionId()).orElse(null);
	    ViewAllResultModel resultModel = new ViewAllResultModel();
	    resultModel.setAnsweredBy(principle.getUserId());
	    if(question!=null) {
		    resultModel.setChapterId(question.getChapter().getChapterId());
		    resultModel.setSection(question.getSection());
		    resultModel.setSubjectId(question.getSubject().getSubjectId());
	    }

	    
	    UserResultDisplayModel response = getUserResult(resultModel);
	    return response;
	 //   return new BaseResponse("result" + CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}


	@Override
	public BaseResponse updateResult(ResultModel resultModel) {
		Result result = new Result();
		if(resultModel.getResultId()!=null)
				result = resultRepo.findById(resultModel.getResultId())
				.orElseThrow(() -> new CusException(" Result Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for Result ", HttpStatus.NOT_FOUND);
		result.setAnswered(resultModel.getAnswered());
		result.setAnsweredAt(resultModel.getAnsweredAt());
		result.setAnsweredBy(resultModel.getAnsweredBy());
		result.setModifiedDate(LocalDateTime.now());
		result.setModifiedBy(principle.getUserId());
		result = resultRepo.save(result);
		return new BaseResponse("result"+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deleteResult(ResultModel resultModel) {
		Result result = new Result();
		if(resultModel.getResultId()!=null)
				result = resultRepo.findById(resultModel.getResultId())
				.orElseThrow(() -> new CusException(" Result Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for Result ", HttpStatus.NOT_FOUND);
		result.setStatus(false);
		result.setModifiedDate(LocalDateTime.now());
		result.setModifiedBy(principle.getUserId());
		resultRepo.save(result);
		return new BaseResponse("Result "+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	@Override
	public ResultModel findById(ResultModel resultModel) {
		Result result = new Result();
		if(resultModel.getResultId()!=null)
				result = resultRepo.findById(resultModel.getResultId())
				.orElseThrow(() -> new CusException(" Result Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for Result ", HttpStatus.NOT_FOUND);
		resultModel = resultMapper.convertDomainToModel(result);
		return resultModel;
	}

	@Override
	public Page<ResultModel> getAllResult (UUID chapterId, int section, Pageable pageable)throws Exception{
		Page<Result> Result = resultRepo.findAll(new Specification<Result>() {

			@Override
			public Predicate toPredicate(Root<Result> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

	            if (chapterId != null) {
	            	List<UUID> questionIds = questionRepo.findByChapter(chapterId);
	                if (!questionIds.isEmpty()) {
	                    predicates.add(criteriaBuilder.in(root.get("question")).value(questionIds));
	                }
	            }
	            if (section != 0) {
	            	predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("question").get("section"), section)));
	            }

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<ResultModel> resultModel = resultMapper.ConverDomainToModel(Result);
	    return resultModel;
	}

	@Override
	public UserResultDisplayModel getUserResult(ViewAllResultModel resultModel) {
		UUID userId = principle.getUserId();
		List<Result> results = resultRepo.findBychapterIdAndSectionAndAnsweredBy(resultModel.getChapterId().toString(),resultModel.getSection(),userId.toString());
		int correctAns=0;
		int wrongAns=0;
		int skipped=0;
		List<ResultModel> resultModels = new ArrayList<>();
		for(Result result:results) {
			ResultModel resultM = new ResultModel();
			resultM.setAnswered(result.getAnswered());
			resultM.setAnsweredAt(result.getAnsweredAt());
			resultM.setCorrect(result.isCorrect());
			resultM.setCorrectAnswer(result.getQuestion().getCorrectAnswer());
			resultM.setOptionA(result.getQuestion().getOptionA());
			resultM.setOptionB(result.getQuestion().getOptionB());
			resultM.setOptionC(result.getQuestion().getOptionC());
			resultM.setOptionD(result.getQuestion().getOptionD());
			resultM.setQuestionId(result.getQuestion().getQuestionId());
			resultM.setQuestion(result.getQuestion().getQuestionName());
			resultM.setSkipped(result.isSkipped());
			resultM.setResultId(result.getResultId());
			if(result.isSkipped()) {
				skipped++;
			}else {
			if(result.isCorrect()) {
				correctAns++;
			}else {
				wrongAns++;
			}
		}
			resultModels.add(resultM);
		}

		SubjectModel subjectModel = new SubjectModel();
		subjectModel.setSubjectId(results.get(0).getQuestion().getSubject().getSubjectId());
		subjectModel = subjectService.findById(subjectModel);
		UserResultDisplayModel response = new UserResultDisplayModel();
		response.setSubjectId(subjectModel.getSubjectId());
		response.setSubjectName(subjectModel.getSubjectName());
		response.setScore(correctAns);
		double value = (((double)correctAns/(correctAns+wrongAns+skipped))*100);
		double roundedValue = Math.round(value * 100.0) / 100.0;
		String percentage =  roundedValue+"%";
		response.setPercentage(percentage);
		response.setCorrectAnswers(correctAns);
		response.setWrongAnswers(wrongAns);
		response.setSkippedAnswers(skipped);
		response.setSection(resultModel.getSection());
		response.setResultModels(resultModels);
		System.out.println("correctAns :"+correctAns);
		System.out.println("wrongAns :"+wrongAns);
		
		return response;
	}

	@Override
	public Boolean IsUserAttendedThisTestAlready(ViewAllQuestionModel viewModel) {
		UserResultDisplayModel response = new UserResultDisplayModel();
		ViewAllResultModel resultModel = new ViewAllResultModel();
		resultModel.setAnsweredBy(principle.getUserId());
	//	resultModel.setChapterId(viewModel.getChapterId());
		resultModel.setSubjectId(viewModel.getSubjectId());
		resultModel.setSection(viewModel.getSection());
		response = getUserResult(resultModel);
		if(response.getResultModels().isEmpty()||response.getResultModels().size()<30) {
			return true;
		}
		return LocalDateTime.now().minusHours(24).isAfter(response.getResultModels().get(0).getAnsweredAt());
		
	}
	
	@Override
	public List<SectionModel> getDistinctAvailavleSections() {
	    List<SectionModel> sectionModels = new ArrayList<>();

	    List<Object[]> sections = resultRepo.findsectionAndSubjectAndAnsweredBy(principle.getUserId());

	    // Group by subjectId
	    Map<UUID, SectionModel> subjectMap = new HashMap<>();
	    
	    


	    for (Object[] s : sections) {
	        Integer section = (Integer) s[0];
	        UUID subjectId = (UUID) s[1];
	        UUID chapterId = (UUID) s[2]; 
	        
	        

	        // get or create SectionModel for this subject
	        SectionModel model = subjectMap.computeIfAbsent(subjectId, sid -> {
	            SectionModel m = new SectionModel();
	            m.setSubjectId(sid);
	            m.setChapterId(chapterId);   
	            
	            Subject subject = subjectRepo.findById(sid).orElse(null);
	            if (subject != null) {
	                m.setSubjectName(subject.getSubjectName());
	            }
	        //    List<Integer> sections = resultRepo.findSectionBySubjectSubjectIdAndAnsweredBy(subjectId, principle.getUserId());            
	            m.setSections(resultRepo.findSectionBySubjectSubjectIdAndAnsweredBy(subjectId, principle.getUserId()));
	            return m;
	        });


	    }


	    sectionModels.addAll(subjectMap.values());
	    return sectionModels;
	}


	@Override
	public List<UserModel> findUserAttendedTests() {
		
		List<UserModel> userModelList=new ArrayList<>();
		List<UUID> userIds=resultRepo.findUsersAttendedTests();
		for(UUID userId:userIds) {
			UserModel userModel=new UserModel();
			userModel=userService.findByUserId(userId);
			userModelList.add(userModel);
		}
		return userModelList;
	}

	@Override
	public List<DashBoardModel> getDashboardData() {
		List<DashBoardModel>  dashboardList = new ArrayList<>();
		List<Subject> subjects = subjectRepo.findAll();
		for(Subject subject:subjects) {
			DashBoardModel dashboard = new DashBoardModel();
			List<Question> questionList = questionRepo.findBySubjectSubjectId(subject.getSubjectId());
			List<Integer> distinctSections = new ArrayList<Integer>();
			List<Integer> distinctAnsSections = new ArrayList<Integer>();
			if (!questionList.isEmpty()) {
			    distinctSections = questionList.stream()
			        .filter(q -> q.getSection() != 0) // skip nulls
			        .collect(Collectors.groupingBy(
			            Question::getSection,
			            Collectors.counting()
			        ))
			        .entrySet().stream()
			        .filter(entry -> entry.getValue() == 30)   // ✅ only sections with exactly 30 questions
			        .map(Map.Entry::getKey)
			        .sorted()
			        .collect(Collectors.toList());
			}


			distinctAnsSections = resultRepo.findSectionBySubjectSubjectIdAndAnsweredBy(subject.getSubjectId(),principle.getUserId());

			dashboard.setSubjectId(subject.getSubjectId());
			dashboard.setSubjectName(subject.getSubjectName());
			dashboard.setAvailableTest((long)distinctSections.size());
			dashboard.setAttendedTest((long)distinctAnsSections.size());
			dashboardList.add(dashboard);
		}
		return dashboardList;
	}
	


}
