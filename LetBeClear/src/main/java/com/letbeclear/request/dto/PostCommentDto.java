package com.letbeclear.request.dto;

public class PostCommentDto 
{
	//Used by PostCommentController
	private int postId;
	private String comment;
	
	//Used by PostCommentLikeController
	private int commentId;
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) 
	{
		this.comment = comment;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
}
