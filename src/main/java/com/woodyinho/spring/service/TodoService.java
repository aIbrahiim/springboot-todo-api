package com.woodyinho.spring.service;

import com.woodyinho.spring.error.ConflictException;
import com.woodyinho.spring.error.NotFoundException;
import com.woodyinho.spring.model.Todo;
import com.woodyinho.spring.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepsitory;

    public List<Todo> findAll() {
        return todoRepsitory.findAll();
    }

    public List<Todo> findByUser(String userId){
        return todoRepsitory.findByUserId(userId);
    }

    public Todo getById(String id) {
        try {
            return todoRepsitory.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new NotFoundException(String.format("No Record found with id [%s] in the database", id));
        }
    }

    public Todo save(Todo todo) {
        if (todoRepsitory.findByTitle(todo.getTitle()) != null) {
            throw new ConflictException("Another record with the same title exists");
        }
        return todoRepsitory.save(todo);
    }

    public void delete(String id){
         todoRepsitory.deleteById(id);
    }
}
