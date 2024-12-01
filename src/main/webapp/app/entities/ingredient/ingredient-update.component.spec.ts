/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import IngredientUpdate from './ingredient-update.vue';
import IngredientService from './ingredient.service';
import AlertService from '@/shared/alert/alert.service';

import InstructionService from '@/entities/instruction/instruction.service';

type IngredientUpdateComponentType = InstanceType<typeof IngredientUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const ingredientSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<IngredientUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Ingredient Management Update Component', () => {
    let comp: IngredientUpdateComponentType;
    let ingredientServiceStub: SinonStubbedInstance<IngredientService>;

    beforeEach(() => {
      route = {};
      ingredientServiceStub = sinon.createStubInstance<IngredientService>(IngredientService);
      ingredientServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          ingredientService: () => ingredientServiceStub,
          instructionService: () =>
            sinon.createStubInstance<InstructionService>(InstructionService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(IngredientUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.ingredient = ingredientSample;
        ingredientServiceStub.update.resolves(ingredientSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ingredientServiceStub.update.calledWith(ingredientSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        ingredientServiceStub.create.resolves(entity);
        const wrapper = shallowMount(IngredientUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.ingredient = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ingredientServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        ingredientServiceStub.find.resolves(ingredientSample);
        ingredientServiceStub.retrieve.resolves([ingredientSample]);

        // WHEN
        route = {
          params: {
            ingredientId: `${ingredientSample.id}`,
          },
        };
        const wrapper = shallowMount(IngredientUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.ingredient).toMatchObject(ingredientSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        ingredientServiceStub.find.resolves(ingredientSample);
        const wrapper = shallowMount(IngredientUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
