import { type ComputedRef, type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import type LoginService from '@/account/login.service';
import type { IRecipe } from '@/shared/model/recipe.model';
import RecipeService from '@/entities/recipe/recipe.service';
import { Carousel, Slide, Pagination, Navigation } from 'vue3-carousel';
import 'vue3-carousel/dist/carousel.css';
import foodCategoriesJsonSource from '../../../i18n/en/foodCategory.json';
import { functions } from '@/shared/utils/functions';
import Recipe from '@/entities/recipe/recipe.component';
import RecipeBookService from '@/entities/recipe-book/recipe-book.service';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import HomeCarousel from '@/core/components/HomeCarousel.vue';
import img1 from '../../../content/images/img1.jpg';
import img3 from '../../../content/images/img3.svg';
import img2 from '../../../content/images/img2.svg';
import img4 from '../../../content/images/img4.jpg';
import headerBg from '../../../content/images/header-bg.svg';

export default defineComponent({
  components: { HomeCarousel, FontAwesomeIcon, Recipe, Slide, Carousel, Pagination, Navigation },
  compatConfig: { MODE: 3 },
  setup() {
    const loginService = inject<LoginService>('loginService');
    const recipeService = inject('recipeService', () => new RecipeService());
    const recipeBookService = inject('recipeBookService', () => new RecipeBookService());

    const authenticated = inject<ComputedRef<boolean>>('authenticated');
    const username = inject<ComputedRef<string>>('currentUsername');

    const foodCategoriesJson = foodCategoriesJsonSource.szakdolgozatApp.FoodCategory;
    const foodCategories = ref([]);
    const foodTypes = ref([]);

    const latestRecipes: Ref<IRecipe[]> = ref([]);
    const highestFavoriteCountRecipes: Ref<IRecipe[]> = ref([]);
    const recipeBooks: Ref<IRecipeBook[]> = ref([]);
    const sameFoodCategoryRecipes: Ref<IRecipe[]> = ref([]);
    const finalChosenFoodCategory = ref(null);

    const openLogin = () => {
      loginService.openLogin();
    };

    const clear = () => {};

    const isFetching = ref(false);

    const retrieveRecipes = async () => {
      isFetching.value = true;
      try {
        const resLatestRecipes = await recipeService().retrieveLatestRecipes();
        const resHighestFavoriteCountRecipes = await recipeService().retrieveHighestFavoriteCountRecipes();
        const resSameFoodCategory = await getRandomFoodCategoryRecipes();
        const resRecipeBooks = await recipeBookService().retrieve();
        latestRecipes.value = resLatestRecipes.data;
        highestFavoriteCountRecipes.value = resHighestFavoriteCountRecipes.data;
        sameFoodCategoryRecipes.value = resSameFoodCategory.data;
        recipeBooks.value = resRecipeBooks.data;
      } catch (err) {
      } finally {
        isFetching.value = false;
      }
    };
    async function getRandomFoodCategoryRecipes() {
      function getRandomFoodCategory() {
        const categories = Object.values(foodCategoriesJson).filter(value => value !== null);
        const randomIndex = Math.floor(Math.random() * categories.length);
        finalChosenFoodCategory.value = categories[randomIndex];
        //console.log("foodCategory:  " + functions.getFoodCategory(randomIndex));
        return categories[randomIndex];
      }
      let resSameFoodCategory = [];
      const chosenFoodCategory = getRandomFoodCategory();
      finalChosenFoodCategory.value = functions.formatFoodCategoryToLabel(chosenFoodCategory);
      resSameFoodCategory = await recipeService().retrieveSameFoodCategoryRecipes(chosenFoodCategory);
      //console.log(chosenFoodCategory);
      return resSameFoodCategory;
    }
    const handleSyncList = () => {
      retrieveRecipes();
    };

    onMounted(async () => {
      await retrieveRecipes();
    });

    const configLatestRecipes = {
      itemsToShow: 0.5,
      snapAlign: 'center',
      autoplay: 3000,
      pauseAutoplayOnHover: true,
      wrapAround: true,
      breakpointMode: 'carousel',
      breakpoints: {
        0: {
          itemsToShow: 1,
          snapAlign: 'center',
        },
        500: {
          itemsToShow: 2,
          snapAlign: 'center',
        },
        976: {
          itemsToShow: 3,
          snapAlign: 'start',
        },
      },
    };
    const configFoodCategories = {
      itemsToShow: 2, // Show 2 items by default
      snapAlign: 'start',
      pauseAutoplayOnHover: true,
      wrapAround: true,
      breakpointMode: 'carousel',
    };
    const configSameFoodCategoryRecipes = {
      itemsToShow: 1,
      snapAlign: 'center',
      autoplay: 3000,
      wrapAround: true,
      pauseAutoplayOnHover: true,
      breakpointMode: 'carousel',
      breakpoints: {
        0: {
          itemsToShow: 1,
          snapAlign: 'center',
        },
        500: {
          itemsToShow: 2,
          snapAlign: 'center',
        },
        976: {
          itemsToShow: 3,
          snapAlign: 'start',
        },
      },
    };

    return {
      authenticated,
      username,
      retrieveRecipes,
      clear,
      latestRecipes,
      configLatestRecipes,
      configSameFoodCategoryRecipes,
      configFoodCategories,
      sameFoodCategoryRecipes,
      finalChosenFoodCategory,
      highestFavoriteCountRecipes,
      foodTypes,
      foodCategories,
      recipeBooks,
      isFetching,
      handleSyncList,
      openLogin,
      img1,
      img3,
      img2,
      img4,
      headerBg,
      t$: useI18n().t,
    };
  },
  computed: {
    functions() {
      return functions;
    },
  },
  mounted() {
    this.loadFoodTypes();
    this.loadFoodCategories();
  },
  methods: {
    loadFoodTypes() {
      this.foodTypes = functions.getAllFoodTypes();
    },
    loadFoodCategories() {
      this.foodCategories = functions.getAllFoodCategories();
    },
    formatFoodCategoryToLabel: category => functions.formatFoodCategoryToLabel(category),
    getAvatarImg: number => functions.getAvatarImg(number),
    getFoodType: number => functions.getFoodType(number),
    getFoodCategory: number => functions.getFoodCategory(number),
  },
});
