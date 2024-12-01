import { defineComponent } from 'vue';
import { useI18n } from 'vue-i18n';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import logo from '/content/images/navbar_food_logo.svg';

export default defineComponent({
  name: 'JhiFooter',
  components: { FontAwesomeIcon },
  compatConfig: { MODE: 3 },
  setup() {
    return {
      logo,
      t$: useI18n().t,
    };
  },
});
