/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import FavoriteRelation from './favorite-relation.vue';
import FavoriteRelationService from './favorite-relation.service';
import AlertService from '@/shared/alert/alert.service';

type FavoriteRelationComponentType = InstanceType<typeof FavoriteRelation>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('FavoriteRelation Management Component', () => {
    let favoriteRelationServiceStub: SinonStubbedInstance<FavoriteRelationService>;
    let mountOptions: MountingOptions<FavoriteRelationComponentType>['global'];

    beforeEach(() => {
      favoriteRelationServiceStub = sinon.createStubInstance<FavoriteRelationService>(FavoriteRelationService);
      favoriteRelationServiceStub.retrieve.resolves({ headers: {} });

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
          favoriteRelationService: () => favoriteRelationServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        favoriteRelationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(FavoriteRelation, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(favoriteRelationServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.favoriteRelations[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: FavoriteRelationComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(FavoriteRelation, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        favoriteRelationServiceStub.retrieve.reset();
        favoriteRelationServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        favoriteRelationServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeFavoriteRelation();
        await comp.$nextTick(); // clear components

        // THEN
        expect(favoriteRelationServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(favoriteRelationServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
