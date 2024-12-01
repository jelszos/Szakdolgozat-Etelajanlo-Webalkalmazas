import { defineComponent, provide } from 'vue';
import { useI18n } from 'vue-i18n';
import JhiFooter from '@/core/jhi-footer/jhi-footer.vue';
import JhiNavbar from '@/core/jhi-navbar/jhi-navbar.vue';
import LoginForm from '@/account/login-form/login-form.vue';

import { useAlertService } from '@/shared/alert/alert.service';

import '@/shared/config/dayjs';
import ConfirmModal from '@/core/modals/ConfirmModal.vue';
import LanguageChanger from '@/core/components/LanguageChanger.vue';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'App',
  components: {
    LanguageChanger,
    ConfirmModal,
    'jhi-navbar': JhiNavbar,
    'login-form': LoginForm,
    'jhi-footer': JhiFooter,
  },
  setup() {
    provide('alertService', useAlertService());

    return {
      t$: useI18n().t,
    };
  },
});
