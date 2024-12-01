/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import RatingUpdate from './rating-update.vue';
import RatingService from './rating.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';
import RecipeService from '@/entities/recipe/recipe.service';

type RatingUpdateComponentType = InstanceType<typeof RatingUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const ratingSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RatingUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Rating Management Update Component', () => {
    let comp: RatingUpdateComponentType;
    let ratingServiceStub: SinonStubbedInstance<RatingService>;

    beforeEach(() => {
      route = {};
      ratingServiceStub = sinon.createStubInstance<RatingService>(RatingService);
      ratingServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          ratingService: () => ratingServiceStub,

          userService: () =>
            sinon.createStubInstance<UserService>(UserService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
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

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(RatingUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(RatingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rating = ratingSample;
        ratingServiceStub.update.resolves(ratingSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ratingServiceStub.update.calledWith(ratingSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        ratingServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RatingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rating = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ratingServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        ratingServiceStub.find.resolves(ratingSample);
        ratingServiceStub.retrieve.resolves([ratingSample]);

        // WHEN
        route = {
          params: {
            ratingId: `${ratingSample.id}`,
          },
        };
        const wrapper = shallowMount(RatingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.rating).toMatchObject(ratingSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        ratingServiceStub.find.resolves(ratingSample);
        const wrapper = shallowMount(RatingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
