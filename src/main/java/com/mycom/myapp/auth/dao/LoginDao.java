package com.mycom.myapp.auth.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.user.dto.UserDto;

@Mapper
public interface LoginDao {
	UserDto login(String userEmail);
	void register(UserDto userDto);
	void updateProfile(UserDto userDto);
}
