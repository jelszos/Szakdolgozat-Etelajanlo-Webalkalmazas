<template>
  <div class="container">
    <div class="mt-5">
      <div v-if="user">
        <div class="d-flex gap mb-5">
          <div>
            <b-img :src="getAvatarImg(user.adminUser?.avatar)" style="width: 300px; height: 500px; object-fit: contain"></b-img>
          </div>
          <div class="w-100 d-flex flex-column justify-content-center">
            <div>
              <span style="font-size: 36px">
                {{ user.adminUser.firstName + ' ' + user.adminUser.lastName }}
              </span>
              <b-badge v-if="user.sumRecipes > 0" class="ml-2 p-2" variant="info"
                >{{ user.sumRecipes + ' ' + t$('szakdolgozatApp.recipe.plural') }}
              </b-badge>
              <b-badge v-if="user.sumRecipeBooks > 0" class="ml-2 p-2" variant="primary"
                >{{ user.sumRecipeBooks + ' ' + t$('szakdolgozatApp.recipeBook.plural') }}
              </b-badge>
            </div>

            <p style="color: #919191; font-weight: 500">
              <b-tooltip target="reg-time">Active member since</b-tooltip>
              <font-awesome-icon icon="stopwatch" id="reg-time"></font-awesome-icon>
              {{ moment(user.adminUser.createdDate).format('LL') }}
            </p>
          </div>
        </div>
        <b-list-group class="d-flex flex-row">
          <b-list-group-item button :active="activeTab == 'recipes'" v-model="activeTab" @click="changeTab('recipes')">
            {{ t$('profile.recipes') }}
          </b-list-group-item>
          <b-list-group-item button :active="activeTab == 'recipeBooks'" v-model="activeTab" @click="changeTab('recipeBooks')">
            {{ t$('profile.recipeBooks') }}
          </b-list-group-item>
        </b-list-group>
        <div v-if="activeTab == 'recipes'">
          <div class="position-relative text-center my-5">
            <b-img :src="headerBg" class="carousel-header-img" />
            <h1 class="carousel-header mb-4 position-relative">{{ t$('profile.recentRecipes') }} {{ user.adminUser.firstName }}</h1>
          </div>
          <div class="grid" v-if="user?.recipes.length > 0">
            <template v-for="recipe in user?.recipes" :key="recipe">
              <recipe-card
                :recipe="recipe"
                :show-user="false"
                :delete-recipe="store?.account?.login == route.params.userId ? () => deleteRecipe(recipe) : null"
              />
            </template>
          </div>
          <div v-else class="no-content d-flex justify-content-center align-items-center">
            <p style="font-size: 26px; font-weight: 500" class="text-slate-900">{{ t$('profile.noData') }}</p>
          </div>
        </div>
        <div v-if="activeTab == 'recipeBooks'">
          <div class="position-relative text-center my-5">
            <b-img :src="headerBg" class="carousel-header-img" />
            <h1 class="text-slate-900 carousel-header mb-4 position-relative">
              {{ user.adminUser.firstName }} {{ t$('profile.tabRecipeBooks') }}
            </h1>
          </div>
          <div class="grid" v-if="user?.recipeBooks.length > 0">
            <recipe-book-card
              v-for="recipeBook in user?.recipeBooks"
              :key="recipeBook.id"
              :recipe-book="recipeBook"
              :show-user="false"
              :remove-from-recipe-books="deleteRecipeBook"
              :show-options="store?.account?.login == route.params.userId"
            />
          </div>
          <div v-else class="no-content d-flex justify-content-center align-items-center">
            <p style="font-size: 26px; font-weight: 500" class="text-slate-900">{{ t$('profile.noData') }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./profile-view.component.ts"></script>

<style>
.grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  column-gap: 8px;
  row-gap: 18px;
}
.gap {
  gap: 22px;
}
</style>
