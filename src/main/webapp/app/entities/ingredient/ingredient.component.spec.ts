/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Ingredient from './ingredient.vue';
import IngredientService from './ingredient.service';
import AlertService from '@/shared/alert/alert.service';

type IngredientComponentType = InstanceType<typeof Ingredient>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Ingredient Management Component', () => {
    let ingredientServiceStub: SinonStubbedInstance<IngredientService>;
    let mountOptions: MountingOptions<IngredientComponentType>['global'];

    beforeEach(() => {
      ingredientServiceStub = sinon.createStubInstance<IngredientService>(IngredientService);
      ingredientServiceStub.retrieve.resolves({ headers: {} });

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
          ingredientService: () => ingredientServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        ingredientServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Ingredient, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(ingredientServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.ingredients[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: IngredientComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Ingredient, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        ingredientServiceStub.retrieve.reset();
        ingredientServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        ingredientServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeIngredient();
        await comp.$nextTick(); // clear components

        // THEN
        expect(ingredientServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(ingredientServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
