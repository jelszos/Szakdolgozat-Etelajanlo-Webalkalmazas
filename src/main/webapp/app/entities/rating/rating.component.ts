import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RatingService from './rating.service';
import { type IRating } from '@/shared/model/rating.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Rating',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const ratingService = inject('ratingService', () => new RatingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const ratings: Ref<IRating[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRatings = async () => {
      isFetching.value = true;
      try {
        const res = await ratingService().retrieve();
        ratings.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRatings();
    };

    onMounted(async () => {
      await retrieveRatings();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRating) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRating = async () => {
      try {
        await ratingService().delete(removeId.value);
        const message = t$('szakdolgozatApp.rating.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRatings();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      ratings,
      handleSyncList,
      isFetching,
      retrieveRatings,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRating,
      t$,
    };
  },
});
