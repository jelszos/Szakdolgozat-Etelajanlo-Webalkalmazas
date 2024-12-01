import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import InstructionService from './instruction.service';
import { type IInstruction } from '@/shared/model/instruction.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'InstructionDetails',
  setup() {
    const instructionService = inject('instructionService', () => new InstructionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const instruction: Ref<IInstruction> = ref({});

    const retrieveInstruction = async instructionId => {
      try {
        const res = await instructionService().find(instructionId);
        instruction.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.instructionId) {
      retrieveInstruction(route.params.instructionId);
    }

    return {
      alertService,
      instruction,

      previousState,
      t$: useI18n().t,
    };
  },
});
