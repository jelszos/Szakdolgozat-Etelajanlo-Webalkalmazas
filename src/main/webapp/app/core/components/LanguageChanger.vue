<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import languages from '@/shared/config/languages';
import { computed, inject } from 'vue';

const changeLanguage = inject<() => Promise<void>>('changeLanguage');
const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);
const langs = languages();

const isActiveLanguage = (key: string) => {
  return key === currentLanguage.value;
};
</script>

<template v-if="langs && Object.keys(langs).length > 1">
  <div class="position-fixed language-changer">
    <b-dropdown size="lg" variant="primary" toggle-class="text-decoration-none rounded-pill p-1" no-caret>
      <template #button-content>
        <font-awesome-icon icon="globe-americas" class="lc-icon" />
        <span class="sr-only">Language changer</span>
      </template>
      <b-dropdown-item
        v-for="(value, key) in langs"
        :key="`lang-${key}`"
        @click="changeLanguage(key)"
        :class="{ active: isActiveLanguage(key) }"
      >
        <span v-if="isActiveLanguage(key)" class="dot"></span>
        {{ value.name }}
      </b-dropdown-item>
    </b-dropdown>
  </div>
</template>

<style scoped>
.language-changer {
  bottom: 20px;
  right: 20px;
}

.lc-icon {
  font-size: 25px;
}

.dot {
  display: inline-block;
  width: 8px;
  height: 8px;
  margin-right: 8px;
  background-color: black; /* Or your preferred color */
  border-radius: 50%;
}
</style>
