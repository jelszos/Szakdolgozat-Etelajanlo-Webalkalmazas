package com.szakdologzat.repiceapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

/**
 * A RecipeBook.
 */
@Entity
@Table(name = "recipe_book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecipeBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "theme")
    private String theme;

    @Column(name = "description")
    private String description;

    @Column(name = "tags")
    private String tags;

    @Column(name = "created_date")
    @CreationTimestamp
    private ZonedDateTime createdDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipeBook", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "recipe", "recipeBook" }, allowSetters = true)
    private Set<RecipeBookRelation> recipeBookRelations = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private boolean isPrivate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Long getId() {
        return this.id;
    }

    public RecipeBook id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public RecipeBook title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return this.theme;
    }

    public RecipeBook theme(String theme) {
        this.setTheme(theme);
        return this;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return this.description;
    }

    public RecipeBook description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return this.tags;
    }

    public RecipeBook tags(String tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public RecipeBook createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Set<RecipeBookRelation> getRecipeBookRelations() {
        return this.recipeBookRelations;
    }

    public void setRecipeBookRelations(Set<RecipeBookRelation> recipeBookRelations) {
        if (this.recipeBookRelations != null) {
            this.recipeBookRelations.forEach(i -> i.setRecipeBook(null));
        }
        if (recipeBookRelations != null) {
            recipeBookRelations.forEach(i -> i.setRecipeBook(this));
        }
        this.recipeBookRelations = recipeBookRelations;
    }

    public RecipeBook recipeBookRelations(Set<RecipeBookRelation> recipeBookRelations) {
        this.setRecipeBookRelations(recipeBookRelations);
        return this;
    }

    public RecipeBook addRecipeBookRelation(RecipeBookRelation recipeBookRelation) {
        this.recipeBookRelations.add(recipeBookRelation);
        recipeBookRelation.setRecipeBook(this);
        return this;
    }

    public RecipeBook removeRecipeBookRelation(RecipeBookRelation recipeBookRelation) {
        this.recipeBookRelations.remove(recipeBookRelation);
        recipeBookRelation.setRecipeBook(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RecipeBook user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeBook)) {
            return false;
        }
        return getId() != null && getId().equals(((RecipeBook) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeBook{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", theme='" + getTheme() + "'" +
            ", description='" + getDescription() + "'" +
            ", tags='" + getTags() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
