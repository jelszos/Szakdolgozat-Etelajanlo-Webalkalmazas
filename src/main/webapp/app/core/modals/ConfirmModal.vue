<script setup lang="ts">
import { isModalVisible, modalContent, modalProps, hideModal } from '@/core/modals/ConfirmModalService';
import { useI18n } from 'vue-i18n';

function resetModalState() {
  modalContent.value = null;
  modalProps.value = {};
}
const { t: t$ } = useI18n();

function handleOk() {
  if (typeof modalProps.value.onOk === 'function') {
    modalProps.value.onOk();
  }
  hideModal();
}
</script>

<template>
  <b-modal id="confirm-modal" v-model="isModalVisible" :title="modalProps.title || t$('profile.deletemodal.title')" @hide="resetModalState">
    <p v-if="modalProps.description">{{ modalProps.description }}</p>
    <div v-if="modalContent">
      <component :is="modalContent" v-bind="modalProps" />
    </div>
    <template #modal-footer>
      <b-button variant="secondary" @click="hideModal">Cancel</b-button>
      <b-button variant="danger" @click="handleOk">Confirm</b-button>
    </template>
  </b-modal>
</template>

<style scoped></style>
