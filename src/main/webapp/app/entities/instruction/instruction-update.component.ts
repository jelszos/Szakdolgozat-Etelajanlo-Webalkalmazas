import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import InstructionService from './instruction.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import RecipeService from '@/entities/recipe/recipe.service';
import { type IRecipe } from '@/shared/model/recipe.model';
import { type IInstruction, Instruction } from '@/shared/model/instruction.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'InstructionUpdate',
  setup() {
    const instructionService = inject('instructionService', () => new InstructionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const instruction: Ref<IInstruction> = ref(new Instruction());

    const recipeService = inject('recipeService', () => new RecipeService());

    const recipes: Ref<IRecipe[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      recipeService()
        .retrieve()
        .then(res => {
          recipes.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      title: {},
      requiredTime: {},
      description: {},
      recipe: {},
    };
    const v$ = useVuelidate(validationRules, instruction as any);
    v$.value.$validate();

    return {
      instructionService,
      alertService,
      instruction,
      previousState,
      isSaving,
      currentLanguage,
      recipes,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.instruction.id) {
        this.instructionService()
          .update(this.instruction)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('szakdolgozatApp.instruction.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.instructionService()
          .create(this.instruction)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('szakdolgozatApp.instruction.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
