<template>
  <div class="row justify-content-center">
    <div class="container">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="szakdolgozatApp.recipe.home.createOrEditLabel" data-cy="RecipeCreateUpdateHeading"></h2>

        <div class="position-relative text-center my-5">
          <b-img :src="headerBg" class="carousel-header-img" />
          <h1
            class="carousel-header mb-4 position-relative"
            v-text="isNewRecipe ? t$('szakdolgozatApp.recipe.home.createLabel') : t$('szakdolgozatApp.recipe.home.createOrEditLabel')"
          ></h1>
        </div>

        <div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.recipe.title')" for="recipe-title"></label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="recipe-title"
              data-cy="title"
              :class="{ valid: !v$.title.$invalid, invalid: v$.title.$invalid }"
              v-model="v$.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.recipe.description')" for="recipe-description"></label>
            <textarea
              type="text"
              class="form-control"
              name="description"
              id="recipe-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.recipe.foodCategory')" for="recipe-foodCategory"></label>
            <select
              class="form-control"
              name="foodCategory"
              :class="{ valid: !v$.foodCategory.$invalid, invalid: v$.foodCategory.$invalid }"
              v-model="v$.foodCategory.$model"
              id="recipe-foodCategory"
              data-cy="foodCategory"
            >
              <option
                v-for="foodCategory in foodCategoryValues"
                :key="foodCategory"
                :value="foodCategory"
                :label="t$('szakdolgozatApp.FoodCategory.' + foodCategory)"
              >
                {{ foodCategory }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.recipe.foodType')" for="recipe-foodType"></label>
            <select
              class="form-control"
              name="foodType"
              :class="{ valid: !v$.foodType.$invalid, invalid: v$.foodType.$invalid }"
              v-model="v$.foodType.$model"
              id="recipe-foodType"
              data-cy="foodType"
            >
              <option
                v-for="foodType in foodTypeValues"
                :key="foodType"
                :value="foodType"
                :label="t$('szakdolgozatApp.FoodType.' + foodType)"
              >
                {{ foodType }}
              </option>
            </select>
          </div>

          <div class="form-group">
            <label class="form-control-label" v-text="t$('szakdolgozatApp.recipe.imageUrl')" for="recipe-imageUrl"></label>
            <b-form-file id="file-default" name="imageUrl" @change="onFileChanged($event)"></b-form-file>
          </div>
          <div v-if="recipe?.imageUrl" class="image-preview d-flex justify-content-center mb-4">
            <img :src="recipe?.imageUrl" alt="recipeImg" />
          </div>
          <div class="text-center">
            <b-button @click="showInstructionForm = true" variant="primary">
              <font-awesome-icon icon="plus" />
              {{ t$('szakdolgozatApp.recipe.addInstruction.title') }}
            </b-button>
          </div>

          <div class="">
            <b-modal
              id="modal-ingredient-form"
              v-model="showInstructionForm"
              @hide="resetForm"
              :title="t$('szakdolgozatApp.recipe.addInstruction.title')"
              hide-footer
              no-close-on-backdrop
            >
              <b-form @submit.prevent="submitInstructionForm">
                <!-- Title Field -->
                <b-form-group :label="t$('szakdolgozatApp.recipe.title')" label-for="title">
                  <b-form-input
                    id="title"
                    type="text"
                    name="instructionTitle"
                    v-model="instructionFormData.title"
                    required
                    :placeholder="t$('szakdolgozatApp.recipe.addInstruction.titlePlaceholder')"
                  ></b-form-input>
                </b-form-group>
                <!-- Time Field -->
                <b-form-group :label="t$('szakdolgozatApp.recipe.addInstruction.timeLabel')" label-for="time">
                  <b-form-input
                    id="time"
                    type="number"
                    name="instructionRequiredTime"
                    v-model="instructionFormData.requiredTime"
                    min="1"
                    required
                    :placeholder="t$('szakdolgozatApp.recipe.addInstruction.timePlaceholder')"
                  ></b-form-input>
                </b-form-group>

                <!-- Description Field -->
                <b-form-group :label="t$('szakdolgozatApp.recipe.addInstruction.descriptionLabel')" label-for="description">
                  <b-form-input
                    id="description"
                    type="text"
                    name="instructionDescription"
                    v-model="instructionFormData.description"
                    required
                    :placeholder="t$('szakdolgozatApp.recipe.addInstruction.descriptionPlaceholder')"
                  ></b-form-input>
                </b-form-group>

                <!-- Ingredients Section -->
                <div v-for="(ingredient, index) in instructionFormData.ingredients" :key="index" class="mb-3">
                  <b-form-row>
                    <b-col cols="6">
                      <b-form-group
                        :label="index + 1 + '. ' + t$('szakdolgozatApp.recipe.addInstruction.ingredient')"
                        :label-for="'ingredient-select-' + index"
                      >
                        <b-form-select
                          :id="'ingredient-select-' + index"
                          name="instructionIngredients"
                          v-model="ingredient.name"
                          :options="filteredIngredients(index)"
                          required
                        ></b-form-select>
                      </b-form-group>
                    </b-col>

                    <!-- Amount Input -->
                    <b-col cols="3">
                      <b-form-group :label="t$('szakdolgozatApp.recipe.addInstruction.amount')" label-for="'amount-' + index">
                        <b-form-input
                          :id="'amount-' + index"
                          name="ingredientAmount"
                          v-model="ingredient.amount"
                          type="number"
                          min="1"
                          required
                        ></b-form-input>
                      </b-form-group>
                    </b-col>

                    <!-- Unit Select -->
                    <b-col cols="3">
                      <b-form-group :label="t$('szakdolgozatApp.recipe.addInstruction.unit')" label-for="'unit-select-' + index">
                        <b-form-select
                          :id="'unit-select-' + index"
                          name="ingredientUnit"
                          v-model="ingredient.unit"
                          :options="unitValues"
                          required
                        ></b-form-select>
                      </b-form-group>
                    </b-col>

                    <!-- Remove Ingredient Button -->
                    <b-col cols="12" class="text-right">
                      <b-button
                        variant="danger"
                        size="sm"
                        @click="removeIngredient(index)"
                        v-if="instructionFormData.ingredients.length > 1"
                      >
                        <font-awesome-icon icon="trash" />
                      </b-button>
                    </b-col>
                  </b-form-row>
                </div>

                <!-- Add Ingredient Button -->
                <b-button class="w-100" variant="secondary" size="sm" @click="addIngredient">
                  <font-awesome-icon icon="plus" />
                  {{ t$('szakdolgozatApp.recipe.addInstruction.addAnother') }}
                </b-button>

                <hr />

                <!-- Submit Button -->
                <b-button type="submit" variant="primary" @click="submitInstructionForm" v-if="!editInstructions">{{
                  t$('szakdolgozatApp.recipe.addInstruction.submit')
                }}</b-button>
                <b-button type="button" variant="primary" @click="closeInstructionModal" v-if="editInstructions">{{
                  t$('szakdolgozatApp.recipe.addInstruction.submit')
                }}</b-button>
              </b-form>
            </b-modal>
          </div>
          <!-- Display the compact instruction list -->
          <b-row v-if="recipe?.instructions?.length > 0 || v$.recipe?.instructions.$error">
            <b-col>
              <p class="instruction-text text-slate-900 mb-5 mt-3">Instructions:</p>
              <div class="d-flex flex-column gap text-slate-900">
                <div v-for="(data, index) in recipe?.instructions" :key="index" class="instructions-section position-relative">
                  <div class="w-100 d-flex justify-content-center">
                    <p class="instruction-number text-slate-900 bg-warning">{{ index + 1 }}</p>
                  </div>
                  <div class="d-flex justify-content-between">
                    <div class="mb-3">
                      <font-awesome-icon icon="clock" style="color: #5a5a5a" />
                      <span style="font-weight: 500">{{ data.requiredTime }} minutes</span>
                    </div>
                    <div class="instruction-buttons">
                      <b-button size="sm" variant="warning" @click="editInstruction(data)">Edit</b-button>
                      <b-button size="sm" variant="danger" @click="deleteInstruction(index)">
                        <font-awesome-icon icon="trash" />
                      </b-button>
                    </div>
                  </div>
                  <div class="recipe-creation-custom-grid">
                    <div>
                      <p style="font-size: 18px; font-weight: 500" v-if="data?.ingredients.length > 0">Ingredients</p>
                      <div v-for="(ingredient, ingIndex) in data.ingredients" :key="ingIndex">
                        {{ ingredient.amount }} {{ ingredient.unit }} of {{ ingredient.name }}
                      </div>
                    </div>
                    <div class="ml-3">
                      <p style="font-weight: 500; font-size: 18px">Description</p>
                      <p>{{ data.description }}</p>
                    </div>
                  </div>
                </div>
              </div>
              <b-alert variant="danger" v-if="v$.recipe?.instructions.$error">
                Instructions are required and must contain valid entries.
              </b-alert>
            </b-col>
          </b-row>
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
            class="btn btn-primary ml-2"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./recipe-update.component.ts"></script>

<style>
.instructions-section {
  border-bottom: 2px solid #e5e5e5;
  padding-bottom: 22px;
}

.instructions-section:last-child {
  border-bottom: unset;
  padding-bottom: 22px;
}

.gap {
  gap: 22px;
}

.instruction-buttons button:last-child {
  margin-left: 8px;
}

.instruction-number {
  font-size: 18px;
  width: 25px;
  height: 25px;
  border-radius: 99px;
  display: flex;
  justify-content: center;
  font-weight: 500;
}

.recipe-creation-custom-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
}

.instruction-text {
  font-size: 32px;
  font-weight: 500;
}

.image-preview img {
  width: auto;

  height: 300px;
  max-height: 300px;
}
</style>
