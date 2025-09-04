package com.wipro.komal.services.auth;

import com.wipro.komal.dto.SignupRequest;
import com.wipro.komal.dto.UserDto;

public interface AuthService 
{

	UserDto signupUser(SignupRequest signupRequest);
	boolean hasUserWithEmail(String email);
}
