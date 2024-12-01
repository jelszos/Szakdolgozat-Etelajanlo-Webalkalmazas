/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RecipeBookRelationDetails from './recipe-book-relation-details.vue';
import RecipeBookRelationService from './recipe-book-relation.service';
import AlertService from '@/shared/alert/alert.service';

type RecipeBookRelationDetailsComponentType = InstanceType<typeof RecipeBookRelationDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const recipeBookRelationSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RecipeBookRelation Management Detail Component', () => {
    let recipeBookRelationServiceStub: SinonStubbedInstance<RecipeBookRelationService>;
    let mountOptions: MountingOptions<RecipeBookRelationDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      recipeBookRelationServiceStub = sinon.createStubInstance<RecipeBookRelationService>(RecipeBookRelationService);

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
          recipeBookRelationService: () => recipeBookRelationServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        recipeBookRelationServiceStub.find.resolves(recipeBookRelationSample);
        route = {
          params: {
            recipeBookRelationId: `${123}`,
          },
        };
        const wrapper = shallowMount(RecipeBookRelationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.recipeBookRelation).toMatchObject(recipeBookRelationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        recipeBookRelationServiceStub.find.resolves(recipeBookRelationSample);
        const wrapper = shallowMount(RecipeBookRelationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
