package com.letbeclear.response.dto;

import java.util.List;

import com.letbeclear.model.PostComment;

public class PostCommentGetResponseDto 
{
	String token;
	String message;
	boolean flag;
	List<PostCommentResponse> commentsList;
	
	public String getToken() 
	{
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public List<PostCommentResponse> getCommentsList() {
		return commentsList;
	}
	public void setCommentsList(List<PostCommentResponse> commentsList) {
		this.commentsList = commentsList;
	}
	
}
