import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import FavoriteRelationService from './favorite-relation.service';
import { type IFavoriteRelation } from '@/shared/model/favorite-relation.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FavoriteRelation',
  setup() {
    const { t: t$ } = useI18n();
    const favoriteRelationService = inject('favoriteRelationService', () => new FavoriteRelationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const favoriteRelations: Ref<IFavoriteRelation[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveFavoriteRelations = async () => {
      isFetching.value = true;
      try {
        const res = await favoriteRelationService().retrieve();
        favoriteRelations.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveFavoriteRelations();
    };

    onMounted(async () => {
      await retrieveFavoriteRelations();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IFavoriteRelation) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeFavoriteRelation = async () => {
      try {
        await favoriteRelationService().delete(removeId.value);
        const message = t$('szakdolgozatApp.favoriteRelation.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveFavoriteRelations();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      favoriteRelations,
      handleSyncList,
      isFetching,
      retrieveFavoriteRelations,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeFavoriteRelation,
      t$,
    };
  },
});
