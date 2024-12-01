<template>
  <b-card
    :img-src="recipe?.imageUrl"
    img-alt="Image"
    class="custom-card"
    img-top
    style="color: #212529"
    tag="a"
    :href="`/recipe/${recipe.id}/view`"
    overlay
  >
    <div class="d-flex justify-content-between">
      <div v-if="recipe?.requiredTime" class="card-icon bg-warning rtime-badge text-center" style="background-color: yellow; color: black">
        <font-awesome-icon icon="clock" style="color: black" />
        <span>{{ recipe.requiredTimeSum }} mins</span>
      </div>

      <div v-if="recipe?.totalRating" class="bg-white-glass d-flex align-items-center" style="padding: 0 8px; border-radius: 16px">
        <font-awesome-icon icon="heart" style="color: #e30000"></font-awesome-icon>
        <span>{{ recipe?.favoriteCount }}</span>
      </div>

      <div class="d-flex gap-custom" v-if="toggleFavorite || markRecipe || deleteRecipe">
        <div
          v-if="toggleFavorite"
          class="py-1 bg-black-glass favorite-badge"
          style="padding: 0 2px"
          @click.stop.prevent="toggleFavorite(recipe)"
        >
          <font-awesome-icon icon="heart" :style="recipe?.isFavorite ? 'color: red' : 'color: rgb(239, 239, 239)'" />
        </div>
        <div v-if="markRecipe" class="p-1 bg-black-glass favorite-badge" @click.stop.prevent="markRecipe(recipe)">
          <font-awesome-icon icon="bookmark" style="color: rgb(239, 239, 239)" />
        </div>
        <div v-if="deleteRecipe" class="p-1 bg-danger favorite-badge" @click.stop.prevent="deleteRecipe(recipe)">
          <font-awesome-icon icon="close" style="color: rgb(239, 239, 239)" />
        </div>
      </div>
    </div>

    <div class="d-flex justify-content-between" style="height: 90%; width: 100%">
      <div class="d-flex image-bottom-badge">
        <div class="bg-yellow-glass rtime-badge px-1">
          <span class="text-slate-900">{{ t$(`szakdolgozatApp.FoodCategory.${recipe?.foodCategory}`) }}</span>
        </div>
        <div class="bg-primary-glass rtime-badge">
          <span>{{ t$(`szakdolgozatApp.FoodType.${recipe?.foodType}`) }}</span>
        </div>
      </div>
      <div class="image-bottom-badge d-flex align-items-center">
        <div class="bg-black-glass rtime-badge px-1">
          <font-awesome-icon icon="star" class="star" />
          <span style="color: #fefefe">{{ (Math.round(recipe.totalRating * 100) / 100).toFixed(1) }}</span>
        </div>
      </div>
    </div>

    <template #footer>
      <div class="text-left">
        <p class="truncate text-slate-900 text-truncate-line-1" style="font-size: 18px">{{ recipe.title }}</p>
        <p class="truncate text-slate-900 text-truncate-line-2" style="font-weight: 500; line-height: 1.3">{{ recipe.description }}</p>
        <div v-if="showUser" class="d-flex align-items-center">
          <router-link :to="`/profile-view/${recipe.user?.login}/view`" class="w-100 text-decoration-none">
            <div class="d-flex align-items-center creator">
              <b-avatar :src="getAvatarImg(recipe.user?.avatar)" size="sm"></b-avatar>
              <p class="ml-1 mt-3 ml-2 text-slate-900 white-hover">{{ recipe.user?.firstName + ' ' + recipe.user?.lastName }}</p>
            </div>
          </router-link>
        </div>
      </div>
    </template>
  </b-card>
</template>

<script>
import { functions } from '@/shared/utils/functions';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { useI18n } from 'vue-i18n';

export default {
  name: 'RecipeCard',
  components: { FontAwesomeIcon },
  props: {
    recipe: {
      type: Object,
      required: true,
    },
    showUser: {
      type: Boolean,
      required: false,
      default: true,
    },
    toggleFavorite: {
      type: Function,
      required: false,
    },
    markRecipe: {
      type: Function,
      required: false,
    },
    deleteRecipe: {
      type: Function,
      required: false,
    },
  },
  setup() {
    const { t: t$ } = useI18n();
    return {
      t$,
    };
  },
  computed: {
    functions() {
      return functions;
    },
  },
  mounted() {
    this.getAvatarImg(this.recipe.avatar);
  },
  methods: {
    getAvatarImg: number => functions.getAvatarImg(number),
    formatFoodCategory(category) {
      if (!category) return '';
      return category.replace(/_/g, ' ').replace(/\b\w/g, char => char.toUpperCase());
    },
  },
};
</script>

<style scoped>
.rtime-badge {
  width: fit-content;
  border-radius: 99px;
  display: flex;
  align-items: center;
  padding: 0 8px;
}

.rtime-badge span {
  font-size: 75%;
  color: #212529;
}

.image-bottom-badge {
  height: fit-content;
  align-self: end;
  gap: 8px;
}

.truncate {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.text-truncate-line-1 {
  -webkit-line-clamp: 1;
  line-clamp: 1;
}

.text-truncate-line-2 {
  -webkit-line-clamp: 2;
  line-clamp: 2;
}
.creator {
  width: 100%;
  border-radius: 12px;
  padding-left: 4px;
  display: flex;
  align-items: center;
  justify-content: start;
  transition: background-color 0.3s ease;
}
.creator:hover {
  background-color: #e6e6e6;
  color: white !important;
  cursor: pointer;
  text-decoration: none;
}

.custom-card:hover {
  text-decoration: none;
  -webkit-box-shadow: 0px 0px 30px -10px rgba(0, 0, 0, 0.75);
  -moz-box-shadow: 0px 0px 30px -10px rgba(0, 0, 0, 0.75);
  box-shadow: 0px 0px 30px -10px rgba(0, 0, 0, 0.75);
  transition: 0.2s ease-in-out;
}

.favorite-badge {
  width: fit-content;
  border-radius: 99px;
}
.favorite-badge:hover {
  width: fit-content;
  transform: scale(1.2);
  transition: 0.2s ease-in-out;
  //background-color: rgb(66, 66, 66) !important;
}

.gap-custom {
  gap: 8px;
}

.bg-yellow-glass {
  background: rgba(236, 255, 0, 0.6);
  border-radius: 16px;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(3.4px);
  border: 1.5px solid rgba(236, 255, 0, 0.97);
}

.bg-primary-glass {
  background: rgba(73, 150, 255, 0.6);
  border-radius: 16px;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(6.7px);
  -webkit-backdrop-filter: blur(6.7px);
  border: 1px solid rgba(73, 150, 255, 0.97);
}

.bg-white-glass {
  background: rgba(239, 239, 239, 0.89);
  border-radius: 16px;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(8.8px);
  -webkit-backdrop-filter: blur(8.8px);
  border: 1px solid rgba(239, 239, 239, 0.97);
}

.bg-black-glass {
  background: rgba(85, 85, 85, 0.8) !important;
  -webkit-box-shadow: 0 8px 32px 0 rgba(112, 115, 133, 0.37);
  box-shadow: 0 8px 32px 0 rgba(112, 115, 133, 0.37);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(5.5px);
}

.star {
  color: #fef100;
}
</style>
