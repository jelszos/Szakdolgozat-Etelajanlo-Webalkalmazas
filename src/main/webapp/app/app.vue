<template>
  <div id="app">
    <div id="app-header">
      <jhi-navbar></jhi-navbar>
    </div>
    <div class="position-relative mt-4">
      <div class="custom-h">
        <router-view></router-view>
      </div>
      <b-modal id="login-page" hide-footer lazy>
        <template #modal-title>
          <span data-cy="loginTitle" id="login-title" v-text="t$('login.title')"></span>
        </template>
        <login-form></login-form>
      </b-modal>
      <confirm-modal />

      <language-changer />

      <jhi-footer></jhi-footer>
    </div>
  </div>
</template>

<script lang="ts" src="./app.component.ts"></script>
<style>
.custom-h {
  min-height: 70vh;
  padding-bottom: 22px;
}
#app-header {
  position: sticky;
  top: 0;
  padding-top: 10px;
  z-index: 10;
}
</style>
