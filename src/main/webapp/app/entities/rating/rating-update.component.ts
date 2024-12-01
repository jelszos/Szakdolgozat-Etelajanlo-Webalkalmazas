import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RatingService from './rating.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';
import RecipeService from '@/entities/recipe/recipe.service';
import { type IRecipe } from '@/shared/model/recipe.model';
import { type IRating, Rating } from '@/shared/model/rating.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RatingUpdate',
  setup() {
    const ratingService = inject('ratingService', () => new RatingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rating: Ref<IRating> = ref(new Rating());
    const userService = inject('userService', () => new UserService());
    const users: Ref<Array<any>> = ref([]);

    const recipeService = inject('recipeService', () => new RecipeService());

    const recipes: Ref<IRecipe[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveRating = async ratingId => {
      try {
        const res = await ratingService().find(ratingId);
        res.createdDate = new Date(res.createdDate);
        rating.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.ratingId) {
      retrieveRating(route.params.ratingId);
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
      rate: {},
      title: {},
      description: {},
      imageUrl: {},
      createdDate: {},
      user: {},
      recipe: {},
    };
    const v$ = useVuelidate(validationRules, rating as any);
    v$.value.$validate();

    return {
      ratingService,
      alertService,
      rating,
      previousState,
      isSaving,
      currentLanguage,
      users,
      recipes,
      v$,
      ...useDateFormat({ entityRef: rating }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.rating.id) {
        this.ratingService()
          .update(this.rating)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('szakdolgozatApp.rating.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.ratingService()
          .create(this.rating)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('szakdolgozatApp.rating.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
