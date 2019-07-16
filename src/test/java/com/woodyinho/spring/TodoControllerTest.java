package com.woodyinho.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodyinho.spring.model.Todo;
import com.woodyinho.spring.service.TodoService;
import com.woodyinho.spring.todos.AbstractTodoTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoControllerTest extends AbstractTodoTest {
    @MockBean
    private TodoService todoService;
        @Test
    public void getAllTodos() throws Exception{
        Todo todo = new Todo("1", "Todo one", "This is my todo one");

        Todo todo2 = new Todo("2", "Todo two", "This is my todo two");

        List<Todo> list = Arrays.asList(todo, todo2);
        given(todoService.findByUser(anyString())).willReturn(list);
        mockMvc.perform(getReq("/api/v1/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].title", equalTo(todo.getTitle())));
    }

    @Test
    public void postTodo() throws Exception{

        Todo todo = new Todo("1", "Todo One", "This Todo One");
        ObjectMapper mapper = new ObjectMapper();


        given(todoService.save(Mockito.any(Todo.class))).willReturn(todo);
        mockMvc.perform(postReq("/api/v1/todos/create").contentType(MediaType.APPLICATION_JSON)
                 .content(mapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", equalTo(todo.getTitle())));

    }
}
