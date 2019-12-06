package com.jhallat.utilities.todolistsserver.controllers;

import com.jhallat.listdb.*;
import com.jhallat.utilities.todolistsserver.model.ToDoItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/todo-list")
public class TodoListController {

    private Topic todoTopic;

    public TodoListController(ListDBDataSource dataSource) throws ListDBException {
        Directory directory = dataSource.getDirectoryFromPath("todo-list");
        todoTopic = directory.getTopic("todo");
    }

    @GetMapping()
    public ResponseEntity<List<Record>> getItems() throws ListDBException {
        List<Record> items = todoTopic.list();
        return ResponseEntity.ok(items);
    }

    @PostMapping()
    public ResponseEntity<String> createItem(UriComponentsBuilder uriBuilder, @RequestBody ToDoItem toDoItem) throws ListDBException {
        String id = todoTopic.add(toDoItem.getContent());
        UriComponents uriComponents = uriBuilder.path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

}
