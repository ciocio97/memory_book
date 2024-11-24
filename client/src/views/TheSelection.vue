<template>
  <div class="wrapper-selection">
    <div class="logoContainer">
      <p class="logo">나의 우주에 너라는</p>
      <p class="logo">☆ 을 남겨줘</p>
    </div>
    <div class="faviconContainer">
      <div class="favicon">
        <button class="button" @click="onClickShareLinkButton">
          <img
            class="image"
            src="../assets/images/favicons/icon_blue_share.png"
          />
        </button>
        <p class="text">링크 공유</p>
      </div>
      <div class="favicon">
        <button class="button" @click.once="onClickWriteMemoButton">
          <img
            class="image"
            src="../assets/images/favicons/icon_blue_write.png"
          />
        </button>
        <p class="text">편지 쓰기</p>
      </div>
      <div class="favicon">
        <button class="button" @click.once="onClickReadMemoButton">
          <img
            class="image"
            src="../assets/images/favicons/icon_blue_read.png"
          />
        </button>
        <p class="text">편지 읽기</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const onClickShareLinkButton = () => {
  const linked_token = localStorage.getItem('linked_token');

  if (linked_token === null) {
    console.log('왜 linked_token 없냐 필수인데');
  }

  // kakao link share
  const url = `http://localhost:3000?receiver=${linked_token}`;

  Kakao.Share.sendDefault({
    objectType: 'feed',
    content: {
      title: '나의 우주에 별을 남겨줘',
      description: '나에게 편지를 써줘! 부탁해',
      imageUrl:
        'https://github.com/ciocio97/memory_book/blob/develop/client/src/assets/images/kakao_template_image.png',
      link: {
        mobileWebUrl: 'http://localhost:3000',
        webUrl: 'http://localhost:3000',
      },
    },
    buttons: [
      {
        title: '웹으로 보기',
        link: {
          mobileWebUrl: url,
          webUrl: url,
        },
      },
    ],
  });
};

const onClickWriteMemoButton = () => {
  router.push('/write');
};

const onClickReadMemoButton = () => {
  router.push('/read');
};
</script>

<style scoped>
.wrapper-selection {
  display: flex;
  flex-flow: column nowrap;
  gap: 5rem;
  .logoContainer {
    .logo {
      color: #fff;
      font-size: 2rem;
    }
  }

  .faviconContainer {
    display: flex;
    justify-content: space-around;
    gap: 2.5rem;

    .favicon {
      display: flex;
      flex-flow: column nowrap;
      gap: 0.7rem;
      .button {
        width: 55px;
        height: 55px;
        background: white;
        border-radius: 10rem;
        cursor: pointer;
        .image {
          width: 30px;
        }
        &:hover {
          opacity: 0.5;
        }
      }

      .text {
        color: #fff;
      }
    }
  }
}
</style>
