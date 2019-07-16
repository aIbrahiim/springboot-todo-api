package com.woodyinho.spring;

import com.woodyinho.spring.error.NotFoundException;
import com.woodyinho.spring.model.Todo;
import com.woodyinho.spring.repository.TodoRepository;
import com.woodyinho.spring.service.TodoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class) //alias for junit
@SpringBootTest

public class TodoServiceTest {

    @MockBean
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @TestConfiguration
    static class TodoServiceContextConfiguration{
        @Bean
        public TodoService todoService(){
            return new TodoService();
        }

    }

    @Test
    public void findAll(){
        Todo todo = new Todo("1", "Todo one", "This is my todo one");

        Todo todo2 = new Todo("2", "Todo two", "This is my todo two");

        List<Todo> list = Arrays.asList(todo, todo2);
        //when todoRepo.findAll is called return the list data
        given(todoRepository.findAll()).willReturn(list);

        assertThat(todoService.findAll())
                .hasSize(2)
                .contains(todo, todo2);
    }

    @Test
    public void getById(){
        Todo todo = new Todo("1", "One", "This is one todo");
        given(todoRepository.findById(anyString())).willReturn(Optional.ofNullable(todo));

        Todo result = todoService.getById("1");
        assertThat(result.getTitle()).containsIgnoringCase("One");

    }

    @Test(expected = NotFoundException.class)
    public void invalidId(){
        given(todoRepository.findById(anyString())).willReturn(Optional.empty());
        todoService.getById("1");
    }


}
