package com.tierra.masterdata.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tierra.auth.utils.BaseResponse;
import com.tierra.masterdata.model.ChapterModel;



public interface ChapterService {
	
	public BaseResponse createChapter (ChapterModel chapterModel);
	
	public BaseResponse updateChapter (ChapterModel chapterModel);
	
	public BaseResponse deleteChapter (ChapterModel chapterModel);
	
	public ChapterModel findById (ChapterModel chapterModel);
	
	public Page<ChapterModel> getAllChapter (String ChapterName, Pageable pageable)throws Exception;

}
