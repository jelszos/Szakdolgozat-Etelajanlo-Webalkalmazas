import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import IngredientService from './ingredient.service';
import { type IIngredient } from '@/shared/model/ingredient.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IngredientDetails',
  setup() {
    const ingredientService = inject('ingredientService', () => new IngredientService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const ingredient: Ref<IIngredient> = ref({});

    const retrieveIngredient = async ingredientId => {
      try {
        const res = await ingredientService().find(ingredientId);
        ingredient.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.ingredientId) {
      retrieveIngredient(route.params.ingredientId);
    }

    return {
      alertService,
      ingredient,

      previousState,
      t$: useI18n().t,
    };
  },
});
