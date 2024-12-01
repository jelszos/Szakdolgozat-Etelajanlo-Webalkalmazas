import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import InstructionService from './instruction.service';
import { type IInstruction } from '@/shared/model/instruction.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Instruction',
  setup() {
    const { t: t$ } = useI18n();
    const instructionService = inject('instructionService', () => new InstructionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const instructions: Ref<IInstruction[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveInstructions = async () => {
      isFetching.value = true;
      try {
        const res = await instructionService().retrieve();
        instructions.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveInstructions();
    };

    onMounted(async () => {
      await retrieveInstructions();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IInstruction) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeInstruction = async () => {
      try {
        await instructionService().delete(removeId.value);
        const message = t$('szakdolgozatApp.instruction.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveInstructions();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      instructions,
      handleSyncList,
      isFetching,
      retrieveInstructions,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeInstruction,
      t$,
    };
  },
});
