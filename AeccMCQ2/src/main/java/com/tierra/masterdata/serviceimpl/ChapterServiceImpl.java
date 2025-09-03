package com.tierra.masterdata.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.CusException;
import com.tierra.auth.utils.CustomMessage;
import com.tierra.auth.utils.UserIdPrinciple;
import com.tierra.masterdata.domain.Chapter;
import com.tierra.masterdata.domain.Subject;
import com.tierra.masterdata.mapper.ChapterMapper;
import com.tierra.masterdata.model.ChapterModel;
import com.tierra.masterdata.repo.ChapterRepo;
import com.tierra.masterdata.repo.SubjectRepo;
import com.tierra.masterdata.service.ChapterService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
@Service
public class ChapterServiceImpl implements ChapterService{

	
	@Autowired
	ChapterMapper chapterMapper;
	
	@Autowired
	ChapterRepo chapterRepo;
	
	@Autowired
	SubjectRepo subjectRepo;
	
	@Autowired
	UserIdPrinciple principle;
	
	@Override
	public BaseResponse createChapter(ChapterModel chapterModel) {
		Chapter chapter = chapterMapper.convertModelToDomain(chapterModel);
		chapter.setStatus(true);  
		chapter.setCreatedDate(LocalDateTime.now());
		chapter.setCreatedBy(principle.getUserId());
		try {
		chapter = chapterRepo.save(chapter);
		}catch(Exception e) {
			return new BaseResponse(chapter.getChapterName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(chapter.getChapterName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateChapter(ChapterModel chapterModel) {
		Chapter chapter = new Chapter();
		if(chapterModel.getChapterId()!=null)
		   chapter = chapterRepo.findById(chapterModel.getChapterId())
		   				.orElseThrow(() -> new CusException(" Chapter Not Found ", HttpStatus.NOT_FOUND));
		else 
			throw new CusException(" Id cannot be Null for Chapter ", HttpStatus.NOT_FOUND);
		chapter.setChapterName(chapterModel.getChapterName());
		chapter.setModifiedDate(LocalDateTime.now());
		chapter.setModifiedBy(principle.getUserId());
		Subject subject = subjectRepo.findById(chapterModel.getSubjectId())
				.orElseThrow(() -> new CusException(" Vendor Not Found for Chapter " , HttpStatus.NOT_FOUND ));
		chapter.setSubject(subject);
		chapter = chapterRepo.save(chapter);
		return new BaseResponse(chapter.getChapterName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deleteChapter(ChapterModel chapterModel) {
		Chapter chapter = new Chapter();
		if(chapterModel.getChapterId()!=null)
		   chapter = chapterRepo.findById(chapterModel.getChapterId())
		   				.orElseThrow(() -> new CusException(" Chapter Not Found ", HttpStatus.NOT_FOUND));
		else 
			throw new CusException(" Id cannot be Null for Chapter ", HttpStatus.NOT_FOUND);
		chapter.setStatus(false);
		chapter.setModifiedDate(LocalDateTime.now());
		chapter.setModifiedBy(principle.getUserId());
		chapterRepo.save(chapter);
		return new BaseResponse(chapter.getChapterName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public ChapterModel findById(ChapterModel chapterModel) {
		Chapter chapter = new Chapter();
		if(chapterModel.getChapterId()!=null)
		   chapter = chapterRepo.findById(chapterModel.getChapterId())
		   				.orElseThrow(() -> new CusException(" Chapter Not Found ", HttpStatus.NOT_FOUND));
		else 
			throw new CusException(" Id cannot be Null for Chapter ", HttpStatus.NOT_FOUND);
		chapterModel = chapterMapper.convertDomainToModel(chapter);
		return chapterModel;
	}

	@Override
	public Page<ChapterModel> getAllChapter (String productName, Pageable pageable)throws Exception{
		Page<Chapter> chapter = chapterRepo.findAll(new Specification<Chapter>() {

			@Override
			public Predicate toPredicate(Root<Chapter> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (productName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("productName"), "%" + productName + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    return chapterMapper.ConverDomainToModel(chapter);
	}
	


}
