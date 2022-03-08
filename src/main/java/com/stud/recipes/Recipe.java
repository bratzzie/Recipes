package com.stud.recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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

    @Column(name = "category")
    @NotBlank
    private String category;

    @Column(name = "date")
    @UpdateTimestamp
    private LocalDateTime date;

    @ElementCollection
    @NotEmpty
    @Size(min = 1)
    @Column
    @CollectionTable(name = "ingredients", joinColumns = @JoinColumn(name = "id", nullable=false))
    private List<String> ingredients;

    @ElementCollection
    @NotEmpty
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

    public Recipe(String name, String description, String category, LocalDateTime date, List<String> ingredients, List<String> directions, Long id) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.date =  date;
        this.ingredients = ingredients;
        this.directions = directions;
        this.id = id;
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

    @JsonIgnore
    @JsonProperty(value = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(getName(), recipe.getName()) && Objects.equals(getDescription(), recipe.getDescription()) && Objects.equals(getCategory(), recipe.getCategory()) && Objects.equals(getDate(), recipe.getDate()) && Objects.equals(getIngredients(), recipe.getIngredients()) && Objects.equals(getDirections(), recipe.getDirections()) && Objects.equals(getId(), recipe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getCategory(), getDate(), getIngredients(), getDirections(), getId());
    }
}
