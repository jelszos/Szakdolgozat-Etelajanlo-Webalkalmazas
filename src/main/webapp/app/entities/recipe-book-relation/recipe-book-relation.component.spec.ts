/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import RecipeBookRelation from './recipe-book-relation.vue';
import RecipeBookRelationService from './recipe-book-relation.service';
import AlertService from '@/shared/alert/alert.service';

type RecipeBookRelationComponentType = InstanceType<typeof RecipeBookRelation>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('RecipeBookRelation Management Component', () => {
    let recipeBookRelationServiceStub: SinonStubbedInstance<RecipeBookRelationService>;
    let mountOptions: MountingOptions<RecipeBookRelationComponentType>['global'];

    beforeEach(() => {
      recipeBookRelationServiceStub = sinon.createStubInstance<RecipeBookRelationService>(RecipeBookRelationService);
      recipeBookRelationServiceStub.retrieve.resolves({ headers: {} });

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
          recipeBookRelationService: () => recipeBookRelationServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        recipeBookRelationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(RecipeBookRelation, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(recipeBookRelationServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.recipeBookRelations[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: RecipeBookRelationComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(RecipeBookRelation, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        recipeBookRelationServiceStub.retrieve.reset();
        recipeBookRelationServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        recipeBookRelationServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeRecipeBookRelation();
        await comp.$nextTick(); // clear components

        // THEN
        expect(recipeBookRelationServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(recipeBookRelationServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
