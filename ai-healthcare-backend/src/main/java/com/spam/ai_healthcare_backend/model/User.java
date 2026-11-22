
package com.spam.ai_healthcare_backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	private String name;

	@Column(length = 150, nullable = false, unique = true)
	private String email;

	@Column(name = "phone_number", length = 15, nullable = false)
	private String phoneNumber;

	@Column(length = 255, nullable = false)
	private String password;

	@Column(length = 20, nullable = false)
	private String role;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

}
