package com.letbeclear.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letbeclear.domain.JwtUser;
import com.letbeclear.request.dto.PostCommentDto;
import com.letbeclear.response.dto.PostCommentGetResponseDto;
import com.letbeclear.response.dto.PostCommentResponse;
import com.letbeclear.response.dto.ResponseSender;
import com.letbeclear.security.JwtAuthenticationToken;
import com.letbeclear.service.PostCommentService;
import com.letbeclear.service.PostCommentsLikeService;
import com.letbeclear.utils.JwtGenerator;

@Controller
public class PostCommentController 
{
	@Autowired
	JwtUser jwtUser;
	
	@Autowired
	PostCommentService postCommentService;
	
	@Autowired
	JwtGenerator jwtGenerator;
	
	@Autowired
	PostCommentsLikeService commentsLikeService;
	
	@Autowired
	JwtAuthenticationToken jwtAuthenticationToken;
//	
//	@Autowired
//	UsersService usersService;
//	
//	@Autowired
//	UserPostService userPostService;
//	

//	
//	//method using simply extracting object
//	
//	@RequestMapping(value="rest/post/comment", method=RequestMethod.POST)
//	public @ResponseBody void commentOnPost(@RequestBody PostCommentDto postCommentDto)
//	{
//		//Getting the postId from UserPost class
//		int postId=postCommentDto.getPostId();
//		String comment=postCommentDto.getComment();
//		
//		System.out.println("Inside PostCommentController commentOnPost method");
//		System.out.println("postRequest post id "+postId);
//		System.out.println("post comment "+comment);
//		
//		
//		//fetching userId of user who has commented on post for saving in post_comment
//		long userId=jwtUser.getUserId();
//		System.out.println("Inside PostCommentController userId is "+userId);
//		
//		//fetching user obj for saving it in PostComment class
//		//as userId is not available in PostComment,User user obj is available there
//		
//		Users user=usersService.getUserByUserId(userId);
//
//		//fetching obj of UserPost by postId
//		
//		UserPost userPost=userPostService.getUserPostObjByPostId(postId);
//		//Saving Comment of Post in post_comment table
//		
//		PostComment postComment=new PostComment();
//		postComment.setUsers(user);
//		postComment.setComment(comment);
//		postComment.setUserPost(userPost);
//		
//		//Saving PostComment object in Database
//		
//		postCommentService.insertPostComment(postComment);
//		System.out.println("Inserted Successfully");
//	}
	
	//method by writing query on the query method
	
	@RequestMapping(value="/rest/comment/post", method=RequestMethod.POST)
	public @ResponseBody ResponseSender commentOnPost(@RequestBody PostCommentDto postCommentDto)
	{
		int postId=postCommentDto.getPostId();
		String comment=postCommentDto.getComment();
		
		//fetching userId of the user from jwtUser i.e from token
		long userId=jwtUser.getUserId();
		System.out.println("Jwt User is "+jwtUser.getUserId());
		
		System.out.println("postRequest post id "+postId);
		System.out.println("post comment "+comment);
		System.out.println("Inside PostCommentController userId is "+userId);
		
		int status=postCommentService.insertPostComment(postId, comment, userId);
		
		System.out.println("Inside PostCommentController commentOnPost() status is "+status);
		
		ResponseSender responseSender= new ResponseSender();
		if(status==1)
		{
			//fetching token from JwtAuthenticationToken class
			//String token=jwtAuthenticationToken.getToken();
			
			//String token=jwtGenerator.generate(jwtUser, jwtConfig);
			
			String token=jwtGenerator.generate();
			
			responseSender.setToken(token);
			responseSender.setMessage("Successfully Commented");
			responseSender.setFlag(true);
		}
		else
		{
			responseSender.setMessage("Failed");
			responseSender.setFlag(false);
		}
		return responseSender;
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value="rest/comment/getComment", method=RequestMethod.POST)
	public @ResponseBody PostCommentGetResponseDto getCommentsOnPost(@RequestBody PostCommentDto postCommentDto)
	{
		int postId=postCommentDto.getPostId();
		//long userId=jwtUser.getUserId();
		
		System.out.println("Inside PostCommentController getCommentsOnPost() postId "+postId);
		
		List<PostCommentResponse> listComments=postCommentService.getAllCommentsOnPost(postId);
		
		System.out.println("Inside PostCommentController getCommentsOnPost() listComments is "+listComments);
		
//		Iterator<PostComment> iterator=listComments.iterator();
//		while(iterator.hasNext())
//		{
//			System.out.println("Inside PostCommentController getCommentsOnPost() comment is "+iterator.next());
//		}
			
		PostCommentGetResponseDto postCommentGetResponseDto=new PostCommentGetResponseDto();
		
		if(listComments!=null)
		{
			//String token=jwtGenerator.generate(jwtUser, jwtConfig);
			String token=jwtGenerator.generate();
			
			postCommentGetResponseDto.setCommentsList(listComments);
			postCommentGetResponseDto.setMessage("Success");
			postCommentGetResponseDto.setToken(token);
			postCommentGetResponseDto.setFlag(true);
		}
		else
		{
			postCommentGetResponseDto.setMessage("Failed");
			postCommentGetResponseDto.setFlag(false);
		}
		return postCommentGetResponseDto;
	}
	
	
	@CrossOrigin(origins="*")
	@RequestMapping(value="/rest/post/comment/edit", method=RequestMethod.POST)
	public @ResponseBody ResponseSender editComment(@RequestBody PostCommentDto postCommentDto)
	{
		int commentId=postCommentDto.getCommentId();
		String comment=postCommentDto.getComment();
		
		int status=postCommentService.editComment(commentId,comment);
		
		System.out.println("Inside PostCommentEditController editComment() status is "+status);
		
		ResponseSender responseSender=new ResponseSender();
		
		if(status==1)
		{
			String token=jwtGenerator.generate();
			
			responseSender.setToken(token);
			responseSender.setMessage("Success");
			responseSender.setFlag(true);
		}
		else
		{
			responseSender.setMessage("Failed");
			responseSender.setFlag(false);
		}
		
		return responseSender;
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value="/rest/comment/post/like", method=RequestMethod.POST)
	public @ResponseBody ResponseSender likePost(@RequestBody PostCommentDto postCommentDto)
	{
		int commentId=postCommentDto.getCommentId();
		long userId=jwtUser.getUserId();
		
		System.out.println("Inside PostCommentLikeController likePost() commentId "+commentId);
		System.out.println("Inside PostCommentLikeController likePost() userId "+userId);
		
		int status=commentsLikeService.insertCommentLike(commentId,userId);
	
		System.out.println("Inside PostCommentLikeController status "+status);
		
		ResponseSender responseSender= new ResponseSender();
		if(status==1)
		{
			//fetching token from JwtAuthenticationToken as token will be passed in it
			//from the attemptAuhentication() of JwtAuthenticationTokenFilter
			
			//String token=jwtAuthenticationToken.getToken();
			
			//We need to refresh token each time
			//String token=jwtGenerator.generate(jwtUser, jwtConfig);
			String token=jwtGenerator.generate();
			
			System.out.println("Inside PostCommentLikeController likePost() token is "+token);
			
			responseSender.setToken(token);
			responseSender.setMessage("Success");
			responseSender.setFlag(true);
		}
		else
		{
			responseSender.setMessage("Failed");
			responseSender.setFlag(false);
		}
		System.out.println("Inside PostCommentLikeController likePost() ResponseSender is "+responseSender);

		return responseSender;
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value="/rest/comment/post/dislike", method=RequestMethod.POST)
	public @ResponseBody ResponseSender dislikePost(@RequestBody PostCommentDto postCommentDto)
	{
		long userId=jwtUser.getUserId();
		int commentId=postCommentDto.getCommentId();
		
		int status=commentsLikeService.deleteCommentLike(commentId, userId);
		
		System.out.println("Inside PostCommentLikeController dislikePost() status is "+status);
		
		ResponseSender responseSender=new ResponseSender();
		
		if(status==1)
		{
			//String token=jwtGenerator.generate(jwtUser, jwtConfig);
			String token=jwtGenerator.generate();
			
			System.out.println("Inside PostCommentLikeController dislikePost() token is "+token);
			responseSender.setToken(token);
			responseSender.setMessage("Success");
			responseSender.setFlag(true);
		}
		else
		{
			responseSender.setMessage("Failed");
			responseSender.setFlag(false);
		}
		System.out.println("Inside PostCommentLikeController dislikePost() ResponseSender is "+responseSender);
		return responseSender;
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value="/rest/comment/delete", method=RequestMethod.POST)
	public @ResponseBody ResponseSender deleteComment(@RequestBody PostCommentDto postCommentDto)
	{
		int commentId=postCommentDto.getCommentId();
		
		int status=postCommentService.deleteComment(commentId);
		
		System.out.println("Inside PostCommentController deleteComment() status is "+status);
		
		ResponseSender responseSender=new ResponseSender();
		if(status==1)
		{
			String token=jwtGenerator.generate();
			
			responseSender.setToken(token);
			responseSender.setMessage("Success");
			responseSender.setFlag(true);
		}
		else
		{
			responseSender.setMessage("Failed");
			responseSender.setFlag(false);
		}
		
		return responseSender;
	}
}
