package com.letbeclear.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.letbeclear.mail.ForgotPasswordEmailSender;
import com.letbeclear.model.Address;
import com.letbeclear.model.JwtAuthenticationToken;
import com.letbeclear.model.JwtUser;
import com.letbeclear.model.ResponseSender;
import com.letbeclear.model.UserReg;
import com.letbeclear.model.Users;
import com.letbeclear.security.UserDetailsImpl;
import com.letbeclear.service.UsersService;
import com.letbeclear.token.JwtGenerator;
import com.letbeclear.token.config.JwtConfig;
import com.letbeclear.utils.LoginDetailsValidator;

@RestController
public class LoginController
{
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@Autowired
	private JwtUser jwtUser;
	
	@Autowired
	JwtConfig jwtConfig;
	
	@Autowired
	private LoginDetailsValidator loginDetailsValidator;
	
	@Autowired
	private ForgotPasswordEmailSender forgotPasswordEmailSender;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private JwtAuthenticationToken jwtAuthenticationToken;
	
	HttpServletRequest request;
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String testProject()
	{
		return "Yes It's Working!!!";
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseSender loginMethod(Authentication authResult)
	{
		//The Principal is the userDetailsImpl Object
		
		UserDetailsImpl userDetailsImpl=((UserDetailsImpl)authResult.getPrincipal());
		
		UserReg userReg=userDetailsImpl.getUserReg();
		
		ResponseSender responseSender=new ResponseSender();	
		
		Users user=usersService.getUserByUserId(userReg.getUserId());
			
		//user found in the db, make token for it
		
//		JwtUser jwtUser=new JwtUser();
//		JwtGenerator jwtGenerator= new JwtGenerator();
		
		jwtUser.setEmail(userReg.getLoginid());	//Loginid is email in user_reg table
		jwtUser.setUserId(userReg.getUserId());
		jwtUser.setRole(user.getProfiletype());	//ProfileType id role in users table
		
		//String token=jwtGenerator.generate(jwtUser, jwtConfig);
		String token=jwtGenerator.generate();
		
		System.out.println("Inside JwtUsernamePasswordAuthenticationFilter successfulAuthentication() token is \n"+token);
		//adding token in the response 
		
		responseSender.setToken(token);
		responseSender.setMessage("Success");
		responseSender.setFlag(true);
		
		//Setting token to the JwtAuthenticationToken to ensure that when user will come again in future
		//he/she must be logged in
		
		jwtAuthenticationToken.setToken(token);
		
		//String headerName=jwtConfig.getHeaderName();
		//System.out.println("Header name "+headerName);
		//response.setHeader(headerName, token);
		//response.addHeader(headerName, token);
		
		return responseSender;
	}
	
	@RequestMapping(value="/loginFailed", method=RequestMethod.POST)
	public ResponseSender loginFailed()
	{
		ResponseSender responseSender=new ResponseSender();
		
		responseSender.setMessage("Failed");
		responseSender.setFlag(false);
		
		return responseSender;
	}

	// /loginPriti returning Simple String but we need to send token and Status both
	//therefore going to use ResponseEntity<String>
	
//	@RequestMapping(value="/loginPriti", method=RequestMethod.POST)
//	public @ResponseBody String loginUser(@RequestBody LBCLogin login)
//	{
//		System.out.println("Inside Login");
//		System.out.println("Email "+login.getEmail());
//		System.out.println("Password "+login.getPassword());
//		
//		boolean isValid=loginDetailsValidator.ValidateEmailPassword(login);
//		
//		System.out.println("isValid Email & Password "+isValid);
//		
//		if(isValid)
//		{
//			return "Valid Email and Password";
//		}
//		else
//		{
//			return "Invaild Email and Password";
//		}
//	}
	
	// /login method which will return both token and Status
//		@CrossOrigin(origins="*")
//		@RequestMapping(value="/login", method=RequestMethod.POST)
//		public @ResponseBody ResponseSender loginUser(@RequestBody LBCLogin login,HttpServletRequest request)
//		{
//			System.out.println("Inside Login Priti");
//			System.out.println("Email "+login.getEmail());
//			System.out.println("Password "+login.getPassword());
//			
//			UserReg userReg=loginDetailsValidator.validateEmailPassword(login);
//			
////			HttpSession session=request.getSession(false);
////			System.out.println("Insdide LoginController login() session is "+session);
//			
//			ResponseSender response = new ResponseSender();
//			
//			if(userReg!=null)
//			{
//				System.out.println("user email"+userReg.getLoginid());
//				System.out.println("user password"+userReg.getLogonpassword());
//				
//				//getting userId from UserReg Table
//				long userId=userReg.getUserId();
//				
//				//Making Session of the User
////				HttpSession session=request.getSession();
////				session.setMaxInactiveInterval(5*60);
////				session.setAttribute("userId", String.valueOf(userId) );
//				
//				//System.out.println("Request Session "+session);
//				
//				//getting role from users table
//			
//				Users users=usersService.getProfileType(userId);
//				
//				jwtUser.setEmail(userReg.getLoginid());
//				jwtUser.setRole(users.getProfiletype());
//				jwtUser.setUserId(userReg.getUserId());
//				
//				//Generating Token
//				String token=jwtGenerator.generate(jwtUser);
//				
//				//Setting token in JwtAuthenticationToken
//				jwtAuthenticationToken.setToken(token);
//				
//				System.out.println("Inside LoginController login() token is "+token);
//				//HttpStatus.OK error code 200
//				response.setMessage("Success");
//				response.setToken(token);
//				response.setFlag(true);
//				
//				return response;
//			}
//			else
//			{
//				System.out.println("Inside Login priti null ");
//				//HttpStatus.NOT_FOUND error code 404
//				response.setMessage("Failed");
//				response.setFlag(false);
//				
//				return response;
//			}
//		}
	
	@RequestMapping(value="/rest/logout", method=RequestMethod.GET)
	public ResponseSender logoutUser(HttpServletRequest request)
	{
//		HttpSession session=request.getSession(false);
//		System.out.println("Insdide LoginController logoutUser session is "+session);
		
		String token=jwtAuthenticationToken.getToken();
		
		System.out.println("Inside LoginController logoutUser() before token is "+token);
		
		ResponseSender responseSender=new ResponseSender();
		
		if(token!=null)
		{
			//session.invalidate();
			jwtAuthenticationToken.setToken(null);
			System.out.println("Inside LoginController logoutUser() after token is "+jwtAuthenticationToken.getToken());
			responseSender.setMessage("Sucessfully LoggedOut");
			responseSender.setFlag(true);
		}
		else
		{
			responseSender.setMessage("Please Login First");
			responseSender.setFlag(false);
		}
		return responseSender;
	}
	
//	// /login method which will return both token and Status
//	
//	@CrossOrigin(origins="*")
//	@RequestMapping(value="/loginPriti", method=RequestMethod.POST)
//	public @ResponseBody ResponseEntity<String> loginUser(@RequestBody LBCLogin login)
//	{
//		System.out.println("Inside Login Priti");
//		System.out.println("Email "+login.getEmail());
//		System.out.println("Password "+login.getPassword());
//		
//		Users user=loginDetailsValidator.validateEmailPassword(login);
//		
//		if(user!=null)
//		{
//			//HttpStatus.OK error code 200
//			return new ResponseEntity<String>(tokenGenerator.generator(user),HttpStatus.OK);
//		}
//		else
//		{
//			//HttpStatus.NOT_FOUND error code 404
//			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
//		}
//	}
	
	
	
//	@RequestMapping(value="admin/loginVerify", method=RequestMethod.POST)
//	public String loginUser(@RequestBody LBCLogin login)
//	{
//		//boolean isValidated=
//		System.out.println("Inside Login");
//		System.out.println("Email "+login.getEmail());
//		System.out.println("Password "+login.getPassword());
//		
//		return "Inside Login";
//	}
	
	/////////////////     FORGOT PASSWORD/////////////////////
	
	@CrossOrigin(origins="*")
	@RequestMapping(value="/rest/forgotPassword", method=RequestMethod.POST)
	public ResponseSender forgotEmail(@RequestBody Address addressTable)
	{
		System.out.println("Inside forgot Password "+addressTable.getEmail());
	
		//Verifying User from DataBase
		UserReg userReg=loginDetailsValidator.validateEmail(addressTable.getEmail());
		
		ResponseSender response = new ResponseSender();
		
		if(userReg!=null)
		{
			System.out.println("Inside forgot Password email "+userReg.getLoginid());
			System.out.println("Inside forgot Password password "+userReg.getLogonpassword());
			
			//Send Password to the Email
			forgotPasswordEmailSender.sendEmail(userReg.getLoginid(),userReg.getLogonpassword());
			
			response.setMessage("Success");
			response.setToken(new JwtAuthenticationToken().getToken());
			response.setFlag(true);
			
			return response;
		}
		else
		{
			//HttpStatus.NOT_FOUND error code 404
			response.setMessage("Failed");
			response.setFlag(false);
			
			return response;
		}
		//return new ResponseEntity<String>("Mail Send Successfully",HttpStatus.OK);
	}
}
