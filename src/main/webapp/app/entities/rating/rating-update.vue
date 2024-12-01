<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="szakdolgozatApp.rating.home.createOrEditLabel"
          data-cy="RatingCreateUpdateHeading"
          v-text="t$('szakdolgozatApp.rating.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="rating.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="rating.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.rating.rate')" for="rating-rate"></label>
            <input
              type="number"
              class="form-control"
              name="rate"
              id="rating-rate"
              data-cy="rate"
              :class="{ valid: !v$.rate.$invalid, invalid: v$.rate.$invalid }"
              v-model.number="v$.rate.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.rating.title')" for="rating-title"></label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="rating-title"
              data-cy="title"
              :class="{ valid: !v$.title.$invalid, invalid: v$.title.$invalid }"
              v-model="v$.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.rating.description')" for="rating-description"></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="rating-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.rating.imageUrl')" for="rating-imageUrl"></label>
            <input
              type="text"
              class="form-control"
              name="imageUrl"
              id="rating-imageUrl"
              data-cy="imageUrl"
              :class="{ valid: !v$.imageUrl.$invalid, invalid: v$.imageUrl.$invalid }"
              v-model="v$.imageUrl.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.rating.createdDate')" for="rating-createdDate"></label>
            <div class="d-flex">
              <input
                id="rating-createdDate"
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
            <label class="form-control-label" v-text="t$('szakdolgozatApp.rating.user')" for="rating-user"></label>
            <select class="form-control" id="rating-user" data-cy="user" name="user" v-model="rating.user">
              <option :value="null"></option>
              <option
                :value="rating.user && userOption.id === rating.user.id ? rating.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.rating.recipe')" for="rating-recipe"></label>
            <select class="form-control" id="rating-recipe" data-cy="recipe" name="recipe" v-model="rating.recipe">
              <option :value="null"></option>
              <option
                :value="rating.recipe && recipeOption.id === rating.recipe.id ? rating.recipe : recipeOption"
                v-for="recipeOption in recipes"
                :key="recipeOption.id"
              >
                {{ recipeOption.id }}
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
<script lang="ts" src="./rating-update.component.ts"></script>
