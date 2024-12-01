import { type Ref, computed, defineComponent, inject, ref, onMounted } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RecipeService from './recipe.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';
import { type IRecipe, Recipe } from '@/shared/model/recipe.model';
import { FoodCategory } from '@/shared/model/enumerations/food-category.model';
import { FoodType } from '@/shared/model/enumerations/food-type.model';
import { Unit } from '@/shared/model/enumerations/unit.model';
import IngredientService from '@/entities/ingredient/ingredient.service';
import { maxLength, minLength, required, sameAs } from '@vuelidate/validators';
import headerBg from '../../../content/images/header-bg.svg';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import Ingredient from '@/entities/ingredient/ingredient.component';

export default defineComponent({
  name: 'RecipeUpdate',
  components: { Ingredient, FontAwesomeIcon },
  compatConfig: { MODE: 3 },
  setup() {
    const recipeService = inject('recipeService', () => new RecipeService());
    const alertService = inject('alertService', () => useAlertService(), true);
    const ingredientService = inject('ingredientService', () => new IngredientService());

    const recipe: Ref<IRecipe> = ref(new Recipe());
    const userService = inject('userService', () => new UserService());
    const users: Ref<Array<any>> = ref([]);
    const foodCategoryValues: Ref<string[]> = ref(Object.keys(FoodCategory));
    const foodTypeValues: Ref<string[]> = ref(Object.keys(FoodType));
    const unitValues: Ref<string[]> = ref(Object.keys(Unit));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);
    const editInstructions = ref(false);

    const ingredients = ref([]);
    ingredients.value = keepNonNullValues(ingredients.value);
    const ingredientValue = [];

    const ingredientsArray = computed(() => {
      return Object.values(ingredients.value);
    });

    const availableIngredients = computed(() => {
      return ingredientsArray.value.filter(opt => ingredientValue.indexOf(opt) === -1).sort((a, b) => a.localeCompare(b));
    });

    const instructions = ref([]);
    const showInstructionForm = ref(false);

    const instructionFormData = ref({
      title: '',
      requiredTime: null,
      description: '',
      ingredients: [
        {
          name: null,
          amount: null,
          unit: null,
        },
      ],
    });

    const selectedIngredients = computed(() => {
      return instructionFormData.value.ingredients.map(ingredient => ingredient.name).filter(Boolean);
    });

    function filteredIngredients(index) {
      const selectedIngredients = instructionFormData.value.ingredients.map(ingredient => ingredient.name);

      return availableIngredients.value.filter(ingredient => {
        return ingredient === instructionFormData.value.ingredients[index].name || !selectedIngredients.includes(ingredient);
      });
    }

    const isNewRecipe = useRoute().path == '/recipe/new';
    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    function keepNonNullValues(input: Record<string, string>): Record<string, string> {
      return Object.fromEntries(Object.entries(input).filter(([key, value]) => key != 'null' && value != ''));
    }

    const retrieveRecipe = async recipeId => {
      try {
        const res = await recipeService().find(recipeId);
        res.createdDate = new Date(res.createdDate);
        recipe.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    const retrieveUniqueIngredients = async () => {
      try {
        const res = await ingredientService().retrieveUniqueIngredients();
        ingredients.value = res.data;
        console.log(res.data);
      } catch (err) {}
    };

    if (route.params?.recipeId) {
      retrieveRecipe(route.params.recipeId);
    }

    onMounted(async () => {
      await retrieveUniqueIngredients();
    });

    const initRelationships = () => {
      userService()
        .retrieve()
        .then(res => {
          users.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      title: {
        required,
        minLength: minLength(5),
        maxLength: maxLength(50),
      },
      description: {
        required,
        minLength: minLength(1),
        maxLength: maxLength(254),
      },
      ingredientNames: {},
      foodCategory: {
        required,
      },
      foodType: {
        required,
      },
      imageUrl: {
        required,
      },
      instructions: {
        required,
      },

      createdDate: {},
      user: {},
    };
    const v$ = useVuelidate(validationRules, recipe as any);
    v$.value.$validate();

    const form = ref<HTMLFormElement>();

    function onFileChanged($event: Event) {
      const target = $event.target as HTMLInputElement;

      if (target && target.files) {
        const instructionFormData = new FormData();
        const file = target.files[0];
        instructionFormData.append('file', file);

        recipeService()
          .uploadImage(instructionFormData)
          .then(res => {
            //do something
            this.recipe.imageUrl = res;
            console.log('img path res', res); // -> img path
          });
      }
    }

    //Ingredient modal
    function addIngredient() {
      instructionFormData.value.ingredients.push({
        name: null,
        amount: null,
        unit: null,
      });
    }
    // Remove an ingredient row
    function removeIngredient(index) {
      instructionFormData.value.ingredients.splice(index, 1);
    }
    // Submit the form data
    function resetForm() {
      // Reset form after submission (optional)
      instructionFormData.value = {
        title: '',
        requiredTime: null,
        description: '',
        ingredients: [{ name: null, amount: null, unit: null }],
      };
      showInstructionForm.value = false;
    }

    function isFormValid() {
      return (
        instructionFormData.value.requiredTime &&
        instructionFormData.value.description &&
        instructionFormData.value.ingredients.every(ingredient => ingredient.name && ingredient.amount && ingredient.unit)
      );
    }
    function submitInstructionForm() {
      if (!isFormValid()) {
        alertService.showError('Wrong field(s)!');
        return;
      }
      instructions.value.push({ ...instructionFormData.value });
      recipe.value.instructions = instructions.value;

      resetForm();
    }

    function editInstruction(instruction) {
      editInstructions.value = true;
      showInstructionForm.value = true;
      instructionFormData.value = instruction;
    }

    function closeInstructionModal() {
      if (!isFormValid()) {
        alertService.showError('Wrong fields!');
        return;
      }
      showInstructionForm.value = false;
      editInstructions.value = false;
      resetForm();
    }

    function deleteInstruction(index) {
      recipe.value.instructions = recipe.value.instructions?.filter((ins, i) => index != i);
    }

    return {
      headerBg,
      deleteInstruction,
      closeInstructionModal,
      editInstructions,
      editInstruction,
      onFileChanged,
      recipeService,
      alertService,
      recipe,
      previousState,
      foodCategoryValues,
      foodTypeValues,
      unitValues,
      submitInstructionForm,
      removeIngredient,
      addIngredient,
      instructions,
      resetForm,
      availableIngredients,
      filteredIngredients,
      ingredientValue,
      instructionFormData,
      showInstructionForm,
      isSaving,
      currentLanguage,
      users,
      isNewRecipe,
      v$,
      ...useDateFormat({ entityRef: recipe }),
      t$,
    };
  },
  created(): void {},
  methods: {
    getIngredientNames() {
      let recipeIngredientNames = '';
      recipeIngredientNames = this.recipe.instructions
        ?.flatMap(instruction => instruction.ingredients?.map(ingredient => ingredient.name))
        .join(',');
      return recipeIngredientNames;
    },
    save(): void {
      this.isSaving = true;
      if (this.recipe.id) {
        this.recipe.ingredientNames = this.getIngredientNames();
        this.recipeService()
          .update(this.recipe)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('szakdolgozatApp.recipe.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.recipeService()
          .create(this.recipe)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('szakdolgozatApp.recipe.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
