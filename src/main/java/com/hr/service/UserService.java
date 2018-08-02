package com.hr.service;

import com.hr.model.User;

public interface UserService {
 User findByUsername(String username);
}
