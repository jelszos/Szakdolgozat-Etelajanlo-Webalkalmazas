import { Authority } from '@/shared/security/authority';

const ProfileEdit = () => import('@/core/profile/profile-edit.vue');
const ProfileView = () => import('@/core/profile/profile-view.vue');

export default [
  {
    path: '/profile-view/:userId/view',
    name: 'ProfileView',
    component: ProfileView,
  },
  {
    path: '/profile/:userId/edit',
    name: 'ProfileEdit',
    component: ProfileEdit,
    meta: { authorities: [Authority.USER] },
  },
];
