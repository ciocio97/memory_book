<template>
  <TheLayout>
    <RouterLink to="/read">Read</RouterLink>
    <RouterLink to="/write">Write</RouterLink>
    <RouterView />
  </TheLayout>
</template>

<script setup>
import TheLayout from './components/TheLayout.vue';
import './styles/global.css'; // global css
</script>

<style>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
