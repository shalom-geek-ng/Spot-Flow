package com.spotflow.SpotFlow.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spotflow.SpotFlow.Entities.Url;
import com.spotflow.SpotFlow.Entities.User;

public interface UrlRepository extends JpaRepository<Url, Long> {
	Optional<Url> findByShortCode(String shortCode);
	
	List<Url> findByCreatedBy(User user);
}
