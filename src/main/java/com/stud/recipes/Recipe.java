package com.stud.recipes;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "description")
    @NotBlank
    private String description;

    @ElementCollection
    @Size(min = 1)
    @Column
    @CollectionTable(name = "ingredients", joinColumns = @JoinColumn(name = "id", nullable=false))
    private List<String> ingredients;

    @ElementCollection
    @Size(min = 1)
    @Column
    @CollectionTable(name = "directions", joinColumns = @JoinColumn(name = "id", nullable=false))
    private List<String> directions;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;


    public Recipe() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(getName(), recipe.getName()) && Objects.equals(getDescription(), recipe.getDescription()) && Objects.equals(getIngredients(), recipe.getIngredients()) && Objects.equals(getDirections(), recipe.getDirections()) && Objects.equals(getId(), recipe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getIngredients(), getDirections(), getId());
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                ", directions=" + directions +
                ", id=" + id +
                '}';
    }
}
