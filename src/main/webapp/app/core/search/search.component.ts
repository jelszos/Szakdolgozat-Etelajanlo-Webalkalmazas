import Vue, { computed, type ComputedRef, defineComponent, inject, onMounted, ref, watch } from 'vue';
import { useI18n } from 'vue-i18n';

import type LoginService from '@/account/login.service';
import RecipeService from '@/entities/recipe/recipe.service';
import IngredientService from '@/entities/ingredient/ingredient.service';
import foodTypesJson from '../../../i18n/en/foodType.json';
import foodCategoriesJson from '../../../i18n/en/foodCategory.json';

import type { IRecipe } from '@/shared/model/recipe.model';
import { useStore } from '@/store';
import FavoriteRelationService from '@/entities/favorite-relation/favorite-relation.service';
import { useRoute, useRouter } from 'vue-router';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import RecipeBookService from '@/entities/recipe-book/recipe-book.service';
import type { IRecipeBook } from '@/shared/model/recipe-book.model';
import AlertService, { useAlertService } from '@/shared/alert/alert.service';
import { functions } from '@/shared/utils/functions.ts';
import CreateRecipeBookForm from '@/core/components/CreateRecipeBookForm.vue';
import MarkOrCreateRB from '@/core/components/MarkOrCreateRB.vue';
import headerBg from '/content/images/header-bg.svg';
import RecipeCard from '@/core/components/RecipeCard.vue';

// noinspection TypeScriptValidateTypes
export default defineComponent({
  components: { MarkOrCreateRB, RecipeCard, CreateRecipeBookForm, FontAwesomeIcon },
  setup() {
    const { t } = useI18n();
    const route = useRoute();
    const router = useRouter();
    const loginService = inject<LoginService>('loginService');
    const recipeService = inject('recipeService', () => new RecipeService());
    const ingredientService = inject('ingredientService', () => new IngredientService());
    const favoriteService = inject('favoriteService', () => new FavoriteRelationService());
    const recipebookService = inject('recipeBookService', () => new RecipeBookService());
    const alertService = inject('alertService', () => useAlertService(), true);
    const store = useStore();
    const isAddFavModalData = ref({
      isOpen: false,
      recipe: {},
    });

    const authenticated = inject<ComputedRef<boolean>>('authenticated');

    const searchFormData = {
      foodCategories: route.query.foodCategories || [],
      foodTypes: route.query.foodTypes || [],
      recipeTitle: route.query.recipeTitle || '',
      ingredientNames: route.query.ingredientName || [],
      page: route.query.page || 0,
      sort: route.query.sort || 'createdDate,asc',
    };
    const sortFormData = ref(sortFormatterQuery(route.query.sort || 'createdDate,asc'));
    const sortOptions = [
      { label: t('search.input.title'), value: 'title' },
      { label: t('search.input.creationDate'), value: 'createdDate' },
    ];
    const didSearch = ref(false);
    const recipes: Ref<IRecipe[]> = ref([]);
    const filteredRecipes: Ref<IRecipe[]> = ref(['Nill']);

    const userRecipeBooks: Ref<IRecipeBook> = ref([]);
    const newRecipeBFormVisible: Ref<boolean> = ref(false);

    let foodTypes = functions.getAllFoodTypes();
    let foodCategories = functions.getAllFoodCategories();
    const ingredients = ref([]);
    const foodTypeValue = [];
    const foodCategoryValue = [];
    const ingredientValue = [];
    foodTypes = keepNonNullValues(foodTypes);
    foodCategories = keepNonNullValues(foodCategories);
    ingredients.value = keepNonNullValues(ingredients.value);
    const foodTypesArray = Object.values(foodTypes);
    const foodCategoriesArray = Object.values(foodCategories);
    const ingredientsArray = computed(() => {
      return Object.values(ingredients.value);
    });

    watch(
      () => route.query.page,
      newPage => {
        console.log(route.query);
        paginationInfo.value.currentPage = newPage ? parseInt(newPage) : 0;
        searchFormData.page = newPage ? parseInt(newPage) : 0;
        search();
      },
    );

    const paginationInfo = ref({
      totalElements: 0,
      currentPage: parseInt(<string>route.query.page) - 1,
      totalPages: 0,
    });

    const availableFoodTypes = computed(() => {
      return foodTypesArray.map(foodType => {
        return { text: foodType.label, value: foodType.value };
      });
    });

    const availableFoodCategories = computed(() => {
      return foodCategoriesArray.map(category => {
        return { text: category.label, value: category.value };
      });
    });
    const availableIngredients = computed(() => {
      return ingredientsArray.value.filter(opt => ingredientValue.indexOf(opt) === -1).sort((a, b) => a?.localeCompare(b));
    });

    const clear = () => {};

    const isFetching = ref(false);

    const retrieveRecipes = async () => {
      isFetching.value = true;
      try {
        const res = await recipeService().retrieve();
        recipes.value = res.data;
      } catch (err) {
      } finally {
        isFetching.value = false;
      }
    };

    const retrieveUniqueIngredients = async () => {
      isFetching.value = true;
      try {
        const res = await ingredientService().retrieveUniqueIngredients();
        ingredients.value = res.data;
      } catch (err) {
      } finally {
        isFetching.value = false;
      }
    };

    onMounted(async () => {
      await retrieveUniqueIngredients();
      await search();
      //this.$forceUpdate();
    });

    function onSortChange() {
      const newSortQuery = sortFormatterPage(sortFormData.value.sortBy, sortFormData.value.sortDirection);
      router.push({
        query: {
          ...route.query,
          sort: newSortQuery,
        },
      });
      search();
    }
    watch(
      () => route.query.sort,
      newSortQuery => {
        if (newSortQuery) {
          sortFormData.value = sortFormatterQuery(newSortQuery);
        }
      },
    );

    function sortFormatterQuery(sortQuery: string) {
      const [sortBy, sortDirectionRaw] = sortQuery.split(',');
      const sortDirection = sortDirectionRaw === 'asc';
      return { sortBy, sortDirection };
    }

    function sortFormatterPage(sortBy: string, sortDirection: boolean): string {
      const tmpSortDirection = sortDirection ? 'asc' : 'desc';
      console.log(tmpSortDirection);
      return `${sortBy},${tmpSortDirection}`;
    }

    const search = async () => {
      console.log('search sort: ' + sortFormData.value.sortDirection);
      filteredRecipes.value = [];
      searchFormData.sort = sortFormatterPage(sortFormData.value.sortBy, sortFormData.value.sortDirection);
      const data = await recipeService()
        .searchRecipes(searchFormData)
        .then(res => {
          paginationInfo.value = {
            ...paginationInfo.value,
            totalElements: res?.data?.totalElements,
            currentPage: res?.data?.number,
            totalPages: res.data.totalPages,
          };
          filteredRecipes.value = res.data.content;
          didSearch.value = res.data.content.length > 0;
        });
      // Update the URL with the new query parameters (including sort)
      router.push({
        query: {
          ...route.query,
          page: searchFormData.page,
          sort: sortFormatterPage(sortFormData.value.sortBy, sortFormData.value.sortDirection),
          foodTypes: searchFormData.foodTypes,
          foodCategories: searchFormData.foodCategories,
          recipeTitle: searchFormData.recipeTitle,
          ingredientNames: searchFormData.ingredientNames,
        },
      });
    };

    function updateSortQuery() {
      // Whenever the sort criteria changes, update the route query
      const newSortQuery = sortFormatterPage(sortFormData.value.sortBy, sortFormData.value.sortDirection);
      router.push({
        query: {
          ...route.query,
          sort: newSortQuery,
        },
      });
    }

    function toggleSortDirection() {
      // Toggle sort direction between ascending and descending
      sortFormData.value.sortDirection = !sortFormData.value.sortDirection;
      updateSortQuery();
      search();
    }

    function keepNonNullValues(input: Record<string, string>): Record<string, string> {
      return Object.fromEntries(Object.entries(input).filter(([key, value]) => key != 'null' && value != ''));
    }

    function markRecipe(recipe) {
      if (store.account) {
        isAddFavModalData.value.recipe = recipe;
        isAddFavModalData.value.isOpen = true;
        return;
      }
      loginService?.openLogin();
    }

    function closeMarkModal() {
      isAddFavModalData.value.isOpen = false;
    }

    function toggleFavorite(recipe) {
      if (store.account) {
        favoriteService()
          .create({ recipe })
          .then(res => {
            if (res.status == 204 || res.status == 201) {
              const recipeInList = filteredRecipes.value.find(r => r.id === recipe.id);
              console.log(recipeInList);
              if (recipeInList) {
                Vue.set(recipeInList, 'isFavorite', !recipeInList.isFavorite);
                Vue.set(
                  recipeInList,
                  'favoriteCount',
                  recipeInList.isFavorite ? recipeInList.favoriteCount + 1 : recipeInList.favoriteCount - 1,
                );
              }
            }
          });
        return;
      }
      loginService?.openLogin();
    }

    function onPageChange(pNumber) {
      const page = parseInt(pNumber.target.text) - 1;
      router.push({ query: { ...route.query, page } });
      paginationInfo.value.currentPage = page;
    }

    async function getRecipeBooksByUser(recipeId: number) {
      await recipebookService()
        .retrieveIsRecipeInList(recipeId)
        .then(res => {
          userRecipeBooks.value = res.data;
        });
    }

    function addOrRemoveFromRecipeBook(recipeBookId) {
      recipebookService()
        .addOrRemoveRecipe(recipeBookId, isAddFavModalData.value.recipe?.id)
        .then(res => {
          if (res.status == 201) {
            alertService.showSuccess('Added successfully');
          } else if (res.status == 204) {
            alertService.showInfo('Removed successfully');
          }
        })
        .catch(err => {
          alertService.showError('Something went wrong! Try it again later.');
        });
    }

    function toggleNewRecipeBVisible() {
      newRecipeBFormVisible.value = !newRecipeBFormVisible.value;
    }
    //need for nav link generation just leave it as it is.
    function pLinkGen() {
      return `#`;
    }

    function addRecipeBookToList(recipeBook: IRecipeBook) {
      if (!recipeBook) {
        console.error('Recipe book is null!');
        return;
      }
      userRecipeBooks.value = [...userRecipeBooks.value, recipeBook];
    }

    return {
      authenticated,
      searchFormData,
      recipes,
      filteredRecipes,
      foodTypeValue,
      foodCategoryValue,
      foodCategories,
      foodTypes,
      ingredientValue,
      availableFoodTypes,
      availableFoodCategories,
      availableIngredients,
      isFetching,
      didSearch,
      paginationInfo,
      isAddFavModalData,
      userRecipeBooks,
      newRecipeBFormVisible,
      sortFormData,
      onSortChange,
      toggleSortDirection,
      sortOptions,
      addRecipeBookToList,
      closeMarkModal,

      headerBg,
      pLinkGen,
      markRecipe,
      toggleNewRecipeBVisible,
      addOrRemoveFromRecipeBook,
      onPageChange,
      search,
      toggleFavorite,
      retrieveRecipes,
      clear,
      t$: useI18n().t,
    };
  },
  computed: {
    functions() {
      return functions;
    },
  },
  compatConfig: { MODE: 3 },
  methods: {
    getAvatarImg: number => functions.getAvatarImg(number),
    formatFoodCategory(category) {
      if (!category) return '';
      return category.replace(/_/g, ' ').replace(/\b\w/g, char => char.toUpperCase());
    },
  },
});
