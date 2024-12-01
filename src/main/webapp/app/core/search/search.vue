<template>
  <div class="container">
    <div class="d-flex justify-content-center flex-column">
      <!--      <h1 class="display-4" v-text="t$('search.title')"></h1>-->
      <div class="position-relative text-center my-5">
        <b-img :src="headerBg" class="header-img" />
        <h1 class="carousel-header-text mb-1 position-relative" v-text="t$('search.title')"></h1>
      </div>

      <b-alert variant="info" show>
        <font-awesome-icon icon="search" />
        {{ t$('search.subtitle') }}
      </b-alert>
      <div>
        <b-form @submit.prevent="search()">
          <b-form-group :label="t$('search.input.recipeTitle')">
            <b-input-group>
              <b-form-input v-model="searchFormData.recipeTitle" :placeholder="t$('search.input.recipeTitlePH')" />
              <b-input-group-append>
                <b-button type="submit" variant="primary" v-text="t$('search.searchButton')"></b-button>
              </b-input-group-append>
            </b-input-group>
          </b-form-group>
          <div class="d-flex mt-4 justify-content-between w-100">
            <div class="flex-lg-grow-1 w-100">
              <b-form-group :label="t$('search.input.foodTypes')">
                <b-form-tags
                  id="tags-component-select"
                  v-model="searchFormData.foodTypes"
                  size="lg"
                  class="mb-2"
                  add-on-change
                  no-outer-focus
                >
                  <template #default="{ tags, inputAttrs, inputHandlers, disabled, removeTag }">
                    <b-form-select
                      v-bind="inputAttrs"
                      v-on="inputHandlers"
                      v-model="searchFormData.foodTypes"
                      :disabled="disabled || availableFoodTypes.length === 0"
                      :options="availableFoodTypes"
                    >
                      <template #first>
                        <!-- This is required to prevent bugs with Safari -->
                        <option disabled value="">Choose a tag...</option>
                      </template>
                    </b-form-select>
                    <ul v-if="tags.length > 0" class="list-inline d-inline-block mb-2">
                      <li v-for="tag in tags" :key="tag" class="list-inline-item">
                        <b-form-tag @remove="removeTag(tag)" :title="tag" :disabled="disabled" variant="info">
                          {{ t$(`szakdolgozatApp.FoodType.${tag}`) }}
                        </b-form-tag>
                      </li>
                    </ul>
                  </template>
                </b-form-tags>
              </b-form-group>
            </div>
            <b-form-group :label="t$('search.input.foodCategories')" class="flex-lg-grow-1 w-100">
              <b-input-group>
                <b-form-tags
                  id="tags-component-select"
                  v-model="searchFormData.foodCategories"
                  size="lg"
                  class="mb-2"
                  add-on-change
                  no-outer-focus
                >
                  <template #default="{ tags, inputAttrs, inputHandlers, disabled, removeTag }">
                    <b-form-select
                      v-bind="inputAttrs"
                      v-on="inputHandlers"
                      v-model="searchFormData.foodCategories"
                      :disabled="disabled || availableFoodCategories.length === 0"
                      :options="availableFoodCategories"
                    >
                      <template #first>
                        <!-- This is required to prevent bugs with Safari -->
                        <option disabled value="">Choose a tag...</option>
                      </template>
                    </b-form-select>
                    <ul v-if="tags.length > 0" class="list-inline d-inline-block mb-2">
                      <li v-for="tag in tags" :key="tag" class="list-inline-item">
                        <b-form-tag @remove="removeTag(tag)" :title="tag" :disabled="disabled" variant="info">
                          {{ t$(`szakdolgozatApp.FoodCategory.${tag}`) }}
                        </b-form-tag>
                      </li>
                    </ul>
                  </template>
                </b-form-tags>
              </b-input-group>
            </b-form-group>

            <b-form-group :label="t$('search.input.ingredients')" class="flex-lg-grow-1 w-100">
              <b-input-group>
                <b-form-tags
                  id="tags-component-select"
                  v-model="searchFormData.ingredientNames"
                  size="lg"
                  class="mb-2"
                  add-on-change
                  no-outer-focus
                >
                  <template #default="{ tags, inputAttrs, inputHandlers, disabled, removeTag }">
                    <b-form-select
                      v-bind="inputAttrs"
                      v-on="inputHandlers"
                      v-model="searchFormData.ingredientNames"
                      :disabled="disabled || availableIngredients.length === 0"
                      :options="availableIngredients"
                    >
                      <template #first>
                        <!-- This is required to prevent bugs with Safari -->
                        <option disabled value="">Choose a tag...</option>
                      </template>
                    </b-form-select>
                    <ul v-if="tags.length > 0" class="list-inline d-inline-block mb-2">
                      <li v-for="tag in tags" :key="tag" class="list-inline-item">
                        <b-form-tag @remove="removeTag(tag)" :title="tag" :disabled="disabled" variant="info">{{ tag }}</b-form-tag>
                      </li>
                    </ul>
                  </template>
                </b-form-tags>
              </b-input-group>
            </b-form-group>
          </div>
        </b-form>
      </div>
    </div>

    <div class="d-flex custom-pagination justify-content-between mt-4">
      <div>
        <p class="text-slate-900">{{ paginationInfo.currentPage + 1 }}. {{ t$('search.page') }}</p>
      </div>
      <div class="d-flex align-items-center">
        <div class="d-flex align-items-center">
          <b-form-select v-model="sortFormData.sortBy" @change="onSortChange" style="width: auto; margin-right: 10px">
            <b-form-select-option v-for="(option, index) in sortOptions" :key="index" :value="option.value">
              {{ option.label }}
            </b-form-select-option>
          </b-form-select>

          <b-button
            v-if="sortFormData.sortDirection"
            :variant="sortFormData.sortDirection ? 'dark' : 'light'"
            @click="toggleSortDirection"
            style="font-size: 22px; display: flex; align-items: center"
          >
            <font-awesome-icon icon="sort-amount-desc" />
          </b-button>

          <b-button
            v-else
            :variant="!sortFormData.sortDirection ? 'dark' : 'light'"
            @click="toggleSortDirection"
            style="font-size: 22px; display: flex; align-items: center"
          >
            <font-awesome-icon icon="sort-amount-asc" />
          </b-button>
        </div>
      </div>
      <div class=" ">
        <p class="text-slate-900">
          {{ paginationInfo.totalElements }}
          <span> {{ t$('search.result') }} </span>
        </p>
      </div>
    </div>

    <div class="recipe-search-result" v-if="didSearch">
      <recipe-card
        v-for="recipe in filteredRecipes"
        :key="recipe"
        :recipe="recipe"
        :mark-recipe="markRecipe"
        :show-user="true"
        :toggle-favorite="toggleFavorite"
      ></recipe-card>
    </div>
    <!--    <p>{{ paginationInfo.currentPage }}</p>-->
    <b-pagination-nav
      :number-of-pages="paginationInfo.totalPages"
      :page="paginationInfo.currentPage"
      :link-gen="pLinkGen"
      :onclick="onPageChange"
      class="mt-5"
      :value="parseInt(paginationInfo.currentPage) + 1"
      hide-goto-end-buttons
    >
    </b-pagination-nav>
  </div>

  <mark-or-create-r-b :on-close="closeMarkModal" :is-open="isAddFavModalData.isOpen" :recipe-id="isAddFavModalData.recipe?.id" />
</template>

<script lang="ts" src="./search.component.ts"></script>

<style scoped>
.recipe-search-result {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 8px;
  grid-row-gap: 28px;
}

.custom-pagination p {
  font-size: 28px;
}

.header-img {
  max-width: 100%;
  height: 150%;
  left: 0;
  position: absolute;
  z-index: 1;
}

.carousel-header-text {
  font-size: 30px;
  font-weight: 700;
  margin-bottom: 22px;
  z-index: 2;
  top: 4px;
  color: #334155;
}

.rtime-badge span {
  font-size: 75%;
  color: black;
}
</style>
