/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Recipe from './recipe.vue';
import RecipeService from './recipe.service';
import AlertService from '@/shared/alert/alert.service';

type RecipeComponentType = InstanceType<typeof Recipe>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Recipe Management Component', () => {
    let recipeServiceStub: SinonStubbedInstance<RecipeService>;
    let mountOptions: MountingOptions<RecipeComponentType>['global'];

    beforeEach(() => {
      recipeServiceStub = sinon.createStubInstance<RecipeService>(RecipeService);
      recipeServiceStub.retrieve.resolves({ headers: {} });

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
          recipeService: () => recipeServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        recipeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Recipe, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(recipeServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.recipes[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: RecipeComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Recipe, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        recipeServiceStub.retrieve.reset();
        recipeServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        recipeServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeRecipe();
        await comp.$nextTick(); // clear components

        // THEN
        expect(recipeServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(recipeServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
