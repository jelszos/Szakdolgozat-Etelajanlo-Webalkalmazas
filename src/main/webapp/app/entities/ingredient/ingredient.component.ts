import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import IngredientService from './ingredient.service';
import { type IIngredient } from '@/shared/model/ingredient.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Ingredient',
  setup() {
    const { t: t$ } = useI18n();
    const ingredientService = inject('ingredientService', () => new IngredientService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const ingredients: Ref<IIngredient[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveIngredients = async () => {
      isFetching.value = true;
      try {
        const res = await ingredientService().retrieve();
        ingredients.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveIngredients();
    };

    onMounted(async () => {
      await retrieveIngredients();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IIngredient) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeIngredient = async () => {
      try {
        await ingredientService().delete(removeId.value);
        const message = t$('szakdolgozatApp.ingredient.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveIngredients();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      ingredients,
      handleSyncList,
      isFetching,
      retrieveIngredients,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeIngredient,
      t$,
    };
  },
});
