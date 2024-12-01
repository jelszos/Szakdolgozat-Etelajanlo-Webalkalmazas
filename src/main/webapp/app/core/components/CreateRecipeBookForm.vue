<script setup lang="ts">
import { inject, ref } from 'vue';
import RecipeBookService from '@/entities/recipe-book/recipe-book.service';
import { useAlertService } from '@/shared/alert/alert.service';
import { useI18n } from 'vue-i18n';

const props = defineProps<{
  toggleNewRecipeBVisible: void;
  addRecipeBookToList: void;
}>();

const { t } = useI18n();

const alertService = inject('alertService', useAlertService, true);
const recipeBookService = inject('recipeBookService', () => new RecipeBookService());
const newRecipeBook = ref({
  title: '',
  theme: '',
  description: '',
  isPrivate: false,
});

function createNewRecipeBook() {
  recipeBookService()
    .create(newRecipeBook.value)
    .then(res => {
      props.addRecipeBookToList(res);
      alertService.showInfo(t('szakdolgozatApp.recipeBook.modal.addSuccess'));
    })
    .catch(err => {
      alertService.showError(t('szakdolgozatApp.recipeBook.modal.error'));
      console.error(err);
    });
}
</script>

<template>
  <b-form @submit.prevent="createNewRecipeBook">
    <div>
      <label for="title">{{ t('szakdolgozatApp.recipeBook.modal.name') }}</label>
      <b-form-input id="title" v-model="newRecipeBook.title" :placeholder="t('szakdolgozatApp.recipeBook.modal.namePH')"></b-form-input>
    </div>

    <div class="mt-3">
      <label for="theme">{{ t('szakdolgozatApp.recipeBook.modal.theme') }}</label>
      <b-form-input id="theme" v-model="newRecipeBook.theme" :placeholder="t('szakdolgozatApp.recipeBook.modal.themePH')"></b-form-input>
    </div>

    <div class="mt-3">
      <label for="description">{{ t('szakdolgozatApp.recipeBook.modal.description') }}</label>
      <b-form-textarea
        id="description"
        v-model="newRecipeBook.description"
        :placeholder="t('szakdolgozatApp.recipeBook.modal.descriptionPH')"
      ></b-form-textarea>
    </div>

    <div class="my-3">
      <label for="isPrivate">{{ t('szakdolgozatApp.recipeBook.modal.visibility') }}</label>
      <b-form-select id="isPrivate" v-model="newRecipeBook.isPrivate">
        <b-form-select-option :value="true">{{ t('szakdolgozatApp.recipeBook.modal.private') }}</b-form-select-option>
        <b-form-select-option :value="false">{{ t('szakdolgozatApp.recipeBook.modal.public') }}</b-form-select-option>
      </b-form-select>
    </div>

    <b-button class="mt-2" variant="primary" type="submit">
      {{ t('szakdolgozatApp.recipeBook.modal.newButton') }}
    </b-button>
    <b-button class="mt-2 ml-2" variant="light" type="button" @click="toggleNewRecipeBVisible">
      {{ t('szakdolgozatApp.recipeBook.modal.cancel') }}
    </b-button>
  </b-form>
</template>

<style scoped></style>
