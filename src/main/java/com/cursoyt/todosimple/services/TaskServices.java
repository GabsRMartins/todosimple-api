package com.cursoyt.todosimple.services;

import com.cursoyt.todosimple.models.Task;
import com.cursoyt.todosimple.models.User;
import com.cursoyt.todosimple.repositories.TaskRepository;
import com.cursoyt.todosimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServices {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserServices userServices;

    public Task findById(Long id){
        Optional<Task> task =  this.taskRepository.findById(id);
        return task.orElseThrow( () -> new ObjectNotFoundException(
                "Tarefa não encontrada!Id: " + id + "Tipo: " + Task.class.getName()
        ));
    }

    public List<Task> findAllById(Long userId){
        List<Task> tasks = this.taskRepository.findByUser_Id(userId);
        return tasks;
    }

    @Transactional
    public  Task create(Task obj){
        User user = this.userServices.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public  Task update(Task obj){
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return  this.taskRepository.save(newObj);
    }

    public void  delete(Long id){
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        }
        catch (Exception e){
            throw new RuntimeException("Não é possivel excluir pois há entidades relacionadas");
        }
    }
}
