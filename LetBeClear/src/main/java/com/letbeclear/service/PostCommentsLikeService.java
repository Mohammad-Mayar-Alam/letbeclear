package com.letbeclear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letbeclear.repository.PostCommentLikeRepository;

@Service
public class PostCommentsLikeService 
{
	@Autowired 
	PostCommentLikeRepository commentsLikeRepository;
	
	public int insertCommentLike(int commentId, long userId)
	{
		try
		{
			return commentsLikeRepository.addCommentLike(commentId, userId);
		}
		catch(Exception e)
		{
			System.out.println("Exception in CommentsLikeService insertCommentsLike");
			e.printStackTrace();
			return 0;
		}
	}
	
	public int deleteCommentLike(int commentId, long userId)
	{
		try
		{
			int status=commentsLikeRepository.removeCommentLike(commentId, userId);
			System.out.println("Inside PostCommentsLikeService deleteCommentLike() status is "+status);
			return status;
		}
		catch(Exception e)
		{
			System.out.println("Exception in PostCommentLikeService");
			e.printStackTrace();
			return 0;
		}
	}
}
