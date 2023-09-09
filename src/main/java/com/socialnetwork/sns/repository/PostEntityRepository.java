package com.socialnetwork.sns.repository;

import com.socialnetwork.sns.model.entity.PostEntity;
import com.socialnetwork.sns.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findAllByUser(UserEntity entity, Pageable pageable);
}
