import { type Ref, defineComponent, inject, ref, onMounted, watch } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RecipeBookService from './recipe-book.service';
import { useDateFormat } from '@/shared/composables';
import { type IRecipeBook } from '@/shared/model/recipe-book.model';
import { useAlertService } from '@/shared/alert/alert.service';
import RecipeBookCard from '@/core/components/RecipeBookCard.vue';
import RecipeBookRelationService from '@/entities/recipe-book-relation/recipe-book-relation.service';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import headerBg from '../../../content/images/header-bg.svg';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RecipeBookPage',
  components: { FontAwesomeIcon, RecipeBookCard },
  setup() {
    const dateFormat = useDateFormat();
    const recipeBookService = inject('recipeBookService', () => new RecipeBookService());
    const recipeBookRelationService = inject('recipeRelationService', () => new RecipeBookRelationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const searchFormData = {
      title: '',
      description: '',
      page: route.query.page || 0,
      tag: '',
      theme: '',
    };

    const paginationInfo = ref({
      totalElements: 0,
      currentPage: parseInt(<string>route.query.page) - 1,
      totalPages: 0,
    });

    watch(
      () => route.query.page,
      newPage => {
        console.log(route.query);
        paginationInfo.value.currentPage = newPage ? parseInt(newPage) : 0;
        searchFormData.page = newPage ? parseInt(newPage) : 0;
        search();

        router.push({
          query: {
            page: searchFormData.page,
            title: searchFormData.title,
            description: searchFormData.description,
          },
        });
      },
    );

    const previousState = () => router.go(-1);

    const recipeBooks: Ref<IRecipeBook[]> = ref([]);

    const search = async () => {
      try {
        const res = await recipeBookService().retrieve(searchFormData);
        console.log(res);
        recipeBooks.value = res.data.content;
        paginationInfo.value = {
          ...paginationInfo,
          currentPage: res.data.number,
          totalElements: res.data.totalElements,
          totalPages: res.data.totalPages,
        };
        await router.push({
          query: {
            page: searchFormData.page,
            title: searchFormData.title,
            description: searchFormData.description,
          },
        });
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    function pLinkGen() {
      return `#`;
    }
    const retrieveRecipes = async (recipeBookId: string | number) => {
      try {
        const res = await recipeBookRelationService().getAllByRecipeId(recipeBookId);
        recipes.value = res.data;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    onMounted(async () => {
      await search();
      //this.$forceUpdate();
    });

    function onPageChange(pNumber) {
      const page = parseInt(pNumber.target.text) - 1;
      router.push({ query: { ...route.query, page } });
      paginationInfo.value.currentPage = page;
    }

    return {
      ...dateFormat,
      alertService,
      recipeBooks,
      searchFormData,
      paginationInfo,
      headerBg,

      pLinkGen,
      onPageChange,
      search,
      previousState,
      t$: useI18n().t,
    };
  },
  methods: {},
});
