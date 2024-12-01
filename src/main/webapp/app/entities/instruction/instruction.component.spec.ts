/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Instruction from './instruction.vue';
import InstructionService from './instruction.service';
import AlertService from '@/shared/alert/alert.service';

type InstructionComponentType = InstanceType<typeof Instruction>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Instruction Management Component', () => {
    let instructionServiceStub: SinonStubbedInstance<InstructionService>;
    let mountOptions: MountingOptions<InstructionComponentType>['global'];

    beforeEach(() => {
      instructionServiceStub = sinon.createStubInstance<InstructionService>(InstructionService);
      instructionServiceStub.retrieve.resolves({ headers: {} });

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
          instructionService: () => instructionServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        instructionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Instruction, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(instructionServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.instructions[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: InstructionComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Instruction, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        instructionServiceStub.retrieve.reset();
        instructionServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        instructionServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeInstruction();
        await comp.$nextTick(); // clear components

        // THEN
        expect(instructionServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(instructionServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
