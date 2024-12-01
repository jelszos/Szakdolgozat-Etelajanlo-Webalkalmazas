import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RecipeBookRelationService from './recipe-book-relation.service';
import { type IRecipeBookRelation } from '@/shared/model/recipe-book-relation.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RecipeBookRelationDetails',
  setup() {
    const recipeBookRelationService = inject('recipeBookRelationService', () => new RecipeBookRelationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const recipeBookRelation: Ref<IRecipeBookRelation> = ref({});

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

    return {
      alertService,
      recipeBookRelation,

      previousState,
      t$: useI18n().t,
    };
  },
});
