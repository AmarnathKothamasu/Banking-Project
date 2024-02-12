package com.banking.ank.repostiories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.banking.ank.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findFirstByEmail(String email);

	List<User> findAll();

	Optional<User> findByEmail(String email);

	@Query("SELECT u.id FROM User u WHERE u.email = :email")
	Long findUserIdByEmail(String email);
}
