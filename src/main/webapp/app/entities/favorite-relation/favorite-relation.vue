<template>
  <div>
    <h2 id="page-heading" data-cy="FavoriteRelationHeading">
      <span v-text="t$('szakdolgozatApp.favoriteRelation.home.title')" id="favorite-relation-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('szakdolgozatApp.favoriteRelation.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'FavoriteRelationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-favorite-relation"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('szakdolgozatApp.favoriteRelation.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && favoriteRelations && favoriteRelations.length === 0">
      <span v-text="t$('szakdolgozatApp.favoriteRelation.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="favoriteRelations && favoriteRelations.length > 0">
      <table class="table table-striped" aria-describedby="favoriteRelations">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.favoriteRelation.user')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.favoriteRelation.recipe')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="favoriteRelation in favoriteRelations" :key="favoriteRelation.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FavoriteRelationView', params: { favoriteRelationId: favoriteRelation.id } }">{{
                favoriteRelation.id
              }}</router-link>
            </td>
            <td>
              {{ favoriteRelation.user ? favoriteRelation.user.id : '' }}
            </td>
            <td>
              <div v-if="favoriteRelation.recipe">
                <router-link :to="{ name: 'RecipeView', params: { recipeId: favoriteRelation.recipe.id } }">{{
                  favoriteRelation.recipe.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'FavoriteRelationView', params: { favoriteRelationId: favoriteRelation.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'FavoriteRelationEdit', params: { favoriteRelationId: favoriteRelation.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  @click="prepareRemove(favoriteRelation)"
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
          id="szakdolgozatApp.favoriteRelation.delete.question"
          data-cy="favoriteRelationDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-favoriteRelation-heading" v-text="t$('szakdolgozatApp.favoriteRelation.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-favoriteRelation"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeFavoriteRelation()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./favorite-relation.component.ts"></script>
