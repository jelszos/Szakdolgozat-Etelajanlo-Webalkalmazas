<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ingredient">
        <h2 class="jh-entity-heading" data-cy="ingredientDetailsHeading">
          <span v-text="t$('szakdolgozatApp.ingredient.detail.title')"></span> {{ ingredient.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('szakdolgozatApp.ingredient.name')"></span>
          </dt>
          <dd>
            <span>{{ ingredient.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('szakdolgozatApp.ingredient.amount')"></span>
          </dt>
          <dd>
            <span>{{ ingredient.amount }}</span>
          </dd>
          <dt>
            <span v-text="t$('szakdolgozatApp.ingredient.unit')"></span>
          </dt>
          <dd>
            <span v-text="t$('szakdolgozatApp.Unit.' + ingredient.unit)"></span>
          </dd>
          <dt>
            <span v-text="t$('szakdolgozatApp.ingredient.instruction')"></span>
          </dt>
          <dd>
            <div v-if="ingredient.instruction">
              <router-link :to="{ name: 'InstructionView', params: { instructionId: ingredient.instruction.id } }">{{
                ingredient.instruction.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="ingredient.id"
          :to="{ name: 'IngredientEdit', params: { ingredientId: ingredient.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ingredient-details.component.ts"></script>
