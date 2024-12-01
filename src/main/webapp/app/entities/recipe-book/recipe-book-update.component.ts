import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RecipeBookService from './recipe-book.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';
import { type IRecipeBook, RecipeBook } from '@/shared/model/recipe-book.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RecipeBookUpdate',
  setup() {
    const recipeBookService = inject('recipeBookService', () => new RecipeBookService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const recipeBook: Ref<IRecipeBook> = ref(new RecipeBook());
    const userService = inject('userService', () => new UserService());
    const users: Ref<Array<any>> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveRecipeBook = async recipeBookId => {
      try {
        const res = await recipeBookService().find(recipeBookId);
        res.createdDate = new Date(res.createdDate);
        recipeBook.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.recipeBookId) {
      retrieveRecipeBook(route.params.recipeBookId);
    }

    const initRelationships = () => {
      userService()
        .retrieve()
        .then(res => {
          users.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      title: {},
      theme: {},
      description: {},
      tags: {},
      createdDate: {},
      user: {},
    };
    const v$ = useVuelidate(validationRules, recipeBook as any);
    v$.value.$validate();

    return {
      recipeBookService,
      alertService,
      recipeBook,
      previousState,
      isSaving,
      currentLanguage,
      users,
      v$,
      ...useDateFormat({ entityRef: recipeBook }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.recipeBook.id) {
        this.recipeBookService()
          .update(this.recipeBook)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('szakdolgozatApp.recipeBook.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.recipeBookService()
          .create(this.recipeBook)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('szakdolgozatApp.recipeBook.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
