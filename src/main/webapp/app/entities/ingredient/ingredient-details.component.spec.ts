/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import IngredientDetails from './ingredient-details.vue';
import IngredientService from './ingredient.service';
import AlertService from '@/shared/alert/alert.service';

type IngredientDetailsComponentType = InstanceType<typeof IngredientDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const ingredientSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Ingredient Management Detail Component', () => {
    let ingredientServiceStub: SinonStubbedInstance<IngredientService>;
    let mountOptions: MountingOptions<IngredientDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      ingredientServiceStub = sinon.createStubInstance<IngredientService>(IngredientService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          ingredientService: () => ingredientServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        ingredientServiceStub.find.resolves(ingredientSample);
        route = {
          params: {
            ingredientId: `${123}`,
          },
        };
        const wrapper = shallowMount(IngredientDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.ingredient).toMatchObject(ingredientSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        ingredientServiceStub.find.resolves(ingredientSample);
        const wrapper = shallowMount(IngredientDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
