package com.cursoyt.todosimple.services;

import com.cursoyt.todosimple.models.User;
import com.cursoyt.todosimple.repositories.UserRepository;
import com.cursoyt.todosimple.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServicesImp implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = this.userRepository.findByUsername(username);
        if(Objects.isNull(user))
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);

        return new UserSpringSecurity(user.getUsername(),user.getId(), user.getPassword(), user.getProfiles());
    }
}
