<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rating">
        <h2 class="jh-entity-heading" data-cy="ratingDetailsHeading">
          <span v-text="t$('szakdolgozatApp.rating.detail.title')"></span> {{ rating.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('szakdolgozatApp.rating.rate')"></span>
          </dt>
          <dd>
            <span>{{ rating.rate }}</span>
          </dd>
          <dt>
            <span v-text="t$('szakdolgozatApp.rating.title')"></span>
          </dt>
          <dd>
            <span>{{ rating.title }}</span>
          </dd>
          <dt>
            <span v-text="t$('szakdolgozatApp.rating.description')"></span>
          </dt>
          <dd>
            <span>{{ rating.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('szakdolgozatApp.rating.imageUrl')"></span>
          </dt>
          <dd>
            <span>{{ rating.imageUrl }}</span>
          </dd>
          <dt>
            <span v-text="t$('szakdolgozatApp.rating.createdDate')"></span>
          </dt>
          <dd>
            <span v-if="rating.createdDate">{{ formatDateLong(rating.createdDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('szakdolgozatApp.rating.user')"></span>
          </dt>
          <dd>
            {{ rating.user ? rating.user.id : '' }}
          </dd>
          <dt>
            <span v-text="t$('szakdolgozatApp.rating.recipe')"></span>
          </dt>
          <dd>
            <div v-if="rating.recipe">
              <router-link :to="{ name: 'RecipeView', params: { recipeId: rating.recipe.id } }">{{ rating.recipe.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="rating.id" :to="{ name: 'RatingEdit', params: { ratingId: rating.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./rating-details.component.ts"></script>
