package com.szakdologzat.repiceapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Instruction.
 */
@Entity
@Table(name = "instruction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Instruction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "required_time")
    private Integer requiredTime;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "instruction", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "instruction" }, allowSetters = true)
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "instructions", "ratings", "recipeBookRelations", "favoriteRelations", "user" }, allowSetters = true)
    private Recipe recipe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Instruction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Instruction title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRequiredTime() {
        return this.requiredTime;
    }

    public Instruction requiredTime(Integer requiredTime) {
        this.setRequiredTime(requiredTime);
        return this;
    }

    public void setRequiredTime(Integer requiredTime) {
        this.requiredTime = requiredTime;
    }

    public String getDescription() {
        return this.description;
    }

    public Instruction description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        if (this.ingredients != null) {
            this.ingredients.forEach(i -> i.setInstruction(null));
        }
        if (ingredients != null) {
            ingredients.forEach(i -> i.setInstruction(this));
        }
        this.ingredients = ingredients;
    }

    public Instruction ingredients(Set<Ingredient> ingredients) {
        this.setIngredients(ingredients);
        return this;
    }

    public Instruction addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setInstruction(this);
        return this;
    }

    public Instruction removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
        ingredient.setInstruction(null);
        return this;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Instruction recipe(Recipe recipe) {
        this.setRecipe(recipe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Instruction)) {
            return false;
        }
        return getId() != null && getId().equals(((Instruction) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Instruction{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", requiredTime=" + getRequiredTime() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
