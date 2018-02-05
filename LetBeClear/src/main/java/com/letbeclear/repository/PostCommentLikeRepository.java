package com.letbeclear.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.letbeclear.model.CommentsLike;

@Repository
public interface PostCommentLikeRepository extends JpaRepository<CommentsLike, Integer>
{
	@Modifying
	@Query(value="insert into comments_like(comment_id, user_id) values(:commentId, :userId)", nativeQuery=true)
	@Transactional
	public int addCommentLike(@Param("commentId") Integer commentId, @Param("userId")Long userId);
	
	@Modifying
	@Query(value="delete from comments_like where comment_id= :commentId and user_id= :userId", nativeQuery=true)
	@Transactional
	public int removeCommentLike(@Param("commentId") Integer commentId, @Param("userId")Long userId );
}
