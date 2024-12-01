import { type ComputedRef, type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useVuelidate } from '@vuelidate/core';
import { email, maxLength, minLength, required } from '@vuelidate/validators';
import axios from 'axios';
import languages from '@/shared/config/languages';
import { EMAIL_ALREADY_USED_TYPE } from '@/constants';
import { useStore } from '@/store';
import { functions } from '@/shared/utils/functions';

const validations = {
  settingsAccount: {
    firstName: {
      required,
      minLength: minLength(1),
      maxLength: maxLength(50),
    },
    lastName: {
      required,
      minLength: minLength(1),
      maxLength: maxLength(50),
    },
  },
};

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Settings',
  validations,
  setup() {
    const store = useStore();

    const success: Ref<string> = ref(null);
    const error: Ref<string> = ref(null);
    const errorEmailExists: Ref<string> = ref(null);

    const settingsAccount = computed(() => store.account);
    const username = inject<ComputedRef<string>>('currentUsername', () => computed(() => store.account?.login), true);

    const avatarImages = ref([]);
    const selectedAvatar = ref(0);

    return {
      success,
      error,
      errorEmailExists,
      settingsAccount,
      username,
      avatarImages,
      selectedAvatar,
      v$: useVuelidate(),
      languages: languages(),
      t$: useI18n().t,
    };
  },
  mounted() {
    this.loadAvatars();
    this.selectAvatar();
  },
  methods: {
    save() {
      this.error = null;
      this.errorEmailExists = null;
      return axios
        .post('api/account', this.settingsAccount)
        .then(() => {
          this.error = null;
          this.success = 'OK';
          this.errorEmailExists = null;
        })
        .catch(ex => {
          this.success = null;
          this.error = 'ERROR';
          if (ex.response.status === 400 && ex.response.data.type === EMAIL_ALREADY_USED_TYPE) {
            this.errorEmailExists = 'ERROR';
            this.error = null;
          }
        });
    },
    loadAvatars() {
      this.avatarImages = functions.getAllAvatarImgs();
    },
    // Handle selection of an avatar
    selectAvatar(avatarIndex) {
      if (this.selectedAvatar == 0 && avatarIndex == null) {
        this.selectedAvatar = this.settingsAccount.avatar;
        return;
      } else {
        this.selectedAvatar = avatarIndex;
        this.settingsAccount.avatar = avatarIndex;
        return;
      }
    },
  },
});
