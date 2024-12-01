import { defineComponent, inject, ref } from 'vue';
import RecipeService from '@/entities/recipe/recipe.service';
import { useAlertService } from '@/shared/alert/alert.service';
import { useRoute, useRouter } from 'vue-router';
import { useStore } from '@/store';
import { useI18n } from 'vue-i18n';
import type { IRecipe } from '@/shared/model/recipe.model';
import { functions } from '@/shared/utils/functions';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RecipeDetailsRatings',
  setup() {
    const recipeService = inject('recipeService', () => new RecipeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const ratings = ref([]);
    const recipe: Ref<IRecipe> = ref({});
    const route = useRoute();
    const router = useRouter();
    const store = useStore();
    const previousState = () => router.go(-1);

    const retrieveRecipeRatings = async recipeId => {
      try {
        const res = await recipeService().retrieveRecipeRatings(recipeId);
        console.log(res.data);
        ratings.value = res.data;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };
    const retrieveRecipe = async recipeId => {
      try {
        const res = await recipeService().find(recipeId);
        console.log(res);
        recipe.value = res;
        console.log(recipe.value);
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.recipeId) {
      retrieveRecipeRatings(route.params.recipeId);
      retrieveRecipe(route.params.recipeId);
    }
    return {
      store,
      alertService,
      ratings,
      recipe,

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
  },
});
