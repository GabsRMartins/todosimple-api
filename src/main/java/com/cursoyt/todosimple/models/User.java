package com.cursoyt.todosimple.models;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = User.TABLE_NAME )
public class User {
    public static final String TABLE_NAME = "user";

    public  interface  CreateUser{}

    public  interface  UpdateUser{}


    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    @Column (name = "id",unique = true)
    private  Long id;

    @Column  (name = "username",length = 100, unique = true, nullable = false )
    @NotNull (groups = CreateUser.class)
    @NotEmpty (groups = CreateUser.class)
    @Size ( groups = CreateUser.class,min = 2 , max = 100)
    private  String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column (name = "password" , length = 60, nullable = false)
    @NotBlank (groups = {CreateUser.class , UpdateUser.class})
    @Size ( groups = {CreateUser.class , UpdateUser.class} ,min = 8 , max = 60)
    private  String password;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks = new ArrayList<Task>();


    public User() {
    }



    public User(String username, Long id, String password) {
        this.username = username;
        this.id = id;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(groups = CreateUser.class) @NotEmpty(groups = CreateUser.class) @Size(groups = CreateUser.class, min = 2, max = 100) String getUsername() {
        return username;
    }

    public void setUsername(@NotNull(groups = CreateUser.class) @NotEmpty(groups = CreateUser.class) @Size(groups = CreateUser.class, min = 2, max = 100) String username) {
        this.username = username;
    }

    public @NotBlank(groups = {CreateUser.class, UpdateUser.class}) @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(groups = {CreateUser.class, UpdateUser.class}) @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60) String password) {
        this.password = password;
    }

    @JsonIgnore
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        final  int prime = 31 ;
        int result = 1 ;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
