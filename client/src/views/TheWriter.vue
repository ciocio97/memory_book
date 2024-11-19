<template>
  <div v-if="page === 0" class="container">
    <p class="title">★ 을 골라줘</p>
    <div class="stickerContainer">
      <div v-for="(image, index) in images">
        <button class="stickerButton">
          <img
            :key="'sticker' + index"
            :src="image.src"
            :alt="'sticker' + index"
            class="sticker"
          />
        </button>
      </div>
    </div>
    <div class="buttonContainer">
      <button class="button nextButton" @click.once="onClickNextButton">
        다 음
      </button>
    </div>
  </div>
  <div v-else class="container">
    <p class="title">♥ 도 남겨줄래</p>
    <div class="textareaContainer">
      <textarea class="textarea"></textarea>
    </div>
    <div class="buttonContainer">
      <button class="button prevButton" @click.once="onClickPrevButton">
        ←
      </button>
      <button class="button nextButton" @click.once="onClickCompleteButton">
        전 송 !
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const modules = import.meta.glob('../assets/images/sticker/*.svg', {
  eager: true,
});
const images = [];
const page = ref(0);

const onClickPrevButton = () => {
  page.value = 0;
};

const onClickNextButton = () => {
  page.value = 1;
};

const onClickCompleteButton = () => {
  console.log('전 송 !');
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
</script>

<style scoped>
.container {
  width: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;

  background-color: rgb(255, 255, 255, 0.2);
  padding: 1.8rem 0;
  border: 1px solid rgba(127, 187, 255, 0.1);
  border-radius: 2rem;
  box-shadow:
    rgba(14, 63, 126, 0.04) 0px 0px 0px 1px,
    rgba(42, 51, 69, 0.04) 0px 1px 1px -0.5px,
    rgba(42, 51, 70, 0.04) 0px 3px 3px -1.5px,
    rgba(42, 51, 70, 0.04) 0px 6px 6px -3px,
    rgba(14, 63, 126, 0.04) 0px 12px 12px -6px,
    rgba(14, 63, 126, 0.04) 0px 24px 24px -12px;

  .title {
    width: 100%;
    font-size: 3rem;
    color: #fff;
  }

  .stickerContainer {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    gap: 1rem;
    padding: 1rem 0;

    .stickerButton {
      width: 110px;
      border-radius: 5rem;
      background-color: rgb(255, 255, 255);
      box-shadow:
        rgba(0, 0, 0, 0.3) 0px 2px 4px,
        rgba(0, 0, 0, 0.2) 0px 7px 13px -3px,
        rgba(0, 0, 0, 0.2) 0px -3px 0px inset;
      .sticker {
        width: 100%;
      }
    }
  }

  .textareaContainer {
    height: 160px;
    padding: 1rem 2rem 1.5rem 2rem;
    .textarea {
      width: 100%;
      height: 100%;
      border-radius: 0.5rem;
    }
  }

  .buttonContainer {
    width: 240px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-shrink: 1;
    gap: 0.5rem;

    .button {
      padding: 0.4rem 0;
      border-radius: 1rem;
      font-size: 2rem;
      color: #fff;
      background-color: rgba(127, 187, 255, 0.8);
      cursor: pointer;
      &:hover {
        color: rgb(127, 187, 255);
        background-color: rgb(255, 255, 255);
        transition: 0.3s;
      }

      &.prevButton {
        padding: 0.4rem 0.8rem;
        height: 100%;
      }

      &.nextButton {
        flex-grow: 1;
      }
    }
  }
}
</style>
