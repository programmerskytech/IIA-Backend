package com.astro.repository;

import com.astro.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Integer> {
  UserMaster findByUserIdAndPassword(Integer userId, String password);

   // Optional<UserMaster> findByCreatedBy(String createdBy);
   Optional<UserMaster> findByCreatedBy(String createdBy);

    UserMaster findByUserId(Integer createdBy);

    @Query("SELECT u FROM UserMaster u WHERE u.userId IN :ids")
    List<UserMaster> findByUserIdIn(@Param("ids") Set<Integer> ids);
 @Query("SELECT u.userName FROM UserMaster u WHERE u.userId = :userId")
 String findUserNameByUserId(@Param("userId") Integer userId);

}
