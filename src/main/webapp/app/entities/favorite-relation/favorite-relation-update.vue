<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="szakdolgozatApp.favoriteRelation.home.createOrEditLabel"
          data-cy="FavoriteRelationCreateUpdateHeading"
          v-text="t$('szakdolgozatApp.favoriteRelation.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="favoriteRelation.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="favoriteRelation.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.favoriteRelation.user')" for="favorite-relation-user"></label>
            <select class="form-control" id="favorite-relation-user" data-cy="user" name="user" v-model="favoriteRelation.user">
              <option :value="null"></option>
              <option
                :value="favoriteRelation.user && userOption.id === favoriteRelation.user.id ? favoriteRelation.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.favoriteRelation.recipe')" for="favorite-relation-recipe"></label>
            <select class="form-control" id="favorite-relation-recipe" data-cy="recipe" name="recipe" v-model="favoriteRelation.recipe">
              <option :value="null"></option>
              <option
                :value="favoriteRelation.recipe && recipeOption.id === favoriteRelation.recipe.id ? favoriteRelation.recipe : recipeOption"
                v-for="recipeOption in recipes"
                :key="recipeOption.id"
              >
                {{ recipeOption.id }}
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
<script lang="ts" src="./favorite-relation-update.component.ts"></script>
