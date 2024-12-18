/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import RecipeBookUpdate from './recipe-book-update.vue';
import RecipeBookService from './recipe-book.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';

type RecipeBookUpdateComponentType = InstanceType<typeof RecipeBookUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const recipeBookSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RecipeBookUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RecipeBook Management Update Component', () => {
    let comp: RecipeBookUpdateComponentType;
    let recipeBookServiceStub: SinonStubbedInstance<RecipeBookService>;

    beforeEach(() => {
      route = {};
      recipeBookServiceStub = sinon.createStubInstance<RecipeBookService>(RecipeBookService);
      recipeBookServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          recipeBookService: () => recipeBookServiceStub,

          userService: () =>
            sinon.createStubInstance<UserService>(UserService, {
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
        const wrapper = shallowMount(RecipeBookUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(RecipeBookUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.recipeBook = recipeBookSample;
        recipeBookServiceStub.update.resolves(recipeBookSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(recipeBookServiceStub.update.calledWith(recipeBookSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        recipeBookServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RecipeBookUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.recipeBook = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(recipeBookServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        recipeBookServiceStub.find.resolves(recipeBookSample);
        recipeBookServiceStub.retrieve.resolves([recipeBookSample]);

        // WHEN
        route = {
          params: {
            recipeBookId: `${recipeBookSample.id}`,
          },
        };
        const wrapper = shallowMount(RecipeBookUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.recipeBook).toMatchObject(recipeBookSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        recipeBookServiceStub.find.resolves(recipeBookSample);
        const wrapper = shallowMount(RecipeBookUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
