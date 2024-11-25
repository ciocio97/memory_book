<template>
  <!-- modal start -->
  <div v-show="isModalOpen" class="modalContainer">
    <div class="wrapper">
      <div class="textareaContainer">
        <textarea readonly class="textarea">{{ memo }}</textarea>
      </div>
      <button class="button" @click="onClickCloseModalButton">닫 기</button>
    </div>
  </div>
  <!-- modal end -->
  <div v-if="!canRead">
    <p class="description">편지 조회 가능한 날은</p>
    <p class="description"><b>2024년 12월 9일</b></p>
    <p class="description">입니다</p>
    <button class="button" @click="onClickHomeButton">홈으로 가기</button>
  </div>
  <div v-else-if="!memoList.length && isFetched">
    <p class="description">편지가 없어요 ㅠ.ㅠ</p>
    <button class="button" @click="onClickHomeButton">홈으로 가기</button>
  </div>
  <div v-else-if="isFetched" class="wrapper-reader">
    <div class="stickerContainer">
      <button class="stickerButton" @click="onClickHomeButton">Home</button>
      <button
        v-for="(memo, index) in memoList"
        class="stickerButton"
        @click="() => onClickStickerButton(memo.id)"
      >
        <img
          :key="'memo' + index"
          :src="memo.src"
          :alt="'memo' + index"
          class="sticker"
        />
      </button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { AXIOS, get } from '@/api';

const router = useRouter();

// sample sticker data
const modules = import.meta.glob('../assets/images/sticker/*.svg', {
  eager: true,
});

const images = [];
const memoList = ref([]);
const isModalOpen = ref(false);
const memo = ref('');
const canRead = ref(false);
const isFetched = ref(false);

onMounted(() => {
  const access_token = localStorage.getItem('access_token');

  AXIOS.defaults.headers.common['Authorization'] = `Bearer ${access_token}`;

  isFetched.value = false;

  if (new Date('2024-12-09') < Date.now()) {
    canRead.value = true;
    get('/memo')
      .then((res) => {
        const data = res.data; // memo list
        const memos = [];

        for (let item of data) {
          const memo = {
            id: item.memoId,
            src: images[item.imgNum].src,
          };

          memos.push(memo);
        }

        memoList.value = memos;
        isFetched.value = true;
      })
      .catch((err) => {
        console.log(err);
      });
  }
});

const onClickStickerButton = (id) => {
  get(`/memo/detail/${id}`)
    .then((res) => {
      memo.value = res.data.text;
      isModalOpen.value = true;
    })
    .catch((err) => {
      console.log(err);
    });
};

const onClickCloseModalButton = () => {
  isModalOpen.value = false;
};

const onClickHomeButton = () => {
  router.push('/');
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
</script>

<style scopes>
.wrapper-reader {
  width: 320px;
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
      font-size: 1.3rem;
      cursor: pointer;
      box-shadow:
        rgba(0, 0, 0, 0.3) 0px 2px 4px,
        rgba(0, 0, 0, 0.2) 0px 7px 13px -3px,
        rgba(0, 0, 0, 0.2) 0px -3px 0px inset;

      /* &.on {
        transform: scale(1.1) translate3d(1%, -1%, 1rem);
        transition: 0.2s;
      } */

      :hover {
        opacity: 0.5;
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
}

.button {
  width: 100%;
  padding: 0.4rem 0;
  border-radius: 1rem;
  margin-top: 1.5rem;
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

.description {
  font-size: 2rem;
  color: #fff;
  margin-bottom: 1rem;
}
</style>
