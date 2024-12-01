/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FavoriteRelationDetails from './favorite-relation-details.vue';
import FavoriteRelationService from './favorite-relation.service';
import AlertService from '@/shared/alert/alert.service';

type FavoriteRelationDetailsComponentType = InstanceType<typeof FavoriteRelationDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const favoriteRelationSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('FavoriteRelation Management Detail Component', () => {
    let favoriteRelationServiceStub: SinonStubbedInstance<FavoriteRelationService>;
    let mountOptions: MountingOptions<FavoriteRelationDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      favoriteRelationServiceStub = sinon.createStubInstance<FavoriteRelationService>(FavoriteRelationService);

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
          favoriteRelationService: () => favoriteRelationServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        favoriteRelationServiceStub.find.resolves(favoriteRelationSample);
        route = {
          params: {
            favoriteRelationId: `${123}`,
          },
        };
        const wrapper = shallowMount(FavoriteRelationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.favoriteRelation).toMatchObject(favoriteRelationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        favoriteRelationServiceStub.find.resolves(favoriteRelationSample);
        const wrapper = shallowMount(FavoriteRelationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
