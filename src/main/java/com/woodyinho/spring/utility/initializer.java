package com.woodyinho.spring.utility;

import com.woodyinho.spring.model.AppUser;
import com.woodyinho.spring.service.UserService;
import org.apache.juli.logging.LogFactory;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class initializer implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(initializer.class);
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if(this.userService.findAll().isEmpty()){
            logger.info("NO users found.. creating new users");
            AppUser appUser1 = new AppUser("azz20", "1234", "Azz");
            userService.saveUser(appUser1);
        }
    }
}
