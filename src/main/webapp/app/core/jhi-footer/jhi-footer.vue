<template>
  <div id="footer" class="footer py-5">
    <div class="d-flex container justify-content-between">
      <div>
        <img :src="logo" alt="Logo" class="footer-logo" />
      </div>
      <div class="d-flex align-items-center">
        <p style="font-weight: 500; font-size: 22px" v-text="t$('footer')" class="text-slate-900"></p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./jhi-footer.component.ts"></script>

<style>
.footer-logo {
  width: 100%;
  height: 100px;
}
</style>
