package com.patriotenergygroup.peuploadservice.service.impl;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.patriotenergygroup.peuploadservice.model.Role;
import com.patriotenergygroup.peuploadservice.model.User;
import com.patriotenergygroup.peuploadservice.repository.RoleRepository;
import com.patriotenergygroup.peuploadservice.repository.UserRepository;
import com.patriotenergygroup.peuploadservice.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;	
	
	@Override
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        
        HashSet<Role> roles = new HashSet<>();
        // assign a default role (apiuser)
        if (user.getRoles().size() == 0) {
            Role userRole = roleRepository.findByRole("API_USER");
        	roles.add(userRole);
        }
        else {
        	user.getRoles().forEach(r -> {
        		Role userRole = roleRepository.findOne(r.getId());
        		if (userRole != null) {
        			roles.add(userRole);
        		}
        	});
        }
        user.setRoles(roles);
        
		userRepository.save(user);
	}

}
