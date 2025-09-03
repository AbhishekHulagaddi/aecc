package com.tierra.masterdata.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tierra.auth.utils.BaseResponse;
import com.tierra.masterdata.model.SubjectModel;

public interface SubjectService {
	
	public BaseResponse createSubject (SubjectModel subjectModel);
	
	public BaseResponse updateSubject (SubjectModel subjectModel);
	
	public BaseResponse deleteSubject (SubjectModel subjectModel);
	
	public SubjectModel findById (SubjectModel subjectModel);
	
	public Page<SubjectModel> getAllSubject (String SubjectName,Pageable pageable)throws Exception;

}
