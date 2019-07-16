package com.woodyinho.spring.service;

import com.woodyinho.spring.error.NotFoundException;
import com.woodyinho.spring.repository.UserRepository;
import com.woodyinho.spring.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Bean
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       final AppUser appUser =  this.userRepository.findByUsername(username);
       if(appUser == null)
           throw new NotFoundException("Username not found");
       return appUser;
    }

    public AppUser saveUser(AppUser appUser){
        appUser.setPassword(passwordEncoder().encode(appUser.getPassword()));
        return this.userRepository.save(appUser);
    }

    public List<AppUser> findAll(){
        return this.userRepository.findAll();
    }
}
