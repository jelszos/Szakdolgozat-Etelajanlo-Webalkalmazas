import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RecipeBookRelationService from './recipe-book-relation.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import RecipeService from '@/entities/recipe/recipe.service';
import { type IRecipe } from '@/shared/model/recipe.model';
import RecipeBookService from '@/entities/recipe-book/recipe-book.service';
import { type IRecipeBook } from '@/shared/model/recipe-book.model';
import { type IRecipeBookRelation, RecipeBookRelation } from '@/shared/model/recipe-book-relation.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RecipeBookRelationUpdate',
  setup() {
    const recipeBookRelationService = inject('recipeBookRelationService', () => new RecipeBookRelationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const recipeBookRelation: Ref<IRecipeBookRelation> = ref(new RecipeBookRelation());

    const recipeService = inject('recipeService', () => new RecipeService());

    const recipes: Ref<IRecipe[]> = ref([]);

    const recipeBookService = inject('recipeBookService', () => new RecipeBookService());

    const recipeBooks: Ref<IRecipeBook[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveRecipeBookRelation = async recipeBookRelationId => {
      try {
        const res = await recipeBookRelationService().find(recipeBookRelationId);
        recipeBookRelation.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.recipeBookRelationId) {
      retrieveRecipeBookRelation(route.params.recipeBookRelationId);
    }

    const initRelationships = () => {
      recipeService()
        .retrieve()
        .then(res => {
          recipes.value = res.data;
        });
      recipeBookService()
        .retrieve()
        .then(res => {
          recipeBooks.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      recipe: {},
      recipeBook: {},
    };
    const v$ = useVuelidate(validationRules, recipeBookRelation as any);
    v$.value.$validate();

    return {
      recipeBookRelationService,
      alertService,
      recipeBookRelation,
      previousState,
      isSaving,
      currentLanguage,
      recipes,
      recipeBooks,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.recipeBookRelation.id) {
        this.recipeBookRelationService()
          .update(this.recipeBookRelation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('szakdolgozatApp.recipeBookRelation.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.recipeBookRelationService()
          .create(this.recipeBookRelation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('szakdolgozatApp.recipeBookRelation.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
