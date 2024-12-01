/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import RecipeUpdate from './recipe-update.vue';
import RecipeService from './recipe.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';

type RecipeUpdateComponentType = InstanceType<typeof RecipeUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const recipeSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RecipeUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Recipe Management Update Component', () => {
    let comp: RecipeUpdateComponentType;
    let recipeServiceStub: SinonStubbedInstance<RecipeService>;

    beforeEach(() => {
      route = {};
      recipeServiceStub = sinon.createStubInstance<RecipeService>(RecipeService);
      recipeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          recipeService: () => recipeServiceStub,

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
        const wrapper = shallowMount(RecipeUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(RecipeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.recipe = recipeSample;
        recipeServiceStub.update.resolves(recipeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(recipeServiceStub.update.calledWith(recipeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        recipeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RecipeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.recipe = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(recipeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        recipeServiceStub.find.resolves(recipeSample);
        recipeServiceStub.retrieve.resolves([recipeSample]);

        // WHEN
        route = {
          params: {
            recipeId: `${recipeSample.id}`,
          },
        };
        const wrapper = shallowMount(RecipeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.recipe).toMatchObject(recipeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        recipeServiceStub.find.resolves(recipeSample);
        const wrapper = shallowMount(RecipeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
