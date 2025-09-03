package com.tierra.accesspolicy.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.tierra.accesspolicy.domain.Feature;
import com.tierra.accesspolicy.model.FeatureModel;

@Component
public class FeatureMapper {
	
	public FeatureModel ConverDomainToModel (Feature feature) {
		FeatureModel featureModel = new FeatureModel();
		BeanUtils.copyProperties(feature,featureModel );
		return featureModel;
	}

	public Feature ConverModelToDomain (FeatureModel featureModel) {
		Feature feature = new Feature();
		BeanUtils.copyProperties(featureModel,feature );
		return feature;
	}

	public Page<FeatureModel> ConverDomainToModel(Page<Feature> feature) {
        List<FeatureModel> featureModel =  new ArrayList<>();
        for(Feature featureD : feature)
        {
        	FeatureModel featureM = new FeatureModel();
              BeanUtils.copyProperties(featureD, featureM);
              featureModel.add(featureM);
        }
		
		
        return new PageImpl<>(featureModel, feature.getPageable(), feature.getTotalElements());
    }


}
