package com.company.teaPost.repositories;

import com.company.teaPost.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, String> {
    Admin getByEmail(String email);
}
