import Vue, { type Ref, defineComponent, inject, ref, computed } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RecipeService from './recipe.service';
import { useDateFormat } from '@/shared/composables';
import { type IRecipe } from '@/shared/model/recipe.model';
import { useAlertService } from '@/shared/alert/alert.service';
import type { IRating } from '@/shared/model/rating.model';
import { Rating } from '@/shared/model/rating.model';
import RatingService from '@/entities/rating/rating.service';
import { useStore } from '@/store';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { functions } from '@/shared/utils/functions';
import LoginService from '@/account/login.service';
import FavoriteRelationService from '@/entities/favorite-relation/favorite-relation.service';
import CreateRecipeBookForm from '@/core/components/CreateRecipeBookForm.vue';
import MarkOrCreateRB from '@/core/components/MarkOrCreateRB.vue';

export default defineComponent({
  name: 'RecipeDetails',
  components: { MarkOrCreateRB, CreateRecipeBookForm, FontAwesomeIcon },
  compatConfig: { MODE: 3 },
  setup() {
    const dateFormat = useDateFormat();
    const recipeService = inject('recipeService', () => new RecipeService());
    const loginService = inject('loginService', () => new LoginService());
    const alertService = inject('alertService', () => useAlertService(), true);
    const ratingService = inject('ratingService', () => new RatingService(), true);
    const favoriteService = inject('favoriteService', () => new FavoriteRelationService());

    const route = useRoute();
    const router = useRouter();
    const store = useStore();

    const isAddFavModalData = ref({
      isOpen: false,
      recipe: {},
    });

    const previousState = () => router.go(-1);
    const recipe: Ref<IRecipe> = ref({});
    const visibilityRatingEditForm = ref(false);
    const rating: Ref<IRating> = ref(new Rating());
    const isEditing: Ref<boolean> = ref(false);
    const showAllRatings = ref(false);
    const newRecipeBFormVisible: Ref<boolean> = ref(false);

    const retrieveRecipe = async recipeId => {
      try {
        const res = await recipeService().find(recipeId);
        res.ratings = res.ratings?.sort((ratingA, ratingB) => {
          const isUserRatingA = ratingA?.user?.id === store?.account?.id;
          const isUserRatingB = ratingB?.user?.id === store?.account?.id;

          if (isUserRatingA && !isUserRatingB) {
            return -1;
          }
          if (!isUserRatingA && isUserRatingB) {
            return 1;
          }
          return 0;
        });
        recipe.value = res;
      } catch (error) {
        alertService.showHttpError(error?.response);
      }
    };

    const limitedRatings = computed(() => {
      return showAllRatings.value ? recipe.value.ratings : recipe.value.ratings?.slice(0, 2);
    });

    if (route.params?.recipeId) {
      retrieveRecipe(route.params.recipeId);
    }

    function isRatingFormVisible() {
      const isVisible = recipe?.value?.ratings?.some(
        rating => rating?.user?.id == store?.account?.id || recipe?.value?.user?.id == store?.account?.id,
      );
      return !isVisible;
    }

    function isMyComment(comment: IRating) {
      return comment?.user?.id == store?.account?.id;
    }

    function toggleEditRatingForm(rating: Rating) {
      isEditing.value = true;
      ratingFormData.value = { ...rating };
      console.log(ratingFormData.value);
      visibilityRatingEditForm.value = !visibilityRatingEditForm.value;
    }

    const ratingFormData = ref({
      title: '',
      description: '',
      rate: 0,
      imageUrl: '',
      recipe: recipe.value,
    });

    function resetForm() {
      ratingFormData.value = {
        title: '',
        description: '',
        rate: 0,
        imageUrl: '',
        recipe: recipe.value,
      };
    }

    function isFormValid() {
      return ratingFormData.value.rate; //&& ratingFormData.value.title && ratingFormData.value.description
    }
    async function submitRating() {
      if (!store?.account) loginService.openLogin();
      if (isFormValid()) {
        if (!isEditing.value) {
          rating.value = ratingFormData.value;
          console.log('data', ratingFormData.value);
          await ratingService()
            .createWithRecipe(rating.value, recipe.value.id)
            .then(param => {
              recipe.value.ratings = [param, ...recipe.value.ratings];
              console.log(recipe.value.ratings);
              alertService.showSuccess('Rating created successfully!');
            })
            .catch(error => {
              alertService.showHttpError(error.response);
            });
        } else {
          rating.value = ratingFormData.value;
          rating.value.recipe = recipe.value;
          ratingService()
            .update(rating.value)
            .then(param => {
              const index = recipe.value.ratings?.findIndex(rating => rating.id === param.id);
              if (index !== -1) {
                Vue.set(recipe.value.ratings, index, param);
              }
              alertService.showSuccess('Updated successfully!');
            })
            .catch(error => {
              alertService.showHttpError(error.response);
            });
        }

        resetForm();
        visibilityRatingEditForm.value = false;
      }
    }

    function deleteRating(ratingId: number) {
      if (!ratingId) {
        alertService.showError('No rating ID found!');
      }
      ratingService()
        .delete(ratingId)
        .then(res => {
          if (res.status != 204) {
            return alertService.showError('Something went wrong. Please try it again later!');
          }

          const index = recipe.value.ratings?.findIndex(rating => rating.id === ratingId);
          if (index !== -1) {
            recipe.value.ratings?.splice(index, 1);
            alertService.showSuccess('Deleted your rating successfully!');
          }
        });
    }

    function onFileChanged($event: Event) {
      const target = $event.target as HTMLInputElement;

      if (target && target.files) {
        const formData = new FormData();
        const file = target.files[0];
        formData.append('file', file);

        recipeService()
          .uploadImage(formData)
          .then(res => {
            //do something
            ratingFormData.value.imageUrl = res;
            console.log('img path res', res); // -> img path
          });
      }
    }

    function deleteImgFromRating() {
      ratingFormData.value.imageUrl = '';
    }

    return {
      ...dateFormat,
      store,
      alertService,
      recipe,
      submitRating,
      ratingFormData,
      visibilityRatingEditForm,
      toggleEditRatingForm,
      isMyComment,
      isRatingFormVisible,
      deleteRating,
      showAllRatings,
      limitedRatings,
      onFileChanged,
      deleteImgFromRating,
      loginService,
      favoriteService,
      isAddFavModalData,
      newRecipeBFormVisible,

      previousState,
      t$: useI18n().t,
    };
  },
  computed: {
    functions() {
      return functions;
    },
  },
  methods: {
    getAvatarImg: number => functions.getAvatarImg(number),
    formatFoodCategory(category) {
      if (!category) return '';
      return category.replace(/_/g, ' ').replace(/\b\w/g, char => char.toUpperCase());
    },
    toggleFavorite() {
      if (this.store?.account) {
        this.favoriteService()
          .create({ recipe: this.recipe })
          .then(res => {
            if (res.status == 204 || res.status == 201) {
              this.recipe.isFavorite = !this.recipe.isFavorite;
            }
          });
        return;
      }
      this.loginService?.openLogin();
    },
    markRecipe() {
      if (this.store?.account) {
        this.isAddFavModalData.recipe = this.recipe;
        this.isAddFavModalData.isOpen = true;
        return;
      }
      this.loginService?.openLogin();
    },
    setMarkModalTrue() {
      this.isAddFavModalData.isOpen = true;
    },

    setMarkModalFalse() {
      this.isAddFavModalData.isOpen = false;
    },
  },
});
