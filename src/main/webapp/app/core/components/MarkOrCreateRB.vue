<script setup lang="ts">
import CreateRecipeBookForm from '@/core/components/CreateRecipeBookForm.vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { inject, ref, watch } from 'vue';
import RecipeBookService from '@/entities/recipe-book/recipe-book.service';
import { useAlertService } from '@/shared/alert/alert.service';
import { useI18n } from 'vue-i18n';

const { t: t$ } = useI18n(); // Localization function

const alertService = inject('alertService', () => useAlertService(), true);
const recipebookService = inject('recipeBookService', () => new RecipeBookService());
const recipeBooks = ref([]);
const isRbCreation = ref(false);
const isModalOpen = ref(false); // Local state to manage modal open/close

const props = defineProps<{
  isOpen: boolean;
  onClose: () => void;
  recipeId: number;
  onlyRecipeBookCreation?: false;
}>();

// Sync local state with prop value using a watcher
watch(
  () => props.isOpen,
  newValue => {
    isModalOpen.value = newValue;
  },
);

async function getRecipeBooksByUser(recipeId: number) {
  await recipebookService()
    .retrieveIsRecipeInList(recipeId)
    .then(res => {
      recipeBooks.value = res.data;
    });
}

watch(
  () => isModalOpen.value,
  newValue => {
    if (newValue) {
      getRecipeBooksByUser(props.recipeId);
    } else {
      props.onClose(); // Call the onClose method when modal closes
    }
  },
);

function addRbToList(recipeBook) {
  recipeBooks.value = [...recipeBooks.value, recipeBook];
}

function addOrRemoveFromRecipeBook(recipeBookId: number) {
  recipebookService()
    .addOrRemoveRecipe(recipeBookId, props.recipeId)
    .then(res => {
      if (res.status == 201) {
        alertService.showSuccess(t$('szakdolgozatApp.recipeBook.modal.addSuccess'));
      } else if (res.status == 204) {
        alertService.showInfo(t$('szakdolgozatApp.recipeBook.modal.removeSuccess'));
      }
    })
    .catch(() => {
      alertService.showError(t$('szakdolgozatApp.recipeBook.error'));
    });
}

function toggleNewRecipeBVisible() {
  isRbCreation.value = !isRbCreation.value;
}
</script>

<template>
  <b-modal :title="t$('szakdolgozatApp.recipeBook.modal.modalTitle')" v-model="isModalOpen" hide-footer centered @hide="props.onClose">
    <div v-if="!isRbCreation">
      <div v-for="recipeBook in recipeBooks" :key="recipeBook.id">
        <div class="my-2 cursor-pointer d-flex justify-content-between">
          <b-form-checkbox
            :checked="recipeBook?.isRecipeInList"
            class="cursor-pointer"
            @change="() => addOrRemoveFromRecipeBook(recipeBook.id)"
          >
            {{ recipeBook?.title }}
            {{ recipeBook?.isRecipeInTheList }}
          </b-form-checkbox>
          <font-awesome-icon v-if="!recipeBook?.isPrivate" icon="globe-americas" id="id_public_icon"></font-awesome-icon>
          <font-awesome-icon v-else icon="lock"></font-awesome-icon>
        </div>
      </div>

      <b-button v-if="!onlyRecipeBookCreation" class="w-100 mt-2" variant="secondary" @click="toggleNewRecipeBVisible">
        {{ t$('szakdolgozatApp.recipeBook.modal.newButton') }}
      </b-button>
    </div>

    <div v-if="isRbCreation && !onlyRecipeBookCreation">
      <create-recipe-book-form :add-recipe-book-to-list="addRbToList" :toggle-new-recipe-b-visible="toggleNewRecipeBVisible" />
    </div>
  </b-modal>
</template>

<style scoped></style>
