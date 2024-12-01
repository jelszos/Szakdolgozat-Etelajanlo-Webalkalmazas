<template>
  <div>
    <h2 id="page-heading" data-cy="RecipeHeading">
      <span v-text="t$('szakdolgozatApp.recipe.home.title')" id="recipe-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('szakdolgozatApp.recipe.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RecipeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-recipe"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('szakdolgozatApp.recipe.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && recipes && recipes.length === 0">
      <span v-text="t$('szakdolgozatApp.recipe.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="recipes && recipes.length > 0">
      <table class="table table-striped" aria-describedby="recipes">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipe.title')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipe.description')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipe.ingredientNames')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipe.foodCategory')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipe.foodType')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipe.imageUrl')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipe.createdDate')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipe.user')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="recipe in recipes" :key="recipe.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RecipeView', params: { recipeId: recipe.id } }">{{ recipe.id }}</router-link>
            </td>
            <td>{{ recipe.title }}</td>
            <td>{{ recipe.description }}</td>
            <td>{{ recipe.ingredientNames }}</td>
            <td v-text="t$('szakdolgozatApp.FoodCategory.' + recipe.foodCategory)"></td>
            <td v-text="t$('szakdolgozatApp.FoodType.' + recipe.foodType)"></td>
            <td>{{ recipe.imageUrl }}</td>
            <td>{{ formatDateShort(recipe.createdDate) || '' }}</td>
            <td>
              {{ recipe.user ? recipe.user.id : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RecipeView', params: { recipeId: recipe.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'RecipeEdit', params: { recipeId: recipe.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  @click="prepareRemove(recipe)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="szakdolgozatApp.recipe.delete.question" data-cy="recipeDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-recipe-heading" v-text="t$('szakdolgozatApp.recipe.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-recipe"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeRecipe()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./recipe.component.ts"></script>
