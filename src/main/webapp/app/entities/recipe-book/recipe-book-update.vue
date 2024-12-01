<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="szakdolgozatApp.recipeBook.home.createOrEditLabel"
          data-cy="RecipeBookCreateUpdateHeading"
          v-text="t$('szakdolgozatApp.recipeBook.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="recipeBook.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="recipeBook.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.recipeBook.title')" for="recipe-book-title"></label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="recipe-book-title"
              data-cy="title"
              :class="{ valid: !v$.title.$invalid, invalid: v$.title.$invalid }"
              v-model="v$.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.recipeBook.theme')" for="recipe-book-theme"></label>
            <input
              type="text"
              class="form-control"
              name="theme"
              id="recipe-book-theme"
              data-cy="theme"
              :class="{ valid: !v$.theme.$invalid, invalid: v$.theme.$invalid }"
              v-model="v$.theme.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.recipeBook.description')" for="recipe-book-description"></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="recipe-book-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.recipeBook.tags')" for="recipe-book-tags"></label>
            <input
              type="text"
              class="form-control"
              name="tags"
              id="recipe-book-tags"
              data-cy="tags"
              :class="{ valid: !v$.tags.$invalid, invalid: v$.tags.$invalid }"
              v-model="v$.tags.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.recipeBook.createdDate')" for="recipe-book-createdDate"></label>
            <div class="d-flex">
              <input
                id="recipe-book-createdDate"
                data-cy="createdDate"
                type="datetime-local"
                class="form-control"
                name="createdDate"
                :class="{ valid: !v$.createdDate.$invalid, invalid: v$.createdDate.$invalid }"
                :value="convertDateTimeFromServer(v$.createdDate.$model)"
                @change="updateZonedDateTimeField('createdDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.recipeBook.user')" for="recipe-book-user"></label>
            <select class="form-control" id="recipe-book-user" data-cy="user" name="user" v-model="recipeBook.user">
              <option :value="null"></option>
              <option
                :value="recipeBook.user && userOption.id === recipeBook.user.id ? recipeBook.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./recipe-book-update.component.ts"></script>
