package com.rizwanmushtaq.ElectronicStore.repositories;

import com.rizwanmushtaq.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
