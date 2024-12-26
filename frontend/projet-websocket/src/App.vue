<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png" />
    <ListingsComponent />
  </div>
</template>

<script>
import ListingsComponent from './components/ListingsComponent.vue'; // Import du composant ListingsComponent

export default {
  name: 'App',
  components: {
    ListingsComponent, // Déclaration du composant
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>