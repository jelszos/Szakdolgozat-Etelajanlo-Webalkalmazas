<script lang="ts">
import { defineComponent, inject, ref } from 'vue';

import { functions } from '@/shared/utils/functions';
import RecipeBookService from '@/entities/recipe-book/recipe-book.service';
import { useAlertService } from '@/shared/alert/alert.service';
import { showConfirmModal } from '@/core/modals/ConfirmModalService';
import type { IRecipeBook } from '@/shared/model/recipe-book.model';
import { useI18n } from 'vue-i18n';

export default defineComponent({
  name: 'RecipeBookCard',
  components: {},
  props: {
    recipeBook: {
      type: Object,
      required: true,
    },
    removeFromRecipeBooks: {
      type: Function,
      required: false,
    },
    showOptions: {
      type: Boolean,
      required: false,
    },
    showUser: {
      type: Boolean,
      required: false,
      default: true,
    },
  },
  setup() {
    const recipeBookService = inject('recipeBookService', () => new RecipeBookService());
    const alertService = inject('alertService', () => useAlertService(), true);
    const editRecipeBookModal = ref(false);
    const editRecipeBookData: IRecipeBook = ref({});

    return {
      editRecipeBookData,
      recipeBookService,
      alertService,
      editRecipeBookModal,
      t$: useI18n().t,
    };
  },
  computed: {
    functions() {
      return functions;
    },
  },
  mounted() {
    this.getAvatarImg(this.recipeBook?.user?.avatar);
  },
  methods: {
    getAvatarImg: number => functions.getAvatarImg(number),

    confirmDeleting(recipeBookId: number) {
      showConfirmModal(null, {
        title: 'Are you sure?',
        description: "We will delete it permanently, you can't undo it afterwards",
        onOk: () => this.deleteRecipeBook(recipeBookId),
      });
    },
    deleteRecipeBook(recipeBookId: number) {
      this.recipeBookService()
        .delete(recipeBookId)
        .then(res => {
          if (res.status == 204) {
            this.removeFromRecipeBooks(recipeBookId);
            this.alertService.showSuccess('Successfully deleted!');
          }
        });
    },
    editRecipeBook() {
      this.recipeBookService()
        .partialUpdate(this.editRecipeBookData)
        .then(() => {
          this.alertService.showSuccess('Successfully updated!');
        })
        .catch(err => {
          this.alertService.showError('Something went wrong');
        });
    },
    closeEditRecipeModal() {
      this.editRecipeBookModal = false;
    },
    openEditRecipeModal(recipeBook: IRecipeBook) {
      this.editRecipeBookModal = true;
      this.editRecipeBookData = recipeBook;
    },
  },
});
</script>

<template>
  <div class="recipe-book-card">
    <b-card style="color: #212529" class="h-100">
      <div class="text-slate-900 position-relative h-100 d-flex flex-column">
        <b-dropdown v-if="showOptions" size="sm" variant="secondary" text="" class="position-absolute" style="right: 0">
          <b-dropdown-item @click="openEditRecipeModal(recipeBook)">
            {{ t$('szakdolgozatApp.recipeBook.card.edit') }}
          </b-dropdown-item>
          <b-dropdown-item variant="danger" @click="confirmDeleting(recipeBook.id)">
            {{ t$('szakdolgozatApp.recipeBook.card.delete') }}
          </b-dropdown-item>
        </b-dropdown>
        <strong class="trun two-line" style="font-size: 18px">
          {{ recipeBook.title }}
        </strong>
        <p class="trun four-line">
          {{ recipeBook.description }}
        </p>
        <div class="recipe-images grid-images mb-3">
          <div v-for="image in recipeBook.recipeImages" :key="image" style="flex-grow: 1">
            <img :src="image" alt="Img" style="width: 100%; height: 50px; object-fit: cover" />
          </div>
        </div>
        <div class="mt-4 d-flex gap mt-auto mb-0">
          <b-button :href="'/recipe-book/' + recipeBook.id + '/view'" class="w-100">{{
            t$('szakdolgozatApp.recipeBook.card.check')
          }}</b-button>
        </div>
      </div>
      <template #footer v-if="showUser">
        <router-link :to="`/profile-view/${recipeBook.user?.login}/view`" class="w-100 text-decoration-none">
          <div class="d-flex align-items-center creator">
            <b-avatar :src="getAvatarImg(recipeBook.user?.avatar)" size="sm"></b-avatar>
            <p class="ml-1 mt-3 ml-2 text-slate-900 white-hover">{{ recipeBook.user?.firstName + ' ' + recipeBook.user?.lastName }}</p>
          </div>
        </router-link>
      </template>
    </b-card>
  </div>

  <b-modal
    v-model="editRecipeBookModal"
    @close="closeEditRecipeModal"
    hide-footer
    :title="t$('szakdolgozatApp.recipeBook.modal.editTitle')"
  >
    <b-form @submit.prevent="editRecipeBook">
      <div>
        <label for="title">{{ t$('szakdolgozatApp.recipeBook.modal.name') }}</label>
        <b-form-input
          id="title"
          v-model="editRecipeBookData.title"
          :placeholder="t$('szakdolgozatApp.recipeBook.modal.namePH')"
        ></b-form-input>
      </div>

      <div class="mt-3">
        <label for="theme">{{ t$('szakdolgozatApp.recipeBook.modal.theme') }}</label>
        <b-form-input
          id="theme"
          v-model="editRecipeBookData.theme"
          :placeholder="t$('szakdolgozatApp.recipeBook.modal.themePH')"
        ></b-form-input>
      </div>

      <div class="mt-3">
        <label for="description">{{ t$('szakdolgozatApp.recipeBook.modal.description') }}</label>
        <b-form-textarea
          type="textarea"
          id="description"
          v-model="editRecipeBookData.description"
          :placeholder="t$('szakdolgozatApp.recipeBook.modal.descriptionPH')"
        ></b-form-textarea>
      </div>

      <div class="my-3">
        <label for="isPrivate">{{ t$('szakdolgozatApp.recipeBook.modal.visibility') }}</label>
        <b-form-select id="isPrivate" v-model="editRecipeBookData.isPrivate">
          <b-form-select-option :value="true"> {{ t$('szakdolgozatApp.recipeBook.modal.private') }} </b-form-select-option>
          <b-form-select-option :value="false"> {{ t$('szakdolgozatApp.recipeBook.modal.public') }} </b-form-select-option>
        </b-form-select>
      </div>
      <div class="d-flex justify-content-end">
        <b-button type="button" @click="closeEditRecipeModal" variant="secondary">{{
          t$('szakdolgozatApp.recipeBook.modal.cancel')
        }}</b-button>
        <b-button type="submit" variant="primary" class="ml-2">{{ t$('szakdolgozatApp.recipeBook.modal.update') }}</b-button>
      </div>
    </b-form>
  </b-modal>
</template>

<style scoped>
.grid-images {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 4px;
}
.creator {
  border-radius: 12px;
  padding-left: 24px;
}
.creator:hover {
  background-color: #e6e6e6;
  color: white !important;
  cursor: pointer;
}

.trun {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.trun.two-line {
  -webkit-line-clamp: 2;
  line-clamp: 4;
}
.trun.four-line {
  -webkit-line-clamp: 4;
  line-clamp: 4;
}

.gap {
  gap: 8px;
}
</style>
