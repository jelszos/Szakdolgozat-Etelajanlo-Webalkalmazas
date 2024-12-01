<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="szakdolgozatApp.ingredient.home.createOrEditLabel"
          data-cy="IngredientCreateUpdateHeading"
          v-text="t$('szakdolgozatApp.ingredient.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="ingredient.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="ingredient.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.ingredient.name')" for="ingredient-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="ingredient-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.ingredient.amount')" for="ingredient-amount"></label>
            <input
              type="number"
              class="form-control"
              name="amount"
              id="ingredient-amount"
              data-cy="amount"
              :class="{ valid: !v$.amount.$invalid, invalid: v$.amount.$invalid }"
              v-model.number="v$.amount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.ingredient.unit')" for="ingredient-unit"></label>
            <select
              class="form-control"
              name="unit"
              :class="{ valid: !v$.unit.$invalid, invalid: v$.unit.$invalid }"
              v-model="v$.unit.$model"
              id="ingredient-unit"
              data-cy="unit"
            >
              <option v-for="unit in unitValues" :key="unit" :value="unit" :label="t$('szakdolgozatApp.Unit.' + unit)">{{ unit }}</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.ingredient.instruction')" for="ingredient-instruction"></label>
            <select
              class="form-control"
              id="ingredient-instruction"
              data-cy="instruction"
              name="instruction"
              v-model="ingredient.instruction"
            >
              <option :value="null"></option>
              <option
                :value="
                  ingredient.instruction && instructionOption.id === ingredient.instruction.id ? ingredient.instruction : instructionOption
                "
                v-for="instructionOption in instructions"
                :key="instructionOption.id"
              >
                {{ instructionOption.id }}
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
<script lang="ts" src="./ingredient-update.component.ts"></script>
