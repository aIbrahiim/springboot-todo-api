package com.woodyinho.spring.todos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.woodyinho.spring.model.AppUser;
import com.woodyinho.spring.security.SignInRequest;
import com.woodyinho.spring.service.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.BDDMockito.*;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class AbstractTodoTest {

    private  final String AUTH_HEADER = "Authorization";
    private final String USERNAME_TEST = "woodyinho";
    private final String PASSWORD_TEST = "1234";

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Before
    public void setup(){
        AppUser appUser = new AppUser(USERNAME_TEST,new BCryptPasswordEncoder().encode(PASSWORD_TEST), "woody");
        appUser.setId("111111");

        given(userService.loadUserByUsername(appUser.getUsername())).willReturn(appUser);
    }
    //MockMvc returns result actions
    public ResultActions login(String username, String password) throws Exception{
        SignInRequest signInRequest = new SignInRequest(username, password);
        return mockMvc.perform(post("/api/v1/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(signInRequest)));
    }

    private String getHeader(){
        try{
            MvcResult result = login(USERNAME_TEST, PASSWORD_TEST).andReturn();
            String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
            String header = String.format("Bearer %s", token);
            return header;
        }catch (Exception ex){
            return null;
        }
    }

    public MockHttpServletRequestBuilder getReq(String url){
        return get(url).header(AUTH_HEADER, getHeader());
    }

    public MockHttpServletRequestBuilder postReq(String url){
        return post(url).header(AUTH_HEADER, getHeader());
    }


}
