<template>
  <div v-if="page === 0" class="wrapper-writer">
    <p class="title">★ 을 골라줘</p>
    <div class="stickerContainer">
      <button class="carouselButton" @click="onClickCarouselPrevButton">
        <
      </button>
      <button ref="sticker" class="stickerButton">
        <img
          :src="images[imageIndex].src"
          :alt="'sticker' + imageIndex"
          class="sticker"
        />
      </button>
      <button class="carouselButton" @click="onClickCarouselNextButton">
        >
      </button>
    </div>
    <div class="buttonContainer">
      <button class="button nextButton" @click.once="onClickNextButton">
        다 음
      </button>
    </div>
  </div>
  <div v-else class="wrapper-writer">
    <p class="title">♥ 도 남겨줄래</p>
    <div class="textareaContainer">
      <textarea class="textarea" :value="memo" @input="onInputText"></textarea>
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

import axios from 'axios';

const modules = import.meta.glob('../assets/images/sticker/*.svg', {
  eager: true,
});
const images = [];
const page = ref(0); // sticker vs memo
const imageIndex = ref(0);
const memo = ref('');

const onClickCarouselPrevButton = () => {
  const index = imageIndex.value - 1;
  imageIndex.value = index < 0 ? images.length - 1 : index;
};

const onClickCarouselNextButton = () => {
  const index = imageIndex.value + 1;
  imageIndex.value = index >= images.length ? 0 : index;
};

const onClickPrevButton = () => {
  page.value = 0;
};

const onClickNextButton = () => {
  page.value = 1;
};

const onInputText = (event) => {
  const text = event.target.value;
  memo.value = text;
};

const onClickCompleteButton = () => {
  console.log(imageIndex.value); // 선택된 이미지 번호
  console.log(memo.value); // 텍스트 내용
  // console.log(localStorage.getItem('access_token'));

  const access_token = localStorage.getItem('access_token');

  axios.defaults.headers.common['Authorization'] = `Bearer ${access_token}`;

  axios({
    headers: {
      withCredentials: true,
      // Authorization: `Bearer ${access_token}`,
    },
    method: 'post',
    url: 'http://localhost:8080/memo',
    data: {
      imgNum: imageIndex.value,
      text: memo.value,
    },
  })
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err);
    });
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
.wrapper-writer {
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
    font-size: 2.5rem;
    color: #fff;
  }

  .stickerContainer {
    height: 160px; /* fix size */
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 1rem;
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
      width: 120px;
      height: 120px;
      border-radius: 30rem;
      margin: 0 1rem;
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

  .textareaContainer {
    height: 160px; /* fix size */
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
      font-size: 1.8rem;
      color: #fff;
      background-color: rgba(127, 187, 255, 0.8);
      cursor: pointer;
      &:hover {
        color: rgb(127, 187, 255);
        background-color: rgb(255, 255, 255);
        transition: 0.1s;
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
