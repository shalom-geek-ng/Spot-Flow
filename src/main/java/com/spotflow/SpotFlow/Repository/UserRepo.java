package com.spotflow.SpotFlow.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spotflow.SpotFlow.Entities.User;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional <User> findbyEmail(String email);

}
