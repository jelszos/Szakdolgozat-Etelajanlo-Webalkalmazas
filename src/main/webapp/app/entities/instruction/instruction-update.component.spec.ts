/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import InstructionUpdate from './instruction-update.vue';
import InstructionService from './instruction.service';
import AlertService from '@/shared/alert/alert.service';

import RecipeService from '@/entities/recipe/recipe.service';

type InstructionUpdateComponentType = InstanceType<typeof InstructionUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const instructionSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<InstructionUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Instruction Management Update Component', () => {
    let comp: InstructionUpdateComponentType;
    let instructionServiceStub: SinonStubbedInstance<InstructionService>;

    beforeEach(() => {
      route = {};
      instructionServiceStub = sinon.createStubInstance<InstructionService>(InstructionService);
      instructionServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          instructionService: () => instructionServiceStub,
          recipeService: () =>
            sinon.createStubInstance<RecipeService>(RecipeService, {
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
        const wrapper = shallowMount(InstructionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.instruction = instructionSample;
        instructionServiceStub.update.resolves(instructionSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(instructionServiceStub.update.calledWith(instructionSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        instructionServiceStub.create.resolves(entity);
        const wrapper = shallowMount(InstructionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.instruction = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(instructionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        instructionServiceStub.find.resolves(instructionSample);
        instructionServiceStub.retrieve.resolves([instructionSample]);

        // WHEN
        route = {
          params: {
            instructionId: `${instructionSample.id}`,
          },
        };
        const wrapper = shallowMount(InstructionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.instruction).toMatchObject(instructionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        instructionServiceStub.find.resolves(instructionSample);
        const wrapper = shallowMount(InstructionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
