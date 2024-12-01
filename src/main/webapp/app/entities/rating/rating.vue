<template>
  <div>
    <h2 id="page-heading" data-cy="RatingHeading">
      <span v-text="t$('szakdolgozatApp.rating.home.title')" id="rating-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('szakdolgozatApp.rating.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RatingCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rating"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('szakdolgozatApp.rating.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && ratings && ratings.length === 0">
      <span v-text="t$('szakdolgozatApp.rating.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="ratings && ratings.length > 0">
      <table class="table table-striped" aria-describedby="ratings">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.rating.rate')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.rating.title')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.rating.description')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.rating.imageUrl')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.rating.createdDate')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.rating.user')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.rating.recipe')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rating in ratings" :key="rating.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RatingView', params: { ratingId: rating.id } }">{{ rating.id }}</router-link>
            </td>
            <td>{{ rating.rate }}</td>
            <td>{{ rating.title }}</td>
            <td>{{ rating.description }}</td>
            <td>{{ rating.imageUrl }}</td>
            <td>{{ formatDateShort(rating.createdDate) || '' }}</td>
            <td>
              {{ rating.user ? rating.user.id : '' }}
            </td>
            <td>
              <div v-if="rating.recipe">
                <router-link :to="{ name: 'RecipeView', params: { recipeId: rating.recipe.id } }">{{ rating.recipe.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RatingView', params: { ratingId: rating.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'RatingEdit', params: { ratingId: rating.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  @click="prepareRemove(rating)"
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
        <span id="szakdolgozatApp.rating.delete.question" data-cy="ratingDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-rating-heading" v-text="t$('szakdolgozatApp.rating.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-rating"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeRating()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./rating.component.ts"></script>
