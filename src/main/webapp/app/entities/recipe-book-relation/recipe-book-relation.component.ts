import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RecipeBookRelationService from './recipe-book-relation.service';
import { type IRecipeBookRelation } from '@/shared/model/recipe-book-relation.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RecipeBookRelation',
  setup() {
    const { t: t$ } = useI18n();
    const recipeBookRelationService = inject('recipeBookRelationService', () => new RecipeBookRelationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const recipeBookRelations: Ref<IRecipeBookRelation[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRecipeBookRelations = async () => {
      isFetching.value = true;
      try {
        const res = await recipeBookRelationService().retrieve();
        recipeBookRelations.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRecipeBookRelations();
    };

    onMounted(async () => {
      await retrieveRecipeBookRelations();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRecipeBookRelation) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRecipeBookRelation = async () => {
      try {
        await recipeBookRelationService().delete(removeId.value);
        const message = t$('szakdolgozatApp.recipeBookRelation.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRecipeBookRelations();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      recipeBookRelations,
      handleSyncList,
      isFetching,
      retrieveRecipeBookRelations,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRecipeBookRelation,
      t$,
    };
  },
});
