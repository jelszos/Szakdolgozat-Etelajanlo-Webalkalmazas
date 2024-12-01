import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RecipeBookService from './recipe-book.service';
import { type IRecipeBook } from '@/shared/model/recipe-book.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RecipeBook',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const recipeBookService = inject('recipeBookService', () => new RecipeBookService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const recipeBooks: Ref<IRecipeBook[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRecipeBooks = async () => {
      isFetching.value = true;
      try {
        const res = await recipeBookService().retrieve();
        recipeBooks.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRecipeBooks();
    };

    onMounted(async () => {
      await retrieveRecipeBooks();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRecipeBook) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRecipeBook = async () => {
      try {
        await recipeBookService().delete(removeId.value);
        const message = t$('szakdolgozatApp.recipeBook.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRecipeBooks();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      recipeBooks,
      handleSyncList,
      isFetching,
      retrieveRecipeBooks,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRecipeBook,
      t$,
    };
  },
});
