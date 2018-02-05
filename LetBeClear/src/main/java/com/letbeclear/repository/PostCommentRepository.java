package com.letbeclear.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.type.TrueFalseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.letbeclear.model.PostComment;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer>
{
	
	//When using @Query the name of the method doesn't matters here i am not giving 
	//name like saveCommentPost using save as prefix
	
	@Modifying
	@Query(value="insert into post_comment (post_id, comment, user_id) values(:postId, :comment, :userId)", nativeQuery=true)
	@Transactional
	public int insertCommentPost(@Param("postId")Integer postId, @Param("comment")String comment, @Param("userId")Long userId);

//	@Modifying
//	@Transactional
	
	//GETTING COMMENTS ON POST USING NAMED QUERY
	@Query(value="select * from post_comment where post_id=:postId", nativeQuery=true)
	public List<PostComment> getAllComments(@Param("postId")Integer postId);
	
	//GETTING COMMENTS ON POST USING POSITION BASED PARAMETER QUERY
//	@Query(value="select p from PostComment p where p.userPost.postId=?1")
//	public List<PostComment> getAllComments(Integer postId);
	
	//METHOD TO EDIT OLD COMMENT
	@Modifying
	@Query(value="update PostComment p set p.comment=?2 where p.commentId=?1")
	@Transactional
	public int editOldComment(int commentId, String comment);

	@Modifying
	@Transactional
	@Query(value="delete from post_comment where comment_id=:commentId", nativeQuery=true)
	public int deleteComment(@Param("commentId") Integer commentId);
}
