<template>
  <div>
    <h2 id="page-heading" data-cy="IngredientHeading">
      <span v-text="t$('szakdolgozatApp.ingredient.home.title')" id="ingredient-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('szakdolgozatApp.ingredient.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'IngredientCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-ingredient"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('szakdolgozatApp.ingredient.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && ingredients && ingredients.length === 0">
      <span v-text="t$('szakdolgozatApp.ingredient.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="ingredients && ingredients.length > 0">
      <table class="table table-striped" aria-describedby="ingredients">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.ingredient.name')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.ingredient.amount')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.ingredient.unit')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.ingredient.instruction')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ingredient in ingredients" :key="ingredient.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'IngredientView', params: { ingredientId: ingredient.id } }">{{ ingredient.id }}</router-link>
            </td>
            <td>{{ ingredient.name }}</td>
            <td>{{ ingredient.amount }}</td>
            <td v-text="t$('szakdolgozatApp.Unit.' + ingredient.unit)"></td>
            <td>
              <div v-if="ingredient.instruction">
                <router-link :to="{ name: 'InstructionView', params: { instructionId: ingredient.instruction.id } }">{{
                  ingredient.instruction.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'IngredientView', params: { ingredientId: ingredient.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'IngredientEdit', params: { ingredientId: ingredient.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  @click="prepareRemove(ingredient)"
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
          id="szakdolgozatApp.ingredient.delete.question"
          data-cy="ingredientDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-ingredient-heading" v-text="t$('szakdolgozatApp.ingredient.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-ingredient"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeIngredient()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./ingredient.component.ts"></script>
