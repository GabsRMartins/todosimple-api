package com.cursoyt.todosimple.services;


import com.cursoyt.todosimple.models.User;
import com.cursoyt.todosimple.models.enums.ProfileEnum;
import com.cursoyt.todosimple.repositories.TaskRepository;
import com.cursoyt.todosimple.repositories.UserRepository;
import com.cursoyt.todosimple.services.exceptions.DataBindingViolationException;
import com.cursoyt.todosimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserServices {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TaskRepository taskRepository;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow( () -> new ObjectNotFoundException(
                "Usuário não encontrado!Id: " + id + "Tipo: " + User.class.getName()
        ));
    }

    @Transactional  //Usar para updates ou inserção no banco
    public User create(User obj){
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public  User update(User obj){
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        return this.userRepository.save(newObj);
    }


    public void delete(Long id){
        findById(id);
        try {
            this.userRepository.deleteById(id);
        }
        catch(Exception e){
            throw  new DataBindingViolationException("Não é possivel excluir pois há entidades relacionadas");
        }
    }
}
