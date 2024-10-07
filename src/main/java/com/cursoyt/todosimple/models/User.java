package com.cursoyt.todosimple.models;



import com.cursoyt.todosimple.models.enums.ProfileEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table (name = User.TABLE_NAME )
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user")
    private List<Task> tasks = new ArrayList<Task>();

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile")
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();


    public Set<ProfileEnum> getProfiles(){
        return this.profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profileEnum){
        this.profiles.add(profileEnum.getCode());

    }
}
