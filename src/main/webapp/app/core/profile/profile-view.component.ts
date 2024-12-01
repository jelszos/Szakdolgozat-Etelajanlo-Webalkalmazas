import { type Ref, defineComponent, inject, ref, watch } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute } from 'vue-router';

import ProfileService from './profile.service';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';
import moment from 'moment';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import RecipeCard from '@/core/components/RecipeCard.vue';
import { functions } from '@/shared/utils/functions';
import headerBg from '../../../content/images/header-bg.svg';
import type { Recipe } from '@/shared/model/recipe.model';
import RecipeService from '@/entities/recipe/recipe.service';
import { showConfirmModal } from '@/core/modals/ConfirmModalService';
import { useStore } from '@/store';
import RecipeBookCard from '@/core/components/RecipeBookCard.vue';

export default defineComponent({
  name: 'ProfileView',
  components: { RecipeBookCard, RecipeCard, FontAwesomeIcon },
  compatConfig: { MODE: 3 },
  setup() {
    const route = useRoute();
    const { formatDateLong: formatDate } = useDateFormat();
    const store = useStore();

    const alertService = inject('alertService', () => useAlertService(), true);
    const profileService = inject('profileService', () => new ProfileService(), true);
    const recipeService = inject('recipeService', () => new RecipeService(), true);
    const activeTab = ref('recipes');

    const user: Ref<any> = ref(null);

    async function loadUser(userId: string) {
      try {
        const response = await profileService.get(userId);
        user.value = response.data;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    }

    loadUser(route.params?.userId);

    watch(
      () => route.params.userId,
      newPage => {
        loadUser(route?.params?.userId);
      },
    );

    return {
      route,
      activeTab,
      store,
      recipeService,
      formatDate,
      alertService,
      profileService,
      user,
      headerBg,
      moment,
      t$: useI18n().t,
    };
  },
  methods: {
    getAvatarImg: number => functions.getAvatarImg(number),

    deleteRecipe(recipe: Recipe) {
      if (!recipe) return;
      showConfirmModal(null, {
        title: this.t$('profile.deletemodal.title'),
        description: this.t$('profile.deletemodal.description'),
        onOk: () => {
          this.recipeService
            .delete(recipe.id)
            .then(res => {
              if (res.status == 204) {
                this.user.recipes = this.user.recipes.filter(rec => rec.id != recipe.id);
              }
            })
            .catch(err => {
              this.alertService.showError(err.response.data);
            });
        },
      });
    },

    deleteRecipeBook(recipeBookId: number) {
      if (!recipeBookId) {
        console.error('Recipe book id null!');
        return;
      }
      this.user.recipeBooks.value = this.user.recipeBooks.value.filter(recipe => recipe.id != recipeBookId);
    },
    changeTab(tabValue: string) {
      this.activeTab = tabValue;
    },
  },
});
