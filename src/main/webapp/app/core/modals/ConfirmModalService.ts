import { ref } from 'vue';

export const isModalVisible = ref(false);
export const modalContent = ref<any>(null);
export const modalProps = ref<Record<string, any>>({});

export function showConfirmModal(component: any, props = {}) {
  modalContent.value = component;
  modalProps.value = props;
  isModalVisible.value = true;
}

export function hideModal() {
  isModalVisible.value = false;
}
