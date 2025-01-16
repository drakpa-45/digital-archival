package com.alfresco.archival.config;

import java.util.UUID;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public class RepositoryConfig {


	// Base generic repository
	public interface MasterRepository<T> extends JpaRepository<T, UUID> {
	}

}
