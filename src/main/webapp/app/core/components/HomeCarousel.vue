<script lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import RecipeCard from '@/core/components/RecipeCard.vue';
import { Carousel, Navigation, Pagination, Slide } from 'vue3-carousel';

export default {
  name: 'HomeCarousel',
  components: { Slide, Carousel, RecipeCard, FontAwesomeIcon, Pagination, Navigation },
  props: {
    recipes: {
      type: Array,
      required: true,
    },
    config: {
      type: Object,
      required: true,
    },
  },
  setup() {},
  methods: {},
};
</script>

<template>
  <Carousel v-bind="config" class="p-2">
    <Slide v-for="recipe in recipes" :key="recipe.id">
      <div class="carousel__item mx-2">
        <recipe-card :recipe="recipe" />
      </div>
    </Slide>
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
      <Pagination />
    </template>
  </Carousel>
</template>

<style scoped>
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
.fancy-display {
  font-size: 2.5rem; /* Slightly larger than display-4 */
  font-weight: 700; /* Bolder font weight */
  color: #333; /* Darker color for readability */
  text-transform: uppercase; /* Uppercase for a modern look */
  letter-spacing: 1px; /* Slightly spaced letters */
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
  border-bottom: 3px solid #ff6f61; /* Accent border at the bottom */
  padding-bottom: 5px;
}
</style>
