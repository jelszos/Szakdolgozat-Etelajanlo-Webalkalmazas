/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import InstructionDetails from './instruction-details.vue';
import InstructionService from './instruction.service';
import AlertService from '@/shared/alert/alert.service';

type InstructionDetailsComponentType = InstanceType<typeof InstructionDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const instructionSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Instruction Management Detail Component', () => {
    let instructionServiceStub: SinonStubbedInstance<InstructionService>;
    let mountOptions: MountingOptions<InstructionDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      instructionServiceStub = sinon.createStubInstance<InstructionService>(InstructionService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          instructionService: () => instructionServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        instructionServiceStub.find.resolves(instructionSample);
        route = {
          params: {
            instructionId: `${123}`,
          },
        };
        const wrapper = shallowMount(InstructionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.instruction).toMatchObject(instructionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        instructionServiceStub.find.resolves(instructionSample);
        const wrapper = shallowMount(InstructionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
