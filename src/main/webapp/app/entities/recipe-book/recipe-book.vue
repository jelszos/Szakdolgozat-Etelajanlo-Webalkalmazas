<template>
  <div>
    <h2 id="page-heading" data-cy="RecipeBookHeading">
      <span v-text="t$('szakdolgozatApp.recipeBook.home.title')" id="recipe-book-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('szakdolgozatApp.recipeBook.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RecipeBookCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-recipe-book"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('szakdolgozatApp.recipeBook.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && recipeBooks && recipeBooks.length === 0">
      <span v-text="t$('szakdolgozatApp.recipeBook.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="recipeBooks && recipeBooks.length > 0">
      <table class="table table-striped" aria-describedby="recipeBooks">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipeBook.title')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipeBook.theme')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipeBook.description')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipeBook.tags')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipeBook.createdDate')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.recipeBook.user')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="recipeBook in recipeBooks" :key="recipeBook.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RecipeBookView', params: { recipeBookId: recipeBook.id } }">{{ recipeBook.id }}</router-link>
            </td>
            <td>{{ recipeBook.title }}</td>
            <td>{{ recipeBook.theme }}</td>
            <td>{{ recipeBook.description }}</td>
            <td>{{ recipeBook.tags }}</td>
            <td>{{ formatDateShort(recipeBook.createdDate) || '' }}</td>
            <td>
              {{ recipeBook.user ? recipeBook.user.id : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RecipeBookView', params: { recipeBookId: recipeBook.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'RecipeBookEdit', params: { recipeBookId: recipeBook.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  @click="prepareRemove(recipeBook)"
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
          id="szakdolgozatApp.recipeBook.delete.question"
          data-cy="recipeBookDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-recipeBook-heading" v-text="t$('szakdolgozatApp.recipeBook.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-recipeBook"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeRecipeBook()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./recipe-book.component.ts"></script>
