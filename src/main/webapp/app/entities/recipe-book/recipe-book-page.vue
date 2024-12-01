<template>
  <div class="container">
    <div class="d-flex justify-content-center flex-column">
      <div class="position-relative text-center my-5">
        <b-img :src="headerBg" class="carousel-header-img" />
        <h1 class="carousel-header mb-0 position-relative" v-text="t$('szakdolgozatApp.recipeBook.home.title')"></h1>
      </div>
      <b-badge class="ml-3">Public recipe books</b-badge>

      <b-alert show>
        <font-awesome-icon icon="info-circle"></font-awesome-icon>
        {{ t$('szakdolgozatApp.recipeBook.home.subtitle') }}
      </b-alert>
      <div class="mt-3">
        <div class="icon-text">
          <p class="text-slate-900" style="font-size: 22px; font-weight: 700">{{ t$('szakdolgozatApp.recipeBook.search.info') }}</p>
        </div>
        <div class="mt-4 d-flex align-items-end custom-gap">
          <b-form-group :label="t$('szakdolgozatApp.recipeBook.search.labelField')" class="flex-lg-grow-1 w-100">
            <b-input-group>
              <b-form-input :placeholder="t$('szakdolgozatApp.recipeBook.search.titleField')" v-model="searchFormData.title" />
            </b-input-group>
          </b-form-group>
          <b-form-group :label="t$('szakdolgozatApp.recipeBook.search.descriptionLabel')" class="flex-lg-grow-1 w-100">
            <b-input-group>
              <b-form-input :placeholder="t$('szakdolgozatApp.recipeBook.search.descriptionField')" v-model="searchFormData.description" />
            </b-input-group>
          </b-form-group>

          <b-form-group :label="t$('szakdolgozatApp.recipeBook.search.themeLabel')" class="flex-lg-grow-1 w-100">
            <b-input-group>
              <b-form-input :placeholder="t$('szakdolgozatApp.recipeBook.search.themeField')" v-model="searchFormData.theme" />
            </b-input-group>
          </b-form-group>

          <b-form-group :label="t$('szakdolgozatApp.recipeBook.search.tagLabel')" class="flex-lg-grow-1 w-100">
            <b-input-group>
              <b-form-input :placeholder="t$('szakdolgozatApp.recipeBook.search.tagField')" v-model="searchFormData.tag" />
            </b-input-group>
          </b-form-group>

          <div class="mb-3">
            <b-button variant="primary" @click.prevent.stop="search()">{{ t$('szakdolgozatApp.recipeBook.card.search') }}</b-button>
          </div>
        </div>
      </div>

      <div class="w-100 divider my-5"></div>

      <div class="d-flex text-slate-900 align-items-center justify-content-between" style="font-size: 20px; font-weight: 500">
        <p>{{ paginationInfo.currentPage + 1 }}. {{ t$('global.page') }}</p>
        <p>{{ paginationInfo.totalElements }} {{ t$('szakdolgozatApp.recipeBook.entityTitle') }}</p>
      </div>

      <div class="recipe-books">
        <recipe-book-card v-for="recipeBook in recipeBooks" :key="recipeBook.id" :recipe-book="recipeBook"></recipe-book-card>
        <br />
      </div>
    </div>
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
</template>

<script lang="ts" src="./recipe-book-page.component.ts"></script>

<style>
.recipe-books {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 8px;
  grid-row-gap: 28px;
}
.icon-text {
  display: flex;
  align-items: center;
}

.custom-gap {
  gap: 12px;
}
.divider {
  outline: 1px solid rgba(126, 126, 126, 0.3);
}
</style>
