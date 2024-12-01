import {
  BAlert,
  BBadge,
  BButton,
  BCollapse,
  BDropdown,
  BDropdownItem,
  BForm,
  BFormCheckbox,
  BFormDatepicker,
  BFormGroup,
  BFormInput,
  BInputGroup,
  BInputGroupPrepend,
  BLink,
  BModal,
  BNavItem,
  BNavItemDropdown,
  BNavbar,
  BNavbarBrand,
  BNavbarNav,
  BNavbarToggle,
  BPagination,
  BProgress,
  BProgressBar,
  ToastPlugin,
  VBModal,
  BCardGroup,
  BCard,
  BCardTitle,
  BCarousel,
  BCarouselSlide,
  BTable,
  BCardText,
  BInputGroupAppend,
  BFormTags,
  BFormSelect,
  BFormTag,
  BImg,
  BFormRow,
  BCol,
  BRow,
  BFormRating,
  BCardBody,
  BCardHeader,
  BContainer,
  BAvatar,
  BIcon,
  BFormSelectOption,
  BTooltip,
  BAvatarGroup,
  BPaginationNav,
  BCardFooter,
  BFormTextarea,
  BFormRadioGroup,
  BListGroupItem,
  BListGroup,
  BFormFile,
} from 'bootstrap-vue';

export function initBootstrapVue(vue) {
  vue.use(ToastPlugin);

  vue.component('b-badge', BBadge);
  vue.component('b-dropdown', BDropdown);
  vue.component('b-dropdown-item', BDropdownItem);
  vue.component('b-link', BLink);
  vue.component('b-alert', BAlert);
  vue.component('b-button', BButton);
  vue.component('b-navbar', BNavbar);
  vue.component('b-navbar-nav', BNavbarNav);
  vue.component('b-navbar-brand', BNavbarBrand);
  vue.component('b-navbar-toggle', BNavbarToggle);
  vue.component('b-pagination', BPagination);
  vue.component('b-progress', BProgress);
  vue.component('b-progress-bar', BProgressBar);
  vue.component('b-form', BForm);
  vue.component('b-form-input', BFormInput);
  vue.component('b-form-group', BFormGroup);
  vue.component('b-form-checkbox', BFormCheckbox);
  vue.component('b-collapse', BCollapse);
  vue.component('b-nav-item', BNavItem);
  vue.component('b-nav-item-dropdown', BNavItemDropdown);
  vue.component('b-modal', BModal);
  vue.directive('b-modal', VBModal);
  vue.component('b-form-datepicker', BFormDatepicker);
  vue.component('b-input-group', BInputGroup);
  vue.component('b-input-group-prepend', BInputGroupPrepend);
  vue.component('b-card', BCard);
  vue.component('b-card-group', BCardGroup);
  vue.component('b-card-title', BCardTitle);
  vue.component('b-carousel', BCarousel);
  vue.component('b-carousel-slide', BCarouselSlide);
  vue.component('b-table', BTable);
  vue.component('b-card-text', BCardText);
  vue.component('b-input-group-append', BInputGroupAppend);
  vue.component('b-form-tags', BFormTags);
  vue.component('b-form-select', BFormSelect);
  vue.component('b-form-tag', BFormTag);
  vue.component('b-img', BImg);
  vue.component('b-form-row', BFormRow);
  vue.component('b-col', BCol);
  vue.component('b-row', BRow);
  vue.component('b-form-rating', BFormRating);
  vue.component('b-card-header', BCardHeader);
  vue.component('b-card-body', BCardBody);
  vue.component('b-icon', BIcon);
  vue.component('b-avatar', BAvatar);
  vue.component('b-container', BContainer);
  vue.component('b-form-select-option', BFormSelectOption);
  vue.component('b-tooltip', BTooltip);
  vue.component('b-avatar-group', BAvatarGroup);
  vue.component('b-pagination-nav', BPaginationNav);
  vue.component('b-card-footer', BCardFooter);
  vue.component('b-form-textarea', BFormTextarea);
  vue.component('b-form-radio-group', BFormRadioGroup);
  vue.component('b-list-group-item', BListGroupItem);
  vue.component('b-list-group', BListGroup);
  vue.component('b-form-file', BFormFile);
}
