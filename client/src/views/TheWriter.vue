<template>
  <div class="container">
    <!-- <img class="title" src="../assets/images/create_memory_white.svg" /> -->
    <!-- <div class="stickerContainer">
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
    </div> -->
    <!-- <button class="nextButton">
      <img
        class="next"
        src="../assets/images/next.svg"
        @click.once="onClickNextButton"
      />
    </button> -->
    <img class="title" src="../assets/images/create_memo_white.svg" />
    <div class="inputContainer">
      <input class="input" />
    </div>
    <button class="nextButton">
      <img
        class="next"
        src="../assets/images/complete.svg"
        @click.once="onClickNextButton"
      />
    </button>
  </div>
</template>

<script setup>
const modules = import.meta.glob('../assets/images/sticker/*.svg', {
  eager: true,
});
const images = [];

const onClickNextButton = () => {
  console.log('next button click');
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
  background-color: rgb(255, 255, 255, 0.2);
  padding: 1rem 0;
  border: 7px solid rgba(127, 187, 255, 0.756);
  border-radius: 4.5rem;
  box-shadow:
    rgba(14, 63, 126, 0.04) 0px 0px 0px 1px,
    rgba(42, 51, 69, 0.04) 0px 1px 1px -0.5px,
    rgba(42, 51, 70, 0.04) 0px 3px 3px -1.5px,
    rgba(42, 51, 70, 0.04) 0px 6px 6px -3px,
    rgba(14, 63, 126, 0.04) 0px 12px 12px -6px,
    rgba(14, 63, 126, 0.04) 0px 24px 24px -12px;

  .title {
    width: 13rem;
    color: white;
  }

  .stickerContainer {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    padding: 1rem 0 1rem 0;

    .stickerButton {
      width: 110px;
      border-radius: 5rem;
      margin: 0 0.3rem;
      margin-bottom: 0.6rem;
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

  .inputContainer {
    width: 235px;
    height: 340px;
    padding: 1rem 2rem 1.5rem 2rem;
    .input {
      width: 100%;
      height: 100%;
      border-radius: 1rem;
    }
  }

  .nextButton {
    width: 70%;
    height: 50px;
    border-radius: 1.5rem;
    background-color: rgba(127, 187, 255, 0.8);

    .next {
      width: 3.5rem;
    }
  }
}
</style>
