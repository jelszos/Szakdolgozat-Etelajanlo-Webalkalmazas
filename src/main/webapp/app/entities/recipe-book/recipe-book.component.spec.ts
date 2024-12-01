/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import RecipeBook from './recipe-book.vue';
import RecipeBookService from './recipe-book.service';
import AlertService from '@/shared/alert/alert.service';

type RecipeBookComponentType = InstanceType<typeof RecipeBook>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('RecipeBook Management Component', () => {
    let recipeBookServiceStub: SinonStubbedInstance<RecipeBookService>;
    let mountOptions: MountingOptions<RecipeBookComponentType>['global'];

    beforeEach(() => {
      recipeBookServiceStub = sinon.createStubInstance<RecipeBookService>(RecipeBookService);
      recipeBookServiceStub.retrieve.resolves({ headers: {} });

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
          recipeBookService: () => recipeBookServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        recipeBookServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(RecipeBook, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(recipeBookServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.recipeBooks[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: RecipeBookComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(RecipeBook, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        recipeBookServiceStub.retrieve.reset();
        recipeBookServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        recipeBookServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeRecipeBook();
        await comp.$nextTick(); // clear components

        // THEN
        expect(recipeBookServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(recipeBookServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
