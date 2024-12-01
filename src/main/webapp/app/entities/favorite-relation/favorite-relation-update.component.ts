import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import FavoriteRelationService from './favorite-relation.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';
import RecipeService from '@/entities/recipe/recipe.service';
import { type IRecipe } from '@/shared/model/recipe.model';
import { FavoriteRelation, type IFavoriteRelation } from '@/shared/model/favorite-relation.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FavoriteRelationUpdate',
  setup() {
    const favoriteRelationService = inject('favoriteRelationService', () => new FavoriteRelationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const favoriteRelation: Ref<IFavoriteRelation> = ref(new FavoriteRelation());
    const userService = inject('userService', () => new UserService());
    const users: Ref<Array<any>> = ref([]);

    const recipeService = inject('recipeService', () => new RecipeService());

    const recipes: Ref<IRecipe[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveFavoriteRelation = async favoriteRelationId => {
      try {
        const res = await favoriteRelationService().find(favoriteRelationId);
        favoriteRelation.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.favoriteRelationId) {
      retrieveFavoriteRelation(route.params.favoriteRelationId);
    }

    const initRelationships = () => {
      userService()
        .retrieve()
        .then(res => {
          users.value = res.data;
        });
      recipeService()
        .retrieve()
        .then(res => {
          recipes.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      user: {},
      recipe: {},
    };
    const v$ = useVuelidate(validationRules, favoriteRelation as any);
    v$.value.$validate();

    return {
      favoriteRelationService,
      alertService,
      favoriteRelation,
      previousState,
      isSaving,
      currentLanguage,
      users,
      recipes,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.favoriteRelation.id) {
        this.favoriteRelationService()
          .update(this.favoriteRelation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('szakdolgozatApp.favoriteRelation.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.favoriteRelationService()
          .create(this.favoriteRelation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('szakdolgozatApp.favoriteRelation.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
