<template>
  <div class="w-100 d-flex justify-content-center">
    <b-navbar data-cy="navbar" toggleable="md" type="dark" class="jh-navbar">
      <b-navbar-brand class="logo" b-link to="/">
        <img class="logo-img" :src="logo" alt="logo" />
        <span v-text="t$('global.title')" class="navbar-title"></span>
      </b-navbar-brand>
      <b-navbar-toggle
        right
        class="jh-navbar-toggler d-lg-none"
        href="javascript:void(0);"
        data-toggle="collapse"
        target="header-tabs"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <font-awesome-icon icon="bars" />
      </b-navbar-toggle>

      <b-collapse is-nav id="header-tabs">
        <b-navbar-nav class="ml-auto">
          <b-nav-item v-if="!isNewRecipe && authenticated" id="new-recipe-menu" class="navbar-items" to="/recipe/new" exact>
            <span class="icon-text" style="color: black">
              <font-awesome-icon icon="plus" />
              <span v-text="t$('global.menu.new-recipe')"></span>
            </span>
          </b-nav-item>
          <b-nav-item id="home-menu" class="navbar-items" to="/" exact>
            <span class="icon-text">
              <font-awesome-icon icon="home" />
              <span class="no-bold" v-text="t$('global.menu.home')"></span>
            </span>
          </b-nav-item>

          <b-nav-item id="search-menu" class="navbar-items" to="/search" exact>
            <span class="icon-text">
              <font-awesome-icon icon="search" />
              <span class="no-bold" v-text="t$('global.menu.search')"></span>
            </span>
          </b-nav-item>
          <b-nav-item-dropdown right id="recipe-book-menu" active-class="active" class="pointer">
            <template #button-content>
              <span class="navbar-dropdown-menu">
                <font-awesome-icon icon="book" />
                <span class="no-bold" v-text="t$('global.menu.recipe-book')"></span>
              </span>
            </template>
            <b-dropdown-item to="/recipe-book-page" active-class="active">
              <font-awesome-icon icon="th-list" />
              <span v-text="t$('global.menu.browse-recipe-book')"></span>
            </b-dropdown-item>
            <b-dropdown-item v-if="authenticated" to="/recipe-book-my-page" active-class="active">
              <font-awesome-icon icon="th-list" />
              <span v-text="t$('global.menu.my-recipe-book')"></span>
            </b-dropdown-item>
          </b-nav-item-dropdown>

          <b-nav-item-dropdown
            right
            href="javascript:void(0);"
            id="account-menu"
            :class="{ 'router-link-active': subIsActive('/account') }"
            active-class="active"
            class="pointer"
            data-cy="accountMenu"
          >
            <template #button-content>
              <span class="navbar-dropdown-menu">
                <font-awesome-icon icon="user" />
                <span class="no-bold" v-text="t$('global.menu.account.main')"></span>
              </span>
            </template>
            <b-dropdown-item :to="'/profile-view/' + userLogin + '/view'" v-if="authenticated" active-class="active">
              <font-awesome-icon icon="user-alt" />
              <span v-text="t$('global.menu.account.profile')"></span>
            </b-dropdown-item>
            <b-dropdown-item data-cy="settings" to="/account/settings" v-if="authenticated" active-class="active">
              <font-awesome-icon icon="wrench" />
              <span v-text="t$('global.menu.account.settings')"></span>
            </b-dropdown-item>
            <b-dropdown-item data-cy="passwordItem" to="/account/password" v-if="authenticated" active-class="active">
              <font-awesome-icon icon="lock" />
              <span v-text="t$('global.menu.account.password')"></span>
            </b-dropdown-item>
            <b-dropdown-item data-cy="logout" v-if="authenticated" @click="logout()" id="logout" active-class="active">
              <font-awesome-icon icon="sign-out-alt" />
              <span v-text="t$('global.menu.account.logout')"></span>
            </b-dropdown-item>
            <b-dropdown-item data-cy="login" v-if="!authenticated" @click="openLogin()" id="login" active-class="active">
              <font-awesome-icon icon="sign-in-alt" />
              <span v-text="t$('global.menu.account.login')"></span>
            </b-dropdown-item>
            <b-dropdown-item data-cy="register" to="/register" id="register" v-if="!authenticated" active-class="active">
              <font-awesome-icon icon="user-plus" />
              <span v-text="t$('global.menu.account.register')"></span>
            </b-dropdown-item>
          </b-nav-item-dropdown>

          <b-nav-item-dropdown
            right
            id="admin-menu"
            v-if="hasAnyAuthority('ROLE_ADMIN') && authenticated"
            :class="{ 'router-link-active': subIsActive('/admin') }"
            active-class="active"
            class="pointer"
            data-cy="adminMenu"
          >
            <template #button-content>
              <span class="navbar-dropdown-menu">
                <font-awesome-icon icon="users-cog" />
                <span class="no-bold" v-text="t$('global.menu.admin.main')"></span>
              </span>
            </template>
            <b-dropdown-item to="/admin/user-management" active-class="active">
              <font-awesome-icon icon="users" />
              <span v-text="t$('global.menu.admin.userManagement')"></span>
            </b-dropdown-item>
            <b-dropdown-item v-if="openAPIEnabled" to="/admin/docs" active-class="active">
              <font-awesome-icon icon="book" />
              <span v-text="t$('global.menu.admin.apidocs')"></span>
            </b-dropdown-item>
            <b-dropdown-item v-if="!inProduction" href="http://localhost:8080/h2-console/" target="_tab">
              <font-awesome-icon icon="database" />
              <span v-text="t$('global.menu.admin.database')"></span>
            </b-dropdown-item>
          </b-nav-item-dropdown>
          <b-nav-item-dropdown
            v-if="hasAnyAuthority('ROLE_ADMIN')"
            right
            id="entity-menu"
            active-class="active"
            class="pointer"
            data-cy="entity"
          >
            <template #button-content>
              <span class="navbar-dropdown-menu">
                <font-awesome-icon icon="th-list" />
                <span class="no-bold" v-text="t$('global.menu.entities.main')"></span>
              </span>
            </template>
            <entities-menu></entities-menu>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script lang="ts" src="./jhi-navbar.component.ts"></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* ==========================================================================
    Navbar
    ========================================================================== */

.jh-navbar {
  background: rgba(59, 58, 58, 0.8);
  box-shadow: 0 8px 32px 0 rgba(112, 115, 133, 0.37);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(5.5px);
  border-radius: 10px;

  max-width: 1140px;
  width: 1140px;
  z-index: 10;
}

@media only screen and (max-width: 1200px) {
  .jh-navbar {
    width: unset;
  }
}

.jh-navbar .profile-image {
  margin: -10px 0px;
  height: 40px;
  width: 40px;
  border-radius: 50%;
}

.jh-navbar .dropdown-item.active,
.jh-navbar .dropdown-item.active:focus,
.jh-navbar .dropdown-item.active:hover {
  color: #353d47;
}

#home-menu #entity-menu {
  color: Black;
  background-color: white;
}
.jh-navbar .dropdown-toggle::after {
  margin-left: 0.15em;
}

.jh-navbar ul.navbar-nav {
  padding: 0.5em;
  display: flex;
  flex-wrap: wrap;
  justify-content: end;
}

.jh-navbar .navbar-nav .nav-item {
  margin-left: 1.5rem;
}

.jh-navbar a.nav-link,
.jh-navbar .no-bold {
  font-weight: 500;
}

.jh-navbar .jh-navbar-toggler {
  color: #ccc;
  font-size: 1.5em;
  padding: 10px;
}

.jh-navbar .jh-navbar-toggler:hover {
  color: #fff;
}

@media screen and (min-width: 768px) {
  .jh-navbar-toggler {
    display: none;
  }
}

@media screen and (min-width: 768px) and (max-width: 1150px) {
  span span {
    display: none;
  }
}

.navbar-title {
  display: inline-block;
  color: #d5d5d5;
}

/* ==========================================================================
    Logo styles
    ========================================================================== */
.navbar-brand.logo {
  padding: 0 7px;
}

.logo,
.icon-text {
  display: flex;
  align-items: center;
}

.logo .logo-img {
  height: 45px;
  display: inline-block;
  vertical-align: middle;
  width: 45px;
}

.logo-img {
  height: 100%;
  width: 100%;
  margin: 0 5px;
}
.dot {
  display: inline-block;
  width: 8px;
  height: 8px;
  margin-right: 8px;
  background-color: black; /* Or your preferred color */
  border-radius: 50%;
}

#new-recipe-menu {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 8px;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(6.6px);
  -webkit-backdrop-filter: blur(6.6px);
}
</style>
