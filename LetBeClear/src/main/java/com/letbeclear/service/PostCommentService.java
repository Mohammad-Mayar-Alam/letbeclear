package com.letbeclear.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letbeclear.model.PostComment;
import com.letbeclear.repository.PostCommentRepository;
import com.letbeclear.response.dto.PostCommentResponse;

@Service	
public class PostCommentService 
{
	@Autowired
	PostCommentRepository postCommentRepository;
	
//	public void insertPostComment(PostComment postComment)
//	{
//		postCommentRepository.save(postComment);
//	}
	
	public int insertPostComment(int postId,String comment,long userId)
	{
		try
		{
			return postCommentRepository.insertCommentPost(postId, comment, userId);
		}
		catch(Exception e)
		{
			System.out.println("Exception in PostCommentService insertPostComment()");
			e.printStackTrace();
			return 0;
		}
	}

	public List<PostCommentResponse> getAllCommentsOnPost(int postId) 
	{
		System.out.println("Inside PostCommentService getAllCommentsOnPost() ");
		//return postCommentRepository.findAll();
		List<PostComment> list=postCommentRepository.getAllComments(postId);
		List<PostCommentResponse> listComment=new ArrayList<>();
		
		try
		{
			for(PostComment pc:list)
			{
				PostCommentResponse postCommentResponse=new PostCommentResponse();
				postCommentResponse.setComment(pc.getComment());
				postCommentResponse.setCommentId(pc.getCommentId());
				postCommentResponse.setPostId(pc.getUserPost().getPostId());
				
				listComment.add(postCommentResponse);
			}
			return listComment;
		}
		catch(Exception e)
		{
			System.out.println("Exception in PostCommentService getAllCommentsOnPost()");
			e.printStackTrace();
			return null;
		}
	}

	public int editComment(int commentId, String comment) 
	{
		return postCommentRepository.editOldComment(commentId, comment);
	}

	public int deleteComment(int commentId) 
	{
		return postCommentRepository.deleteComment(commentId);
	}
}
