import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useVuelidate } from '@vuelidate/core';
import { helpers, maxLength, minLength, required, sameAs } from '@vuelidate/validators';
import type LoginService from '@/account/login.service';
import RegisterService from '@/account/register/register.service';
import { LOGIN_ALREADY_USED_TYPE } from '@/constants';
import { functions } from '@/shared/utils/functions';

const loginPattern = helpers.regex(/^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/);

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Register',
  validations() {
    return {
      registerAccount: {
        login: {
          required,
          minLength: minLength(1),
          maxLength: maxLength(50),
          pattern: loginPattern,
        },
        password: {
          required,
          minLength: minLength(4),
          maxLength: maxLength(254),
        },
        firstName: {
          required,
          minLength: minLength(4),
          maxLength: maxLength(254),
        },
        lastName: {
          required,
          minLength: minLength(4),
          maxLength: maxLength(254),
        },
      },
      confirmPassword: {
        required,
        minLength: minLength(4),
        maxLength: maxLength(50),
        sameAsPassword: sameAs(this.registerAccount.password),
      },
    };
  },
  setup(prop) {
    const loginService = inject<LoginService>('loginService');
    const registerService = inject('registerService', () => new RegisterService(), true);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const error: Ref<string> = ref('');
    const errorUserExists: Ref<string> = ref('');
    const success: Ref<boolean> = ref(false);

    const confirmPassword: Ref<any> = ref(null);
    const registerAccount: Ref<any> = ref({
      login: undefined,
      password: undefined,
      avatar: undefined,
      lastName: undefined,
      firstName: undefined,
    });

    const avatarImages = ref([]);
    const selectedAvatar = ref(0);

    const openLogin = () => {
      loginService.openLogin();
    };

    return {
      openLogin,
      currentLanguage,
      registerService,
      error,
      errorUserExists,
      success,
      confirmPassword,
      registerAccount,
      avatarImages,
      selectedAvatar,
      v$: useVuelidate(),
      t$: useI18n().t,
    };
  },
  mounted() {
    this.loadAvatars();
  },
  methods: {
    loadAvatars() {
      this.avatarImages = functions.getAllAvatarImgs();
      console.log(this.avatarImages);
    },
    // Handle selection of an avatar
    selectAvatar(avatarIndex) {
      this.selectedAvatar = avatarIndex;
      this.registerAccount.avatar = avatarIndex;
    },

    register(): void {
      this.error = null;
      this.errorUserExists = null;
      this.registerAccount.langKey = this.currentLanguage;
      if (this.registerAccount.avatar === null) {
        this.registerAccount.avatar = 1;
      }
      this.registerService
        .processRegistration(this.registerAccount)
        .then(() => {
          this.success = true;
        })
        .catch(error => {
          this.success = null;
          if (error.response.status === 400 && error.response.data.type === LOGIN_ALREADY_USED_TYPE) {
            this.errorUserExists = 'ERROR';
          } else {
            this.error = 'ERROR';
          }
        });
    },
  },
});
