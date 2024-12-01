/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FavoriteRelationUpdate from './favorite-relation-update.vue';
import FavoriteRelationService from './favorite-relation.service';
import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';
import RecipeService from '@/entities/recipe/recipe.service';

type FavoriteRelationUpdateComponentType = InstanceType<typeof FavoriteRelationUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const favoriteRelationSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<FavoriteRelationUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('FavoriteRelation Management Update Component', () => {
    let comp: FavoriteRelationUpdateComponentType;
    let favoriteRelationServiceStub: SinonStubbedInstance<FavoriteRelationService>;

    beforeEach(() => {
      route = {};
      favoriteRelationServiceStub = sinon.createStubInstance<FavoriteRelationService>(FavoriteRelationService);
      favoriteRelationServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          favoriteRelationService: () => favoriteRelationServiceStub,

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

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(FavoriteRelationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.favoriteRelation = favoriteRelationSample;
        favoriteRelationServiceStub.update.resolves(favoriteRelationSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(favoriteRelationServiceStub.update.calledWith(favoriteRelationSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        favoriteRelationServiceStub.create.resolves(entity);
        const wrapper = shallowMount(FavoriteRelationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.favoriteRelation = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(favoriteRelationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        favoriteRelationServiceStub.find.resolves(favoriteRelationSample);
        favoriteRelationServiceStub.retrieve.resolves([favoriteRelationSample]);

        // WHEN
        route = {
          params: {
            favoriteRelationId: `${favoriteRelationSample.id}`,
          },
        };
        const wrapper = shallowMount(FavoriteRelationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.favoriteRelation).toMatchObject(favoriteRelationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        favoriteRelationServiceStub.find.resolves(favoriteRelationSample);
        const wrapper = shallowMount(FavoriteRelationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
