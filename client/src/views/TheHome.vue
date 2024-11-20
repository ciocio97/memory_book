<template>
  <div class="logoContainer">
    <p class="logo">나의 우주에</p>
    <p class="logo">너라는</p>
    <p class="logo">☆ 을</p>
    <p class="logo">남겨줘</p>
  </div>
  <!-- todo: 자체제작 favicon 으로 변경 -->
  <button class="loginButtons" @click.once="onClickKakaoLoginButton">
    <img
      class="kakaoImage"
      src="../assets/images/kakao_login_image.png"
      alt="kakao_login_btn"
    />
  </button>
</template>

<script setup>
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

const route = useRoute();

onMounted(() => {
  console.log('mounted');
  const splittedUrl = window.location.href.split('?code=');
  console.log(splittedUrl[1]);
  if (splittedUrl.length === 2) {
    console.log('login 시도');
    const authorization_code = splittedUrl[1];
    axios({
      headers: {
        withCredentials: true,
      },
      method: 'get',
      url: 'http://localhost:8080/oauth/kakao/callback',
      params: {
        code: authorization_code,
      },
    })
      .then((res) => {
        console.log('성공');
        console.log(res);
      })
      .catch((err) => {
        console.log('실패');
        console.log(err);
      });
  }
});

const client_id = import.meta.env.VITE_KAKAO_OAUTH_API_KEY;
const redirect_uri = 'http://localhost:3000';
const kakaoLoginUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${client_id}&redirect_uri=${redirect_uri}`;

const onClickKakaoLoginButton = () => {
  window.location.assign(kakaoLoginUrl);
};
</script>

<style scoped>
.logoContainer {
  margin-bottom: 40px;
  .logo {
    color: #fff;
    font-size: 4rem;
  }
}

.loginButtons {
  width: 100%;
  border: none;
  margin: none;
  background: none;
  cursor: pointer;
  :hover {
    opacity: 0.5;
  }

  .kakaoImage {
    width: 100%;
  }
}
</style>
