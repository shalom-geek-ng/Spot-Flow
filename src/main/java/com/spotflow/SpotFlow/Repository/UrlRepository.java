package com.spotflow.SpotFlow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spotflow.SpotFlow.Entities.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {

}
