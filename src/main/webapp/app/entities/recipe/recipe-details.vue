<template>
  <div class="row justify-content-center">
    <b-container>
      <div class="mb-3 d-flex justify-content-between">
        <button type="submit" @click.prevent="previousState()" class="btn btn-link back-button" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('szakdolgozatApp.recipe.browse')"></span>
        </button>
        <router-link
          v-if="recipe.id && recipe?.user?.id == store?.account?.id"
          :to="{ name: 'RecipeEdit', params: { recipeId: recipe.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
      <div v-if="recipe">
        <div class="position-relative">
          <b-img :src="recipe.imageUrl" class="recipe-img" onerror=""></b-img>
          <p
            class="position-absolute text-slate-900"
            style="bottom: 0; left: 18px; background-color: #fefefe; font-size: 24px; font-weight: 700"
          >
            {{ recipe.title }}
          </p>
        </div>
        <div class="d-flex justify-content-between">
          <b-row style="gap: 8px" class="my-3">
            <b-badge variant="info">{{ t$(`szakdolgozatApp.FoodCategory.${recipe.foodCategory}`) }}</b-badge>
            <b-badge variant="primary">{{ t$(`szakdolgozatApp.FoodType.${recipe.foodType}`) }}</b-badge>
          </b-row>
          <div class="d-flex gap-custom align-items-center">
            <div class="rounded-pill py-1 bg-dark-gray star favorite-badge" style="padding: 0 2px" @click="toggleFavorite">
              <font-awesome-icon icon="heart" :style="recipe?.isFavorite ? 'color: red' : 'color: rgb(239, 239, 239)'" />
            </div>
            <div class="rounded-pill p-1 bg-dark-gray favorite-badge" @click="setMarkModalTrue">
              <font-awesome-icon icon="bookmark" style="color: rgb(239, 239, 239)" />
            </div>
          </div>
        </div>
        <b-row class="align-items-center">
          <!-- Wrap avatar and user info inside a <router-link> for navigation -->
          <router-link :to="`/profile-view/${recipe.user?.login}/view`" class="d-flex align-items-center text-decoration-none">
            <b-avatar :src="getAvatarImg(recipe.user?.avatar)" size="xxl" v-if="recipe.user?.login"></b-avatar>
            <div class="ml-2">
              {{ recipe.user?.firstName + ' ' + recipe.user?.lastName }}
            </div>
          </router-link>
        </b-row>
        <div style="font-size: 24px; font-weight: 500" class="mb-5 mt-4 text-slate-900">
          {{ recipe.description }}
        </div>
        <div v-if="recipe?.instructions?.length > 0" class="mb-5">
          <b-row style="gap: 18px; flex-wrap: nowrap">
            <div class="ingredients">
              <p class="mb-4 text-slate-900" style="font-size: 24px; font-weight: 500">{{ t$('szakdolgozatApp.recipe.ingredients') }}</p>
              <div v-for="(instruction, index) in recipe?.instructions" :key="index">
                <div v-for="(ingredient, ingIndex) in instruction.ingredients" :key="ingIndex" class="my-2 text-slate-900">
                  {{ ingredient.amount }} <strong>{{ $t('szakdolgozatApp.Unit.' + ingredient.unit) }}</strong> of {{ ingredient.name }}
                </div>
              </div>
            </div>
            <div class="">
              <p style="font-size: 24px; font-weight: 500" class="instruction text-slate-900">
                {{ t$('szakdolgozatApp.recipe.instructions') }}
              </p>
              <ul style="list-style: none; padding-inline-start: 0" class="ml-5 text-slate-900">
                <li v-for="(instruction, index) in recipe?.instructions" :key="index" class="my-4">
                  <b-row style="gap: 12px; flex-wrap: nowrap">
                    <div>
                      <span class="step-number"> {{ index + 1 }}. </span>
                    </div>
                    <div>
                      {{ instruction.description }}
                      <p>
                        <font-awesome-icon icon="clock" />
                        {{ instruction.requiredTime }} minutes
                      </p>
                    </div>
                  </b-row>
                </li>
              </ul>
            </div>
          </b-row>
        </div>
        <div class="pb-4 pt-5 d-flex align-items-center justify-content-between">
          <span class="mb-4 ratings text-slate-900">{{ t$('szakdolgozatApp.recipe.ratings.section') }}</span>
          <div class="d-flex align-items-center total-rating">
            <font-awesome-icon icon="star" style="color: yellow" />
            <span>{{ recipe.totalRating }}</span>
          </div>
        </div>
        <b-alert show variant="info" v-if="visibilityRatingEditForm || isRatingFormVisible()">
          <font-awesome-icon icon="info-circle" />
          {{ t$('szakdolgozatApp.recipe.ratings.writeRating') }}
        </b-alert>
        <b-card v-if="visibilityRatingEditForm || isRatingFormVisible()">
          <b-form @submit.prevent="submitRating">
            <b-form-group :label="t$('szakdolgozatApp.recipe.ratings.rate')" label-for="rate">
              <b-form-rating id="rate" name="ratingRate" v-model="ratingFormData.rate"></b-form-rating>
            </b-form-group>
            <b-form-group :label="t$('szakdolgozatApp.recipe.ratings.commentTitle')" label-for="title">
              <b-form-input
                id="title"
                type="text"
                name="ratingTitle"
                v-model="ratingFormData.title"
                :placeholder="t$('szakdolgozatApp.recipe.ratings.commentTitlePh')"
              ></b-form-input>
            </b-form-group>
            <b-form-group :label="t$('szakdolgozatApp.recipe.ratings.commentDesc')" label-for="description">
              <b-form-input
                id="description"
                type="text"
                name="ratingDescription"
                v-model="ratingFormData.description"
                :placeholder="t$('szakdolgozatApp.recipe.ratings.commentDescPh')"
              ></b-form-input>
              <b-form-group :label="t$('szakdolgozatApp.recipe.ratings.imageLabel')" label-for="image" class="mt-2">
                <input id="file-upload" type="file" name="imageUrl" @change="onFileChanged($event)" />
              </b-form-group>
              <div v-if="ratingFormData.imageUrl" class="position-relative hover-delete-button" style="width: fit-content">
                <b-button
                  class="position-absolute rounded-circle"
                  variant="danger"
                  @click="deleteImgFromRating"
                  size="sm"
                  style="right: 5px; top: 5px; padding: 2px"
                >
                  <font-awesome-icon icon="close"></font-awesome-icon>
                </b-button>
                <img :src="ratingFormData.imageUrl" style="width: 150px; height: 150px; object-fit: cover" alt="rating image" />
              </div>
            </b-form-group>

            <b-button
              class="btn btn-primary mt-4"
              variant="primary"
              type="submit"
              v-text="t$('szakdolgozatApp.recipe.ratings.giveRating')"
            ></b-button>
          </b-form>
        </b-card>
        <!--  Ratings -->
        <b-row class="mt-5">
          <div class="w-100">
            <div>
              <div v-for="(rating, index) in limitedRatings" :key="index">
                <b-row class="align-items-center justify-content-between">
                  <div>
                    <b-row class="align-items-center mb-2">
                      <router-link :to="`/profile-view/${rating.user?.login}/view`" class="d-flex align-items-center text-decoration-none">
                        <b-avatar :src="getAvatarImg(rating.user?.avatar)" size="xxl" v-if="rating.user?.login"></b-avatar>
                        <div class="ml-2">
                          {{ rating.user?.firstName + ' ' + rating.user?.lastName }}
                        </div>
                      </router-link>
                    </b-row>
                    <b-form-rating
                      id="rate"
                      name="ratingRate"
                      style="max-width: 160px; width: 160px"
                      readonly
                      v-model="rating.rate"
                    ></b-form-rating>
                  </div>

                  <b-dropdown v-if="isMyComment(rating)" text="">
                    <b-dropdown-item @click="toggleEditRatingForm(rating)">
                      {{ t$('szakdolgozatApp.recipeBook.card.edit') }}
                    </b-dropdown-item>
                    <b-dropdown-item variant="danger" @click="deleteRating(rating.id)">
                      {{ t$('szakdolgozatApp.recipe.home.deleteRating') }}
                    </b-dropdown-item>
                  </b-dropdown>
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

              <!-- Show More and Check All Ratings Buttons -->
              <div v-if="recipe?.ratings?.length > 2" class="mt-5">
                <b-button v-if="!showAllRatings" @click="showAllRatings = true">{{
                  t$('szakdolgozatApp.recipe.ratings.showMore')
                }}</b-button>
                <b-button v-else @click="showAllRatings = false">{{ t$('szakdolgozatApp.recipe.ratings.showLess') }}</b-button>
                <b-button class="ml-3" :to="'/recipe/' + recipe.id + '/ratings'">{{
                  t$('szakdolgozatApp.recipe.ratings.checkAll')
                }}</b-button>
              </div>
            </div>
          </div>
        </b-row>
      </div>
    </b-container>
  </div>
  <p>{{ isAddFavModalData.recipe?.id }}</p>

  <mark-or-create-r-b :on-close="setMarkModalFalse" :is-open="isAddFavModalData.isOpen" :recipe-id="recipe?.id" />
</template>

<script lang="ts" src="./recipe-details.component.ts"></script>

<style>
.hover-delete-button button {
  display: none;
}
.hover-delete-button:hover button {
  display: block;
}

.rating_img {
  width: 150px;
  max-width: 150px;
  height: 150px;
  max-height: 150px;
  object-fit: cover;
}

.step-number {
  padding: 2px;
  background-color: gray;
  border-radius: 99px;
  color: white;
  display: flex;
  width: 33px;
  height: 33px;
  align-items: center;
  justify-content: center;
}

.ingredients {
  border-right: 1px solid #b8b8b8;
  padding: 0 10px;
}

.ingredients p:first-child {
  background-color: #fcde9c;
  width: fit-content;
  padding: 0 8px;
  border-radius: 8px;
  line-height: 38px;
}

.instruction {
  background-color: #fcde9c;
  width: fit-content;
  padding: 0 8px;
  border-radius: 8px;
  line-height: 38px;
}

.total-rating {
  font-size: 20px;
  padding-right: 12px;
  padding-left: 6px;
  background-color: #5a5a5a;
  color: #fefefe;
  border-radius: 16px;
}

.ratings {
  font-weight: 500;
  font-size: 32px;
}

.recipe-img {
  width: 100%;
  min-height: 400px;
  max-height: 400px;
  object-fit: cover;
}

.back-button,
.back-button:hover {
  padding-left: 0;
  text-decoration: none;
}

.gap-custom {
  gap: 8px;
}

.gap-custom div:hover {
  cursor: pointer;
  transform: scale(1.2);
  transition: 0.2s ease-in-out;
}
</style>
