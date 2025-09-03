package com.tierra.message.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tierra.message.domain.Message;

public interface MessageRepo extends JpaRepository<Message, UUID>,JpaSpecificationExecutor<Message>{

}
