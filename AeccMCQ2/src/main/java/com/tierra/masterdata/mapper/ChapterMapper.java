package com.tierra.masterdata.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.tierra.auth.utils.CusException;
import com.tierra.masterdata.domain.Chapter;
import com.tierra.masterdata.domain.Subject;
import com.tierra.masterdata.model.ChapterModel;
import com.tierra.masterdata.repo.SubjectRepo;

@Component
public class ChapterMapper {
	
	@Autowired
	SubjectRepo subjectRepo;
	
	public Chapter convertModelToDomain (ChapterModel chapterModel) {
		Chapter chapter = new Chapter();
		BeanUtils.copyProperties(chapterModel, chapter);
		Subject subject = subjectRepo.findById(chapterModel.getSubjectId())
				.orElseThrow(() -> new CusException(" Subject Not Found for Chapter '"+chapterModel.getChapterName()+"' ", HttpStatus.NOT_FOUND ));
		chapter.setSubject(subject);
		return chapter;
	}
	
	public ChapterModel convertDomainToModel (Chapter chapter) {
		ChapterModel chapterModel = new ChapterModel();
		BeanUtils.copyProperties(chapter, chapterModel);
		chapterModel.setSubjectId(chapter.getSubject().getSubjectId());
		chapterModel.setSubjectName(chapter.getSubject().getSubjectName());
		return chapterModel;
	}

	public Page<ChapterModel> ConverDomainToModel(Page<Chapter> chapter) {
			
	        List<ChapterModel> chapterModel =  new ArrayList<>();
	        for(Chapter chapterD : chapter)
	        {
	        	ChapterModel chapterM = new ChapterModel();
	              BeanUtils.copyProperties(chapterD, chapterM);
	              chapterM.setSubjectId(chapterD.getSubject().getSubjectId());
	              chapterM.setSubjectName(chapterD.getSubject().getSubjectName());
	              chapterModel.add(chapterM);
	        }
			
			
	        return new PageImpl<>(chapterModel, chapter.getPageable(), chapter.getTotalElements());
	    }

}
