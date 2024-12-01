/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RecipeBookRelationUpdate from './recipe-book-relation-update.vue';
import RecipeBookRelationService from './recipe-book-relation.service';
import AlertService from '@/shared/alert/alert.service';

import RecipeService from '@/entities/recipe/recipe.service';
import RecipeBookService from '@/entities/recipe-book/recipe-book.service';

type RecipeBookRelationUpdateComponentType = InstanceType<typeof RecipeBookRelationUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const recipeBookRelationSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RecipeBookRelationUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RecipeBookRelation Management Update Component', () => {
    let comp: RecipeBookRelationUpdateComponentType;
    let recipeBookRelationServiceStub: SinonStubbedInstance<RecipeBookRelationService>;

    beforeEach(() => {
      route = {};
      recipeBookRelationServiceStub = sinon.createStubInstance<RecipeBookRelationService>(RecipeBookRelationService);
      recipeBookRelationServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          recipeBookRelationService: () => recipeBookRelationServiceStub,
          recipeService: () =>
            sinon.createStubInstance<RecipeService>(RecipeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          recipeBookService: () =>
            sinon.createStubInstance<RecipeBookService>(RecipeBookService, {
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
        const wrapper = shallowMount(RecipeBookRelationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.recipeBookRelation = recipeBookRelationSample;
        recipeBookRelationServiceStub.update.resolves(recipeBookRelationSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(recipeBookRelationServiceStub.update.calledWith(recipeBookRelationSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        recipeBookRelationServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RecipeBookRelationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.recipeBookRelation = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(recipeBookRelationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        recipeBookRelationServiceStub.find.resolves(recipeBookRelationSample);
        recipeBookRelationServiceStub.retrieve.resolves([recipeBookRelationSample]);

        // WHEN
        route = {
          params: {
            recipeBookRelationId: `${recipeBookRelationSample.id}`,
          },
        };
        const wrapper = shallowMount(RecipeBookRelationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.recipeBookRelation).toMatchObject(recipeBookRelationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        recipeBookRelationServiceStub.find.resolves(recipeBookRelationSample);
        const wrapper = shallowMount(RecipeBookRelationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
