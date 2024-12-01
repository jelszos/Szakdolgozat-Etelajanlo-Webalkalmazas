import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import type LoginService from '@/account/login.service';
import type AccountService from '@/account/account.service';
import EntitiesMenu from '@/entities/entities-menu.vue';
import logo from '/content/images/navbar_food_logo.svg';

import { useStore } from '@/store';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'JhiNavbar',
  components: {
    'entities-menu': EntitiesMenu,
  },
  setup() {
    const loginService = inject<LoginService>('loginService');
    const accountService = inject<AccountService>('accountService');

    const authenticated = computed(() => store.authenticated);
    const route = useRoute();
    const isAuthenticated = accountService?.authenticated;
    const isNewRecipe = computed(() => route.path === '/recipe/new' || route.path === '/recipe/' + route.params.recipeId + '/edit');
    const router = useRouter();
    const store = useStore();
    const userLogin = computed(() => store.account?.login ?? '');

    const version = `v${APP_VERSION}`;
    const hasAnyAuthorityValues: Ref<any> = ref({});

    const openAPIEnabled = computed(() => store.activeProfiles.indexOf('api-docs') > -1);
    const inProduction = computed(() => store.activeProfiles.indexOf('prod') > -1);

    const openLogin = () => {
      loginService.openLogin();
    };

    const subIsActive = (input: string | string[]) => {
      const paths = Array.isArray(input) ? input : [input];
      return paths.some(path => {
        return router.currentRoute.value.path.indexOf(path) === 0; // current path starts with this path string
      });
    };

    const logout = async () => {
      const response = await loginService.logout();
      store.logout();
      if (router.currentRoute.value.path !== '/') {
        await router.push('/');
      }
    };

    return {
      logo,
      logout,
      subIsActive,
      accountService,
      openLogin,
      version,
      hasAnyAuthorityValues,
      openAPIEnabled,
      inProduction,
      authenticated,
      userLogin,
      isAuthenticated,
      isNewRecipe,
      t$: useI18n().t,
    };
  },
  methods: {
    hasAnyAuthority(authorities: any): boolean {
      this.accountService.hasAnyAuthorityAndCheckAuth(authorities).then(value => {
        if (this.hasAnyAuthorityValues[authorities] !== value) {
          this.hasAnyAuthorityValues = { ...this.hasAnyAuthorityValues, [authorities]: value };
        }
      });
      return this.hasAnyAuthorityValues[authorities] ?? false;
    },
  },
});
