import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import FavoriteRelationService from './favorite-relation.service';
import { type IFavoriteRelation } from '@/shared/model/favorite-relation.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FavoriteRelationDetails',
  setup() {
    const favoriteRelationService = inject('favoriteRelationService', () => new FavoriteRelationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const favoriteRelation: Ref<IFavoriteRelation> = ref({});

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

    return {
      alertService,
      favoriteRelation,

      previousState,
      t$: useI18n().t,
    };
  },
});
