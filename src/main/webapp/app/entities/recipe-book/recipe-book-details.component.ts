import { type Ref, defineComponent, inject, ref, onMounted } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RecipeBookService from './recipe-book.service';
import { useDateFormat } from '@/shared/composables';
import { type IRecipeBook } from '@/shared/model/recipe-book.model';
import { useAlertService } from '@/shared/alert/alert.service';
import { useStore } from '@/store';
import RecipeBookRecipeCard from '@/core/components/RecipeBookRecipeCard.vue';
import RecipeBookRelationService from '@/entities/recipe-book-relation/recipe-book-relation.service';
import type { IRecipe } from '@/shared/model/recipe.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RecipeBookDetails',
  components: { RecipeBookRecipeCard },
  setup() {
    const dateFormat = useDateFormat();
    const recipeBookService = inject('recipeBookService', () => new RecipeBookService());
    const recipeBookRelationService = inject('recipeRelationService', () => new RecipeBookRelationService());
    const alertService = inject('alertService', () => useAlertService(), true);
    const store = useStore();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const recipeBook: Ref<IRecipeBook> = ref({});
    const recipes: Ref<IRecipe[]> = ref([]);

    const retrieveRecipeBook = async recipeBookId => {
      try {
        const res = await recipeBookService().find(recipeBookId);
        recipeBook.value = res;
        //console.log(recipeBook.value);
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    const retrieveRecipes = async (recipeBookId: string | number) => {
      try {
        const res = await recipeBookRelationService().getAllByRecipeId(recipeBookId);
        recipes.value = res.data;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.recipeBookId) {
      retrieveRecipeBook(route.params.recipeBookId);
      retrieveRecipes(route.params.recipeBookId);
    }

    function deleteFromRecipeBook(recipeId: number) {
      recipeBookRelationService()
        .deleteByRecipeNBookId(recipeId, route.params?.recipeBookId)
        .then(res => {
          if (res.status === 204) {
            recipes.value = recipes.value.filter(recipe => recipe.id !== recipeId);
            alertService.showSuccess('Successfully deleted recipe from your recipe book!');
          }
        })
        .catch(err => {
          alertService.showError(err?.response?.data);
        });
    }

    function ownByUser() {
      return store?.account?.id == recipeBook.value.user?.id;
    }

    const isOwnedByUser = ref(false);
    const fetchOwnershipStatus = async () => {
      try {
        const res = await recipeBookService().getOwner(route.params?.recipeBookId);
        console.log('res here', res);
        isOwnedByUser.value = res === store.account.login; // Update the ref
      } catch (error) {
        console.error('Error fetching ownership status:', error);
        isOwnedByUser.value = false; // Default to false in case of error
      }
    };
    onMounted(fetchOwnershipStatus);

    function changeRBStatus() {
      recipeBookService()
        .toggleRecipeBookStatus(recipeBook.value.id)
        .then(res => {
          console.log('res here', res);
        });

      recipeBook.value.isPrivate = !recipeBook.value.isPrivate;
    }

    return {
      ...dateFormat,
      alertService,
      recipeBook,
      recipes,
      deleteFromRecipeBook,
      isOwnedByUser,

      changeRBStatus,
      ownByUser,
      previousState,
      t$: useI18n().t,
    };
  },
  methods: {},
});
