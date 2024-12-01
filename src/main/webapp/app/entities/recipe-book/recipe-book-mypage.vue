<template>
  <div class="container">
    <div class="d-flex justify-content-center flex-column">
      <div class="position-relative text-center my-5">
        <b-img :src="headerBg" class="carousel-header-img" />
        <h1 class="carousel-header mb-0 position-relative" v-text="t$('szakdolgozatApp.recipeBook.mypage.title')"></h1>
      </div>

      <div class="d-flex justify-content-end">
        <button
          @click="toggleNewRecipeBVisible"
          id="jh-create-entity"
          data-cy="entityCreateButton"
          class="btn btn-primary jh-create-entity create-recipe-book"
        >
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="t$('szakdolgozatApp.recipeBook.home.createLabel')"></span>
        </button>
      </div>
      <div class="my-2">
        <b-modal title="Save recipe to..." v-model="newRecipeBFormVisible" hide-footer centered>
          <div id="newRecipeBookForm">
            <create-recipe-book-form
              :add-recipe-book-to-list="addRecipeBookToList"
              :toggle-new-recipe-b-visible="toggleNewRecipeBVisible"
            />
          </div>
        </b-modal>
      </div>
      <div class="recipe-books">
        <recipe-book-card
          :remove-from-recipe-books="removeRecipeBookToList"
          v-for="recipeBook in recipeBooks"
          :key="recipeBook.id"
          :recipe-book="recipeBook"
          :show-options="true"
          :show-user="false"
        />
        <br />
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./recipe-book-mypage.component.ts"></script>

<style>
.recipe-books {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 8px;
  grid-row-gap: 28px;
}
</style>
