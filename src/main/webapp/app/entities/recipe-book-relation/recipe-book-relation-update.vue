<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="szakdolgozatApp.recipeBookRelation.home.createOrEditLabel"
          data-cy="RecipeBookRelationCreateUpdateHeading"
          v-text="t$('szakdolgozatApp.recipeBookRelation.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="recipeBookRelation.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="recipeBookRelation.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('szakdolgozatApp.recipeBookRelation.recipe')"
              for="recipe-book-relation-recipe"
            ></label>
            <select
              class="form-control"
              id="recipe-book-relation-recipe"
              data-cy="recipe"
              name="recipe"
              v-model="recipeBookRelation.recipe"
            >
              <option :value="null"></option>
              <option
                :value="
                  recipeBookRelation.recipe && recipeOption.id === recipeBookRelation.recipe.id ? recipeBookRelation.recipe : recipeOption
                "
                v-for="recipeOption in recipes"
                :key="recipeOption.id"
              >
                {{ recipeOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('szakdolgozatApp.recipeBookRelation.recipeBook')"
              for="recipe-book-relation-recipeBook"
            ></label>
            <select
              class="form-control"
              id="recipe-book-relation-recipeBook"
              data-cy="recipeBook"
              name="recipeBook"
              v-model="recipeBookRelation.recipeBook"
            >
              <option :value="null"></option>
              <option
                :value="
                  recipeBookRelation.recipeBook && recipeBookOption.id === recipeBookRelation.recipeBook.id
                    ? recipeBookRelation.recipeBook
                    : recipeBookOption
                "
                v-for="recipeBookOption in recipeBooks"
                :key="recipeBookOption.id"
              >
                {{ recipeBookOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./recipe-book-relation-update.component.ts"></script>
