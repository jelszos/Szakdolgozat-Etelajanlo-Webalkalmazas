<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="szakdolgozatApp.instruction.home.createOrEditLabel"
          data-cy="InstructionCreateUpdateHeading"
          v-text="t$('szakdolgozatApp.instruction.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="instruction.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="instruction.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.instruction.title')" for="instruction-title"></label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="instruction-title"
              data-cy="title"
              :class="{ valid: !v$.title.$invalid, invalid: v$.title.$invalid }"
              v-model="v$.title.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('szakdolgozatApp.instruction.requiredTime')"
              for="instruction-requiredTime"
            ></label>
            <input
              type="number"
              class="form-control"
              name="requiredTime"
              id="instruction-requiredTime"
              data-cy="requiredTime"
              :class="{ valid: !v$.requiredTime.$invalid, invalid: v$.requiredTime.$invalid }"
              v-model.number="v$.requiredTime.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.instruction.description')" for="instruction-description"></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="instruction-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.instruction.recipe')" for="instruction-recipe"></label>
            <select class="form-control" id="instruction-recipe" data-cy="recipe" name="recipe" v-model="instruction.recipe">
              <option :value="null"></option>
              <option
                :value="instruction.recipe && recipeOption.id === instruction.recipe.id ? instruction.recipe : recipeOption"
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
<script lang="ts" src="./instruction-update.component.ts"></script>
