<template>
  <div>
    <b-dropdown-item to="/ingredient">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.ingredient')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/instruction">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.instruction')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/recipe">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.recipe')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/rating">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.rating')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/recipe-book">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.recipeBook')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/recipe-book-relation">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.recipeBookRelation')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/favorite-relation">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.favoriteRelation')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
