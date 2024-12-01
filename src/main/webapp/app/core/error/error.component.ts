import { type ComputedRef, type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import type LoginService from '@/account/login.service';
import errorImage404 from '../../../content/images/404.svg';
import errorImage403 from '../../../content/images/403.svg';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Error',
  setup() {
    const loginService = inject<LoginService>('loginService');
    const authenticated = inject<ComputedRef<boolean>>('authenticated');
    const errorMessage: Ref<string> = ref(null);
    const error403: Ref<boolean> = ref(false);
    const error404: Ref<boolean> = ref(false);
    const route = useRoute();
    const router = useRouter();

    function openLogin() {
      loginService?.openLogin();
    }

    function goBack() {
      router.push('/');
    }

    if (route.meta) {
      errorMessage.value = route.meta.errorMessage ?? null;
      error403.value = route.meta.error403 ?? false;
      error404.value = route.meta.error404 ?? false;
    }

    return {
      errorImage404,
      errorImage403,
      errorMessage,
      error403,
      error404,
      openLogin,
      goBack,
      t$: useI18n().t,
    };
  },
});
