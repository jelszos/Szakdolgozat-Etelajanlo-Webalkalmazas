<template>
  <div class="position-relative">
    <b-img :src="img4" style="width: 100%; height: 65vh; object-fit: cover" />
    <div class="w-100 h-100 position-absolute" style="top: 0; text-align: left; color: #212529">
      <div class="container h-100 position-relative d-flex align-items-start flex-column justify-content-center">
        <p style="font-size: 46px; font-weight: 700" v-text="t$('home.title')"></p>
        <p style="max-width: 600px; font-size: 30px" v-text="t$('home.bannerDescription')"></p>
        <b-button variant="primary" href="/search" v-text="t$('home.tryThisOut')"></b-button>
      </div>
    </div>
  </div>
  <div class="container">
    <div class="d-flex justify-content-center flex-column">
      <!--      LATEST RECIPES -->
      <div class="mb-lg-5 mt-lg-5">
        <div>
          <div class="position-relative text-center my-5">
            <b-img :src="headerBg" class="carousel-header-img" />
            <h1 class="carousel-header mb-4 position-relative" v-text="t$('home.recipe-groups.latest-recipes')"></h1>
          </div>

          <home-carousel :recipes="latestRecipes" :config="configLatestRecipes"></home-carousel>
        </div>
      </div>

      <div class="d-flex my-5">
        <div class="gradient-blue p-4" style="border-radius: 12px">
          <b-img :src="img3" style="min-width: 25vw; height: 300px; object-fit: contain" />
        </div>
        <div class="text-slate-900">
          <p style="font-size: 30px; font-weight: 700" v-text="t$('home.marketing.title1')"></p>
          <p style="font-size: 20px" v-text="t$('home.marketing.description1')"></p>
          <div class="text-left">
            <b-button href="/search" variant="primary" size="md" v-text="t$('home.marketing.letSee')"></b-button>
          </div>
        </div>
      </div>

      <!--      SAME FOOD CATEGORY-->
      <div class="mb-lg-5 mt-lg-5">
        <div>
          <div class="position-relative text-center my-5">
            <b-img :src="headerBg" class="carousel-header-img" />
            <h1
              class="carousel-header mb-4 position-relative"
              v-text="finalChosenFoodCategory + ' ' + t$('home.recipe-groups.food-category-recipes')"
            ></h1>
          </div>

          <home-carousel :recipes="sameFoodCategoryRecipes" :config="configSameFoodCategoryRecipes"></home-carousel>
        </div>
      </div>

      <div class="d-flex my-5">
        <div class="text-slate-900">
          <p style="font-size: 30px; font-weight: 700" v-text="t$('home.marketing.title2')"></p>
          <p style="font-size: 20px" v-text="t$('home.marketing.description2')"></p>
          <div class="text-left">
            <b-button href="/search" variant="primary" size="md" v-text="t$('home.marketing.letSee')"></b-button>
          </div>
        </div>
        <div class="gradient-blue-reverse p-4" style="border-radius: 12px">
          <b-img :src="img2" style="min-width: 30vw; height: 300px; object-fit: contain" />
        </div>
      </div>

      <!--      HIGHEST FAVORITE COUNT-->
      <div class="mb-lg-5 mt-lg-5">
        <div>
          <div class="position-relative text-center my-5">
            <b-img :src="headerBg" class="carousel-header-img" />
            <h1 class="carousel-header mb-4 position-relative" v-text="t$('home.recipe-groups.highest-favorite-count-recipes')"></h1>
          </div>
          <home-carousel :recipes="highestFavoriteCountRecipes" :config="configLatestRecipes"></home-carousel>
        </div>
      </div>
      <!--      QUICK LINKS TO FOOD TYPES-->
      <div class="mb-lg-5 mt-lg-5">
        <div style="">
          <div class="position-relative text-center my-5">
            <b-img :src="headerBg" class="carousel-header-img" />
            <h1 class="carousel-header mb-4 position-relative" v-text="t$('home.recipe-groups.quick-link-food-types')"></h1>
          </div>
          <carousel v-bind="configFoodCategories">
            <slide v-for="(foodType, index) in foodTypes" :key="index">
              <b-card
                :img-src="foodType.image"
                img-alt="Image"
                img-top
                style="color: #212529"
                tag="a"
                :href="`/search?page=0&foodTypes=${foodType.value.toUpperCase()}`"
                overlay
              >
                <template #footer>
                  <p
                    class="w-100"
                    style="font-size: 24px; padding: 4px 12px; background-color: #fcde9c; border-radius: 22px; color: #222222"
                  >
                    {{ foodType.label }}
                  </p>
                </template>
              </b-card>
            </slide>
            <template #addons>
              <navigation>
                <template #next>
                  <div class="carousel__next">
                    <!-- Replace the icon with Font Awesome right chevron -->
                    <font-awesome-icon icon="angle-double-right" color="#333" />
                  </div>
                </template>

                <template #prev>
                  <div class="carousel__prev">
                    <!-- Replace the icon with Font Awesome left chevron -->
                    <font-awesome-icon icon="angle-double-left" color="#333" />
                  </div>
                </template>
              </navigation>
              <pagination />
            </template>
          </carousel>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>

<style scoped>
.gradient-blue {
  background: rgb(2, 0, 36);
  background: linear-gradient(90deg, rgba(2, 0, 36, 1) 0%, rgba(9, 75, 121, 1) 0%, rgb(255, 255, 255) 38%);
}

.gradient-blue-reverse {
  background: rgb(2, 0, 36);
  background: linear-gradient(270deg, rgba(2, 0, 36, 1) 0%, rgba(9, 75, 121, 1) 0%, rgb(255, 255, 255) 38%);
}

.carousel-header {
  font-size: 30px;
  font-weight: 700;
  margin-bottom: 22px;
  z-index: 2;
  top: 4px;
  color: #334155;
}

.carousel-item-container img {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.carousel__prev,
.carousel__next {
  border-radius: 50%;
  margin: 0 16px;
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #ffffff !important;
  filter: drop-shadow(0px 15px 30px rgba(51, 51, 51, 0.2));
  box-shadow: 0px 15px 30px rgba(51, 51, 51, 0.2);
}
.carousel-header-img {
  max-width: 100%;
  height: 150%;
  left: 0;
  position: absolute;
  z-index: 1;
}
</style>
