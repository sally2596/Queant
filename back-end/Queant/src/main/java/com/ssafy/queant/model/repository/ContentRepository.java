package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.content.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query(value = "select * from content where content_id=:contentId", nativeQuery = true)
    Content findByContentId(@Param("contentId") Long contentId);

}
