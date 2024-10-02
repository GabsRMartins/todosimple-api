package com.cursoyt.todosimple.controllers;


import com.cursoyt.todosimple.models.User;
import com.cursoyt.todosimple.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){

        User obj = this.userServices.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @Validated(User.CreateUser.class)
    public ResponseEntity<Void> create(@Valid @RequestBody User obj){
            this.userServices.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated(User.UpdateUser.class)
    public ResponseEntity<Void> update(@Valid @RequestBody User obj, @PathVariable Long id){
        obj.setId(id);
        obj= this.userServices.update(obj);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable Long id){
        this.userServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
