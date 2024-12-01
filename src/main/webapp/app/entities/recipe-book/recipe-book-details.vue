<template>
  <div class="row justify-content-center">
    <div class="container">
      <div class="d-flex justify-content-between">
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="ownByUser()"
          :to="{ name: 'RecipeBookEdit', params: { recipeBookId: recipeBook.id } }"
          custom
          v-slot="{ navigate }"
        >
          <div class="d-flex align-items-center">
            <span class="mr-2">
              {{ t$(recipeBook.isPrivate ? 'szakdolgozatApp.recipeBook.mypage.private' : 'szakdolgozatApp.recipeBook.mypage.public') }}
            </span>
            <b-form-checkbox class="mr-4" switch :checked="recipeBook.isPrivate" :onchange="changeRBStatus"></b-form-checkbox>

            <button @click="navigate" class="btn btn-primary">
              <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
            </button>
          </div>
        </router-link>
      </div>
      <div class="d-flex justify-content-between align-items-center mt-4">
        <h1>
          {{ recipeBook.title }}
        </h1>
        <div>
          <b-tooltip target="1">Theme</b-tooltip>
          <b-badge id="1">{{ recipeBook.theme }}</b-badge>
        </div>
      </div>
      <div>
        <div>
          {{ recipeBook.description }}
        </div>
        <div class="container my-4">
          <div class="row justify-content-center">
            <div>
              <span v-for="(tag, index) in recipeBook.tags?.split(',')" :key="index">
                <b-badge variant="primary" class="hashtag-badge">#{{ tag }}</b-badge>
              </span>
            </div>
          </div>
        </div>

        <div v-if="isOwnedByUser">
          <recipe-book-recipe-card :recipes="recipes" :delete-from-recipe-book="deleteFromRecipeBook"></recipe-book-recipe-card>
        </div>
        <div v-else>
          <recipe-book-recipe-card :recipes="recipes"></recipe-book-recipe-card>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" src="./recipe-book-details.component.ts"></script>

<style scoped>
/* Custom styling for badges to appear as hashtag chips */
.hashtag-badge {
  margin: 0.2em;
  padding: 0.5em 0.75em;
  font-size: 0.85rem;
  border-radius: 15px;
}
</style>
