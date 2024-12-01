<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="recipeBookRelation">
        <h2 class="jh-entity-heading" data-cy="recipeBookRelationDetailsHeading">
          <span v-text="t$('szakdolgozatApp.recipeBookRelation.detail.title')"></span> {{ recipeBookRelation.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('szakdolgozatApp.recipeBookRelation.recipe')"></span>
          </dt>
          <dd>
            <div v-if="recipeBookRelation.recipe">
              <router-link :to="{ name: 'RecipeView', params: { recipeId: recipeBookRelation.recipe.id } }">{{
                recipeBookRelation.recipe.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('szakdolgozatApp.recipeBookRelation.recipeBook')"></span>
          </dt>
          <dd>
            <div v-if="recipeBookRelation.recipeBook">
              <router-link :to="{ name: 'RecipeBookView', params: { recipeBookId: recipeBookRelation.recipeBook.id } }">{{
                recipeBookRelation.recipeBook.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="recipeBookRelation.id"
          :to="{ name: 'RecipeBookRelationEdit', params: { recipeBookRelationId: recipeBookRelation.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./recipe-book-relation-details.component.ts"></script>
