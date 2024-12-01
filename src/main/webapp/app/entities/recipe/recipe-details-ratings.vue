<template>
  <div class="row justify-content-center">
    <b-container>
      <h2 id="page-heading" data-cy="RecipeHeading">
        <span v-text="t$('szakdolgozatApp.recipe.ratings.title', { param: recipe.title })" id="recipe-heading"></span>

        <div class="mb-3 d-flex justify-content-between">
          <button type="submit" @click.prevent="previousState()" class="btn btn-link back-button" data-cy="entityDetailsBackButton">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;
            <span v-text="t$('szakdolgozatApp.recipe.ratings.navigateBack')"></span>
          </button>
        </div>
      </h2>
      <b-row>
        <div class="w-100">
          <div>
            <div v-for="(rating, index) in ratings" :key="index">
              <b-row class="align-items-center justify-content-between">
                <div>
                  <b-row class="align-items-center mb-2">
                    <router-link :to="`/profile-view/${rating.user?.login}/view`" class="d-flex align-items-center text-decoration-none">
                      <b-avatar :src="getAvatarImg(rating?.user?.avatar)" size="xxl" v-if="rating.user?.login"></b-avatar>
                      <div class="ml-2">
                        {{ rating.user?.firstName + ' ' + rating.user?.lastName }}
                      </div>
                    </router-link>
                  </b-row>
                  <b-form-rating id="rate" name="ratingRate" readonly v-model="rating.rate"></b-form-rating>
                </div>
              </b-row>
              <b-row>
                <b-row class="justify-content-between w-100">
                  <div>
                    <p class="mt-3 mb-0">
                      <strong>{{ rating.title }}</strong>
                    </p>
                    <p>{{ rating.description }}</p>
                  </div>
                  <b-img class="rating_img" :src="rating.imageUrl" fluid></b-img>
                </b-row>
              </b-row>
              <hr />
            </div>
          </div>
        </div>
      </b-row>
    </b-container>
  </div>
</template>

<script lang="ts" src="./recipe-details-ratings.component.ts"></script>

<style scoped>
.rating_img {
  width: 150px;
  max-width: 150px;
  height: 150px;
  max-height: 150px;
  object-fit: cover;
}
</style>
