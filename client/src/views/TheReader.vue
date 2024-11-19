<template>
  <div class="wrapper-reader">
    <div class="stickerContainer">
      <button v-for="(image, index) in images" class="stickerButton">
        <img
          :key="'sticker' + index"
          :src="image.src"
          :alt="'sticker' + index"
          class="sticker"
          @click="onClickStickerButton"
        />
      </button>
    </div>
  </div>
</template>

<script setup>
// sample sticker data
const modules = import.meta.glob('../assets/images/sticker/*.svg', {
  eager: true,
});
const images = [];

const onClickStickerButton = () => {
  // modal open
};

const initImages = () => {
  for (let file in modules) {
    const image = {
      src: modules[file].default,
    };
    images.push(image);
  }
};

initImages();
initImages();
</script>

<style scopes>
.wrapper-reader {
  height: 100vh;
  overflow-y: auto;
  .stickerContainer {
    height: 100%; /* fix size */
    display: flex;
    justify-content: flex-start;
    align-content: flex-start;
    flex-wrap: wrap;
    padding: 1rem 0;

    .carouselButton {
      color: #fff;
      font-size: 2.5rem;

      background-color: transparent;
      cursor: pointer;

      &:hover {
        opacity: 0.3;
        transition: 0.1s;
      }
    }

    .stickerButton {
      width: 55px;
      height: 55px;
      border-radius: 30rem;
      margin: 0.2rem;
      background-color: rgb(255, 255, 255);
      box-shadow:
        rgba(0, 0, 0, 0.3) 0px 2px 4px,
        rgba(0, 0, 0, 0.2) 0px 7px 13px -3px,
        rgba(0, 0, 0, 0.2) 0px -3px 0px inset;

      &.on {
        transform: scale(1.1) translate3d(1%, -1%, 1rem);
        transition: 0.2s;
      }

      .sticker {
        width: 100%;
      }
    }
  }
}
</style>
