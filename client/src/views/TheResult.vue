<template>
  <div v-if="hasReader()">
    <p class="text">전송이 완료되었습니다 !</p>
  </div>
  <div v-else>
    <button class="button" @click.once="onClickShareLinkButton">
      <img class="image" src="../assets/images/favicons/icon_blue_share.png" />
    </button>
    <p class="text">전송하고 싶은 친구에게</p>
    <p class="text">링크를 공유해주세요 !</p>
  </div>
</template>

<script setup>
import { hasReader } from '@/utils/memo';

const onClickShareLinkButton = () => {
  const sender_token = sessionStorage.getItem('sender_token');

  if (sender_token === null) {
    console.log('왜 sender_token 없냐 필수인데');
  }

  // kakao link share
  const url = `http://localhost:3000?sender=${sender_token}`;

  Kakao.Share.sendDefault({
    objectType: 'feed',
    content: {
      title: '나의 우주에 별을 남겨줘',
      description: '누가 나에게 편지를 썼을까? 확인해보자',
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
</script>

<style scoped>
.button {
  width: 55px;
  height: 55px;
  background: white;
  border-radius: 10rem;
  cursor: pointer;
  margin-bottom: 2rem;
  .image {
    width: 30px;
  }
  &:hover {
    opacity: 0.5;
  }
}

.text {
  color: #fff;
  font-size: 2rem;
  margin-bottom: 1rem;
}
</style>
