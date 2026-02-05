package com.tierra.masterdata.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tierra.masterdata.domain.Chapter;

public interface ChapterRepo extends JpaRepository<Chapter, UUID>,JpaSpecificationExecutor<Chapter>{

}
