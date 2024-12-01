<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="favoriteRelation">
        <h2 class="jh-entity-heading" data-cy="favoriteRelationDetailsHeading">
          <span v-text="t$('szakdolgozatApp.favoriteRelation.detail.title')"></span> {{ favoriteRelation.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('szakdolgozatApp.favoriteRelation.user')"></span>
          </dt>
          <dd>
            {{ favoriteRelation.user ? favoriteRelation.user.id : '' }}
          </dd>
          <dt>
            <span v-text="t$('szakdolgozatApp.favoriteRelation.recipe')"></span>
          </dt>
          <dd>
            <div v-if="favoriteRelation.recipe">
              <router-link :to="{ name: 'RecipeView', params: { recipeId: favoriteRelation.recipe.id } }">{{
                favoriteRelation.recipe.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="favoriteRelation.id"
          :to="{ name: 'FavoriteRelationEdit', params: { favoriteRelationId: favoriteRelation.id } }"
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

<script lang="ts" src="./favorite-relation-details.component.ts"></script>
