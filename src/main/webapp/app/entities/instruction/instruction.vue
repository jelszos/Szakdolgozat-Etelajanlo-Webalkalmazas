<template>
  <div>
    <h2 id="page-heading" data-cy="InstructionHeading">
      <span v-text="t$('szakdolgozatApp.instruction.home.title')" id="instruction-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('szakdolgozatApp.instruction.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'InstructionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-instruction"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('szakdolgozatApp.instruction.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && instructions && instructions.length === 0">
      <span v-text="t$('szakdolgozatApp.instruction.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="instructions && instructions.length > 0">
      <table class="table table-striped" aria-describedby="instructions">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.instruction.title')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.instruction.requiredTime')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.instruction.description')"></span></th>
            <th scope="row"><span v-text="t$('szakdolgozatApp.instruction.recipe')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="instruction in instructions" :key="instruction.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'InstructionView', params: { instructionId: instruction.id } }">{{ instruction.id }}</router-link>
            </td>
            <td>{{ instruction.title }}</td>
            <td>{{ instruction.requiredTime }}</td>
            <td>{{ instruction.description }}</td>
            <td>
              <div v-if="instruction.recipe">
                <router-link :to="{ name: 'RecipeView', params: { recipeId: instruction.recipe.id } }">{{
                  instruction.recipe.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'InstructionView', params: { instructionId: instruction.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'InstructionEdit', params: { instructionId: instruction.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  @click="prepareRemove(instruction)"
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
          id="szakdolgozatApp.instruction.delete.question"
          data-cy="instructionDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-instruction-heading" v-text="t$('szakdolgozatApp.instruction.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-instruction"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeInstruction()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./instruction.component.ts"></script>
