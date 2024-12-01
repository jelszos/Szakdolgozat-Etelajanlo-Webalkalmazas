<template>
  <div>
    <div class="container">
      <div v-if="errorMessage">
        <div class="alert alert-danger">{{ errorMessage }}</div>
      </div>
      <div v-if="error403" class="">
        <img :src="errorImage403" class="error-image" alt="error 403" />
        <div class="d-flex flex-column align-items-center">
          <p v-text="t$('error.http.403')" class="text-slate-900" style="font-weight: 500; font-size: 26px"></p>
          <div class="d-flex">
            <b-button variant="primary" @click="openLogin">{{ t$('error.login') }}</b-button>
            <b-button class="ml-3" @click="goBack">{{ t$('error.goBack') }}</b-button>
          </div>
        </div>
      </div>
      <div v-if="error404">
        <img :src="errorImage404" class="error-image" alt="error 404" />
        <div class="d-flex flex-column align-items-center">
          <p v-text="t$('error.http.404')" class="text-slate-900" style="font-weight: 500; font-size: 26px"></p>
          <b-button class="ml-3" @click="goBack">{{ t$('error.goBack') }}</b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./error.component.ts"></script>

<style>
.error-image {
  width: 100%;
  height: 700px;
}
</style>
