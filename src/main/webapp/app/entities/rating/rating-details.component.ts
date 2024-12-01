import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RatingService from './rating.service';
import { useDateFormat } from '@/shared/composables';
import { type IRating } from '@/shared/model/rating.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RatingDetails',
  setup() {
    const dateFormat = useDateFormat();
    const ratingService = inject('ratingService', () => new RatingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rating: Ref<IRating> = ref({});

    const retrieveRating = async ratingId => {
      try {
        const res = await ratingService().find(ratingId);
        rating.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.ratingId) {
      retrieveRating(route.params.ratingId);
    }

    return {
      ...dateFormat,
      alertService,
      rating,

      previousState,
      t$: useI18n().t,
    };
  },
});
