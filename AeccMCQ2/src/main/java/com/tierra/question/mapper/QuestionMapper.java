package com.tierra.question.mapper;

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
import com.tierra.masterdata.repo.ChapterRepo;
import com.tierra.masterdata.repo.SubjectRepo;
import com.tierra.question.domain.Question;
import com.tierra.question.model.QuestionModel;


@Component
public class QuestionMapper {
	
	@Autowired
	SubjectRepo subjectRepo;
	
	@Autowired
	ChapterRepo chapterRepo;

	public Question convertModelToDomain (QuestionModel questionModel) {
		Question question = new Question();
		BeanUtils.copyProperties(questionModel, question);
		Subject subject = subjectRepo.findById(questionModel.getSubjectId())
				.orElseThrow(() -> new CusException(" Subject Not Found For Question ", HttpStatus.NOT_FOUND));
		Chapter chapter = chapterRepo.findById(questionModel.getChapterId())
				.orElseThrow(() -> new CusException(" Chapter Not Found For Question ", HttpStatus.NOT_FOUND));
		question.setSubject(subject);
		question.setChapter(chapter);
		return question;
	}
	
	public QuestionModel convertDomainToModel (Question question) {
		QuestionModel questionModel = new QuestionModel();
		BeanUtils.copyProperties(question, questionModel);
		questionModel.setSubjectId(question.getSubject().getSubjectId());
		questionModel.setChapterId(question.getChapter().getChapterId());
		questionModel.setSubjectName(question.getSubject().getSubjectName());
		questionModel.setChapterName(question.getChapter().getChapterName());
		return questionModel;
	}

	public Page<QuestionModel> ConverDomainToModel(Page<Question> question) {
		
	        List<QuestionModel> questionModel =  new ArrayList<>();
	        for(Question questionD : question)
	        {
	        	QuestionModel questionM = new QuestionModel();
	              BeanUtils.copyProperties(questionD, questionM);
	              if(questionD.getSubject()!=null) {
	  	            questionM.setSubjectId(questionD.getSubject().getSubjectId());
			        questionM.setSubjectName(questionD.getSubject().getSubjectName());
	              }
	              if(questionD.getChapter()!=null) {
		              questionM.setChapterId(questionD.getChapter().getChapterId());
		              questionM.setChapterName(questionD.getChapter().getChapterName());
	              }
	              questionModel.add(questionM);
	        }
			
			
	        return new PageImpl<>(questionModel, question.getPageable(), question.getTotalElements());
	    }
	
	public List<QuestionModel> ConverDomainToModel(List<Question> question) {
		
        List<QuestionModel> questionModel =  new ArrayList<>();
        for(Question questionD : question)
        {
        	QuestionModel questionM = new QuestionModel();
              BeanUtils.copyProperties(questionD, questionM);
              questionM.setSubjectId(questionD.getSubject().getSubjectId());
              questionM.setChapterId(questionD.getChapter().getChapterId());
	          questionM.setSubjectName(questionD.getSubject().getSubjectName());
              questionM.setChapterName(questionD.getChapter().getChapterName());

              questionModel.add(questionM);
        }
		
		
        return questionModel;
    }
}
