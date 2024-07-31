package com.javadevMZ.configurations;

import com.javadevMZ.dao.User;
import com.javadevMZ.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class InDBUserDetailsManager extends InMemoryUserDetailsManager {

    @Autowired
    private UserRepository userRepository;

    public InDBUserDetailsManager(Collection<UserDetails> users){
        super(users);
    }

    public InDBUserDetailsManager(UserDetails... users){
        super(users);
    }

    public InDBUserDetailsManager(){
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        UserDetails result = org.springframework.security.core.userdetails.User
                .builder()
                .username(username)
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
        return result;
    }
}
