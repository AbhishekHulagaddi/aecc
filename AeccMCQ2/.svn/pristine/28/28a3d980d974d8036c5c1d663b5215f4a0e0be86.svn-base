package com.rim.masterdata.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.rim.masterdata.domain.Quality;
import com.rim.masterdata.model.QualityModel;

@Component
public class QualityMapper {
	
	public Quality convertModelToDomain (QualityModel qualityModel) {
		Quality quality = new Quality();
		BeanUtils.copyProperties(qualityModel, quality);
		return quality;
	}
	
	public QualityModel convertDomainToModel (Quality quality) {
		QualityModel qualityModel = new QualityModel();
		BeanUtils.copyProperties(quality, qualityModel);
		return qualityModel;
	}

	public Page<QualityModel> ConverDomainToModel(Page<Quality> quality) {
        List<QualityModel> qualityModel =  new ArrayList<>();
        for(Quality qualityD : quality)
        {
        	QualityModel qualityM = new QualityModel();
              BeanUtils.copyProperties(qualityD, qualityM);
              qualityModel.add(qualityM);
        }
		
		
        return new PageImpl<>(qualityModel, quality.getPageable(), quality.getTotalElements());
    }

}
