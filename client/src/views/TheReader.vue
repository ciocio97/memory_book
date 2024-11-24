<template>
  <div v-show="isModalOpen" class="modalContainer">
    <div class="wrapper">
      <div class="textareaContainer">
        <textarea readonly class="textarea">
안녕하세요 내 편지는 테스트용입니다 안녕하세요 내 편지는 테스트용입니다 안녕하세요 내 편지는 테스트용입니다</textarea
        >
      </div>
      <button class="button" @click="onClickCloseModalButton">닫 기</button>
    </div>
  </div>
  <div class="wrapper-reader">
    <!-- <div class="stickerContainer">
      <button v-for="(memo, index) in memoList" class="stickerButton" @click="() => onClickStickerButton(item.id)">
        <img
          :key="'memo' + index"
          :src="memo.src"
          :alt="'memo' + index"
          class="sticker"
        />
      </button>
    </div> -->
    <!-- test -->
    <div class="stickerContainer">
      <button
        v-for="(item, index) in images"
        class="stickerButton"
        @click="() => onClickStickerButton(item.id)"
      >
        <img
          :key="'sticker' + index"
          :src="item.src"
          :alt="'sticker' + index"
          class="sticker"
        />
      </button>
    </div>
    <!-- test -->
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { get } from '@/api';

// sample sticker data
const modules = import.meta.glob('../assets/images/sticker/*.svg', {
  eager: true,
});

const images = [];
const memoList = ref([]);
const isModalOpen = ref(false);

onMounted(() => {
  get('/memo')
    .then((res) => {
      console.log(res);
      const data = res.data; // memo list

      memoList.value = [];

      for (let item of data) {
        const memo = {
          id: item.memoId,
          src: images[item.imgNum].src,
        };

        memoList.value.push(memo);
      }
    })
    .catch((err) => {
      console.log(err);
    });
});

const onClickStickerButton = (id) => {
  isModalOpen.value = true; // for test

  get(`/memo/detail/${id}`)
    .then((res) => {
      console.log(res);
      isModalOpen.value = true;
    })
    .catch((err) => {
      console.log(err);
    });
};

const onClickCloseModalButton = () => {
  isModalOpen.value = false;
};

const initImages = () => {
  let index = 0;
  for (let file in modules) {
    const image = { id: index, src: modules[file].default };
    images.push(image);
    index++;
  }
};

initImages();
initImages();
initImages();
initImages();
initImages();
initImages();
initImages();
initImages();
initImages();
initImages();
initImages();
initImages();
initImages();
initImages();
</script>

<style scopes>
.wrapper-reader {
  height: 100vh;

  .stickerContainer {
    height: 100%; /* fix size */
    display: flex;
    justify-content: flex-start;
    align-content: flex-start;
    flex-wrap: wrap;
    padding: 1rem 0 7rem 0;
    overflow-y: auto;

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

.modalContainer {
  height: calc(100vh - 100px);
  width: 320px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  color: #fff;
  z-index: 1;

  .wrapper {
    padding: 1.5rem 1.6rem;
    border-radius: 0.4rem;
    box-shadow:
      rgba(0, 0, 0, 0.3) 0px 19px 38px,
      rgba(0, 0, 0, 0.22) 0px 15px 12px;
    background-color: #fff;

    .textareaContainer {
      height: 300px; /* fix size */

      .textarea {
        width: 100%;
        height: 100%;
        border-radius: 0.5rem;

        &:focus-visible {
          outline: none;
        }
      }
    }
  }

  .button {
    width: 100%;
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
</style>
