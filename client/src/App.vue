<template>
  <TheLayout>
    <RouterLink to="/read">Read</RouterLink>
    <RouterLink to="/write">Write</RouterLink>
    <RouterView />
  </TheLayout>
</template>

<script>
import TheLayout from './components/TheLayout.vue';
import './styles/global.css'; // global css

export default {
  name: 'App',
  components: {
    TheLayout,
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
}
</style>
