import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import IngredientService from './ingredient.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import InstructionService from '@/entities/instruction/instruction.service';
import { type IInstruction } from '@/shared/model/instruction.model';
import { type IIngredient, Ingredient } from '@/shared/model/ingredient.model';
import { Unit } from '@/shared/model/enumerations/unit.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IngredientUpdate',
  setup() {
    const ingredientService = inject('ingredientService', () => new IngredientService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const ingredient: Ref<IIngredient> = ref(new Ingredient());

    const instructionService = inject('instructionService', () => new InstructionService());

    const instructions: Ref<IInstruction[]> = ref([]);
    const unitValues: Ref<string[]> = ref(Object.keys(Unit));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveIngredient = async ingredientId => {
      try {
        const res = await ingredientService().find(ingredientId);
        ingredient.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.ingredientId) {
      retrieveIngredient(route.params.ingredientId);
    }

    const initRelationships = () => {
      instructionService()
        .retrieve()
        .then(res => {
          instructions.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {},
      amount: {},
      unit: {},
      instruction: {},
    };
    const v$ = useVuelidate(validationRules, ingredient as any);
    v$.value.$validate();

    return {
      ingredientService,
      alertService,
      ingredient,
      previousState,
      unitValues,
      isSaving,
      currentLanguage,
      instructions,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.ingredient.id) {
        this.ingredientService()
          .update(this.ingredient)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('szakdolgozatApp.ingredient.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.ingredientService()
          .create(this.ingredient)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('szakdolgozatApp.ingredient.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
