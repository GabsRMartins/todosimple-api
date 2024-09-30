package com.cursoyt.todosimple.repositories;

import com.cursoyt.todosimple.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {

}
