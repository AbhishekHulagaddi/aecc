package com.tierra.masterdata.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.tierra.masterdata.domain.Subject;
import com.tierra.masterdata.model.SubjectModel;

@Component
public class SubjectMapper {
	
	public Subject convertModelToDomain (SubjectModel subjectModel) {
		Subject subject = new Subject();
		BeanUtils.copyProperties(subjectModel, subject);
		return subject;
	}
	
	public SubjectModel convertDomainToModel (Subject subject) {
		SubjectModel subjectModel = new SubjectModel();
		BeanUtils.copyProperties(subject, subjectModel);
		return subjectModel;
	}

	public Page<SubjectModel> ConverDomainToModel(Page<Subject> subject) {
        List<SubjectModel> subjectModel =  new ArrayList<>();
        for(Subject subjectD : subject)
        {
        	SubjectModel subjectM = new SubjectModel();
              BeanUtils.copyProperties(subjectD, subjectM);
              subjectModel.add(subjectM);
        }
		
		
        return new PageImpl<>(subjectModel, subject.getPageable(), subject.getTotalElements());
    
	}

}
