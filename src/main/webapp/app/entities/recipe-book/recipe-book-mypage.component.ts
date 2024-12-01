import { type Ref, defineComponent, inject, ref, onMounted } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RecipeBookService from './recipe-book.service';
import { useDateFormat } from '@/shared/composables';
import { type IRecipeBook, RecipeBook } from '@/shared/model/recipe-book.model';
import { useAlertService } from '@/shared/alert/alert.service';
import { useStore } from '@/store';
import RecipeBookCard from '@/core/components/RecipeBookCard.vue';
import CreateRecipeBookForm from '@/core/components/CreateRecipeBookForm.vue';
import headerBg from '../../../content/images/header-bg.svg';

export default defineComponent({
  name: 'RecipeBookMyPage',
  components: { CreateRecipeBookForm, RecipeBookCard },
  setup() {
    const dateFormat = useDateFormat();
    const recipeBookService = inject('recipeBookService', () => new RecipeBookService());
    const alertService = inject('alertService', () => useAlertService(), true);
    const store = useStore();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const recipeBooks: Ref<IRecipeBook[]> = ref([]);
    const newRecipeBFormVisible: Ref<boolean> = ref(false);

    const retrieveRecipeBooks = async () => {
      try {
        const res = await recipeBookService().retrieveMyRecipeBooks();
        recipeBooks.value = res.data;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    function toggleNewRecipeBVisible() {
      newRecipeBFormVisible.value = !newRecipeBFormVisible.value;
    }

    function addRecipeBookToList(recipeBook: IRecipeBook) {
      if (!recipeBook) {
        console.error('Recipe book is null!');
        return;
      }
      recipeBooks.value = [...recipeBooks.value, recipeBook];
    }

    function removeRecipeBookToList(recipeBookId: number) {
      if (!recipeBookId) {
        console.error('Recipe book id null!');
        return;
      }
      recipeBooks.value = recipeBooks.value.filter(recipe => recipe.id != recipeBookId);
    }

    onMounted(async () => {
      await retrieveRecipeBooks();
      //this.$forceUpdate();
    });

    return {
      ...dateFormat,
      alertService,
      recipeBooks,
      toggleNewRecipeBVisible,
      newRecipeBFormVisible,
      addRecipeBookToList,
      removeRecipeBookToList,
      headerBg,

      previousState,
      t$: useI18n().t,
    };
  },
  computed: {
    RecipeBook() {
      return RecipeBook;
    },
  },
  compatConfig: { MODE: 3 },
  methods: {},
});
