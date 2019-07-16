package com.woodyinho.spring.controller;

import com.woodyinho.spring.model.Todo;
import com.woodyinho.spring.service.TodoService;
import com.woodyinho.spring.utility.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/todos")
public class TodoController  extends BaseController {

    @Autowired
    private TodoService todoService;

    @GetMapping(value = {"","/"})
    public ResponseEntity<List<Todo>> getTodos(){

        List<Todo> result =  todoService.findByUser(getCurrentUser().getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable String id){

      Todo result =  todoService.getById(id);
      return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Todo> create(@Valid @RequestBody Todo todo){

       todo.setUserId(getCurrentUser().getId());
       Todo result =  todoService.save(todo);
       return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id){

        todoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
