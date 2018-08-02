package com.hr.mapper;

import org.apache.ibatis.annotations.Param;

import com.hr.model.User;

public interface UserMapper {
	User findByUsername(@Param("username") String username);
}
