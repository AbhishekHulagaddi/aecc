package com.tierra.masterdata.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.WebConstantUrl;
import com.tierra.masterdata.model.ChapterModel;
import com.tierra.masterdata.model.SubjectModel;
import com.tierra.masterdata.model.ViewAllMasterDataModel;
import com.tierra.masterdata.service.ChapterService;


@RestController
@RequestMapping(WebConstantUrl.Chapter)
public class ChapterController {
	
	
	@Autowired
	ChapterService chapterService;


	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createChapter (@RequestBody ChapterModel chapterModel){
		BaseResponse baseResponse = chapterService.createChapter(chapterModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateChapter (@RequestBody ChapterModel chapterModel){
		BaseResponse baseResponse = chapterService.updateChapter(chapterModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteChapter (@RequestBody ChapterModel chapterModel){
		BaseResponse baseResponse = chapterService.deleteChapter(chapterModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody ChapterModel chapterModel){
		chapterModel = chapterService.findById(chapterModel);
		return new ResponseEntity<> (chapterModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllChapter ( @RequestBody ViewAllMasterDataModel viewModel) throws Exception{
		Map<String, Object> map = new HashMap<>();
		Sort sort =null;
		Pageable pages = null;
		
		if(viewModel.getPropertyName()!=null&&viewModel.getDirection()!=null) {
			if(viewModel.getDirection().equalsIgnoreCase("desc")) {
				 sort = Sort.by(Sort.Direction.DESC, viewModel.getPropertyName());
			}else{
				 sort = Sort.by(Sort.Direction.ASC, viewModel.getPropertyName());
			}
			 pages = PageRequest.of(viewModel.getPage() - 1, viewModel.getSize() == 0 ? Integer.MAX_VALUE : viewModel.getSize(), sort);
			
		}else {
			pages = PageRequest.of(viewModel.getPage() - 1, viewModel.getSize() == 0 ? Integer.MAX_VALUE : viewModel.getSize());

		}
		Page<ChapterModel> chapterModel = chapterService.getAllChapter(viewModel.getChapterName(), pages);

		List<ChapterModel> chapterModelList = chapterModel.getContent();
		if (chapterModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", chapterModel.getTotalElements());
			map.put("total_pages", chapterModel.getTotalPages());
			map.put("Chapters", chapterModelList);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", chapterModel.getTotalElements());
		map.put("total_pages", chapterModel.getTotalPages());
		map.put("Chapters", chapterModelList);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}


}
