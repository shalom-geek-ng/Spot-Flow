package com.spotflow.SpotFlow.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Url {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String originalUrl;
	private String shortCode;
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;
	private long clickCount = 0;
	@ManyToOne(fetch = FetchType.LAZY,optional = true)
	@JoinColumn(name="user_id", nullable = true)
	private User createdBy;
	
}
