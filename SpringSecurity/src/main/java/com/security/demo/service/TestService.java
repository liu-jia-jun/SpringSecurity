package com.security.demo.service;

import com.security.demo.pojo.User;

/**
 * @author admin
 */
public interface TestService {

   User selectByUsername(String username);

}
