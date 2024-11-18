<template>
  <div class="container">쓰기 페이지</div>
  <div v-for="(image, index) in images">
    <button>
      <img :key="'sticker' + index" :src="image.src" :alt="'sticker' + index" />
    </button>
  </div>
  <!-- <img src="../assets/images/sticker/sticker_rainbow.svg" /> -->
</template>

<script setup>
const images = [
  { src: '@/assets/sticker_rainbow.svg' },
  { src: '@/assets/sticker_butterfly.svg' },
  { src: '@/assets/sticker_sun.svg' },
  { src: '@/assets/sticker_star.svg' },
  { src: '@/assets/sticker_cloud.svg' },
  { src: '@/assets/sticker_earth.svg' },
];
</script>

<style>
.container {
  background-color: white;
  border-radius: 0.5rem;
}
</style>
