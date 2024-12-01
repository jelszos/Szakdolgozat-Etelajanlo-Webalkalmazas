import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RecipeService from './recipe.service';
import { type IRecipe } from '@/shared/model/recipe.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Recipe',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const recipeService = inject('recipeService', () => new RecipeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const recipes: Ref<IRecipe[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRecipes = async () => {
      isFetching.value = true;
      try {
        const res = await recipeService().retrieve();
        recipes.value = res.data;
        console.log(recipes.value);
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRecipes();
    };

    onMounted(async () => {
      await retrieveRecipes();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRecipe) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRecipe = async () => {
      try {
        await recipeService().delete(removeId.value);
        const message = t$('szakdolgozatApp.recipe.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRecipes();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      recipes,
      handleSyncList,
      isFetching,
      retrieveRecipes,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRecipe,
      t$,
    };
  },
});
