package com.raul.phoneagenda.repository;

import com.raul.phoneagenda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstById(Long id);
    /*@Query(value = "SELECT * FROM utilizatori u WHERE u.name ILIKE '%' || :name || '%'", nativeQuery = true)*/
    List<User> findAllByName(String name);
    User findFirstByPhoneNumber(String phoneNumber);
    @Query(value = "SELECT * FROM utilizatori ",nativeQuery = true)
    List<User> findAllUsers();
}
