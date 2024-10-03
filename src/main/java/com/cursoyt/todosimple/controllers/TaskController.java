package com.cursoyt.todosimple.controllers;


import com.cursoyt.todosimple.models.Task;
import com.cursoyt.todosimple.services.TaskServices;
import com.cursoyt.todosimple.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskServices taskServices;

    @Autowired
    private UserServices userServices;


    @GetMapping("/user/{user_id}")
    public  ResponseEntity<List<Task>> findByUserId(@PathVariable Long user_id){
        this.userServices.findById(user_id);
        List<Task> OBJ = this.taskServices.findAllById(user_id);
        return  ResponseEntity.ok().body(OBJ);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id){
        Task obj = this.taskServices.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj){
        this.taskServices.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public  ResponseEntity<Void> update(@Valid @RequestBody Task obj, @PathVariable Long id ){
        obj.setId(id);
        this.taskServices.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public  ResponseEntity<Void> delete(@PathVariable Long id) {
        this.taskServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
