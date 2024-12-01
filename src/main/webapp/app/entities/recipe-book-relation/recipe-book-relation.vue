<template>
  <div>
    <h2 id="page-heading" data-cy="RecipeBookRelationHeading">
      <span v-text="t$('szakdolgozatApp.recipeBookRelation.home.title')" id="recipe-book-relation-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('szakdolgozatApp.recipeBookRelation.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RecipeBookRelationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-recipe-book-relation"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('szakdolgozatApp.recipeBookRelation.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && recipeBookRelations && recipeBookRelations.length === 0">
      <span v-text="t$('szakdolgozatApp.recipeBookRelation.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="recipeBookRelations && recipeBookRelations.length > 0">
      <table class="table table-striped" aria-describedby="recipeBookRelations">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipeBookRelation.recipe')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipeBookRelation.recipeBook')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="recipeBookRelation in recipeBookRelations" :key="recipeBookRelation.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RecipeBookRelationView', params: { recipeBookRelationId: recipeBookRelation.id } }">{{
                recipeBookRelation.id
              }}</router-link>
            </td>
            <td>
              <div v-if="recipeBookRelation.recipe">
                <router-link :to="{ name: 'RecipeView', params: { recipeId: recipeBookRelation.recipe.id } }">{{
                  recipeBookRelation.recipe.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="recipeBookRelation.recipeBook">
                <router-link :to="{ name: 'RecipeBookView', params: { recipeBookId: recipeBookRelation.recipeBook.id } }">{{
                  recipeBookRelation.recipeBook.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RecipeBookRelationView', params: { recipeBookRelationId: recipeBookRelation.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'RecipeBookRelationEdit', params: { recipeBookRelationId: recipeBookRelation.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  @click="prepareRemove(recipeBookRelation)"
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
        <span
          id="szakdolgozatApp.recipeBookRelation.delete.question"
          data-cy="recipeBookRelationDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-recipeBookRelation-heading"
          v-text="t$('szakdolgozatApp.recipeBookRelation.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-recipeBookRelation"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeRecipeBookRelation()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./recipe-book-relation.component.ts"></script>
