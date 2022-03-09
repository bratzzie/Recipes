package com.stud.recipes.user;

import com.stud.recipes.recipe.Recipe;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Component
@Table(name = "users")
@Entity
public class User {
    @NotBlank
    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    private String role = "USER";

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private Set<Recipe> usersRecipes;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    public User(String email, String password, Set<Recipe> usersRecipes, Long id) {
        this.email = email;
        this.password = password;
        this.role = "USER";
        this.usersRecipes = usersRecipes;
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole() {
        this.role = "USER";
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Set<Recipe> getUsersRecipes() {
        return usersRecipes;
    }

    public void setUsersRecipes(Set<Recipe> usersRecipes) {
        this.usersRecipes = usersRecipes;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", id=" + id +
                '}';
    }
}
