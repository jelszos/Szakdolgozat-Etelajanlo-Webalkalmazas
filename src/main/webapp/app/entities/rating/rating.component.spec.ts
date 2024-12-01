/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Rating from './rating.vue';
import RatingService from './rating.service';
import AlertService from '@/shared/alert/alert.service';

type RatingComponentType = InstanceType<typeof Rating>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Rating Management Component', () => {
    let ratingServiceStub: SinonStubbedInstance<RatingService>;
    let mountOptions: MountingOptions<RatingComponentType>['global'];

    beforeEach(() => {
      ratingServiceStub = sinon.createStubInstance<RatingService>(RatingService);
      ratingServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          ratingService: () => ratingServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        ratingServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Rating, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(ratingServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.ratings[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: RatingComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Rating, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        ratingServiceStub.retrieve.reset();
        ratingServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        ratingServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeRating();
        await comp.$nextTick(); // clear components

        // THEN
        expect(ratingServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(ratingServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
