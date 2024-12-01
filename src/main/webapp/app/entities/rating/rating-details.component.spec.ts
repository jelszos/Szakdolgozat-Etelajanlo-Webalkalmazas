/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RatingDetails from './rating-details.vue';
import RatingService from './rating.service';
import AlertService from '@/shared/alert/alert.service';

type RatingDetailsComponentType = InstanceType<typeof RatingDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const ratingSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Rating Management Detail Component', () => {
    let ratingServiceStub: SinonStubbedInstance<RatingService>;
    let mountOptions: MountingOptions<RatingDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      ratingServiceStub = sinon.createStubInstance<RatingService>(RatingService);

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
          ratingService: () => ratingServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        ratingServiceStub.find.resolves(ratingSample);
        route = {
          params: {
            ratingId: `${123}`,
          },
        };
        const wrapper = shallowMount(RatingDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rating).toMatchObject(ratingSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        ratingServiceStub.find.resolves(ratingSample);
        const wrapper = shallowMount(RatingDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
