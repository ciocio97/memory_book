<template>
  <div class="wrapper-home">
    <div class="logoContainer">
      <p class="logo">나의 우주에</p>
      <p class="logo">너라는</p>
      <p class="logo">☆ 을</p>
      <p class="logo">남겨줘</p>
    </div>
    <button class="loginButtons" @click.once="onClickKakaoLoginButton">
      <img
        class="kakaoImage"
        src="../assets/images/kakao_login_image.png"
        alt="kakao_login_btn"
      />
    </button>
  </div>
</template>

<script setup>
import axios from 'axios';
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { isLogin } from '@/utils/user';

const router = useRouter();

const initPage = () => {
  let splittedUrl = window.location.href.split('?receiver=');

  if (isLogin()) {
    if (splittedUrl.length === 2) {
      console.log('편지 써줘 url');
      const linked_token = splittedUrl[1];
      sessionStorage.setItem('receiver_token', linked_token);
      router.push('/write');
      return;
    }

    if (splittedUrl.length !== 2) {
      router.push('/select');
      return;
    }
  }

  splittedUrl = window.location.href.split('?sender=');

  if (splittedUrl.length === 2) {
    console.log('옛다 편지 url');
    const linked_token = splittedUrl[1];
    sessionStorage.setItem('sender_token', linked_token);
  }

  splittedUrl = window.location.href.split('?code=');

  if (splittedUrl.length === 2) {
    console.log('로그인 시도');
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
        console.log('success login');
        localStorage.setItem('access_token', res.data);
        router.go(-1); // '?code=' parameter 제거 위한 방어 코드

        if (sessionStorage.getItem('sender_token') !== null) {
          router.push('/select');
          return;
        }

        router.push('/read');
      })
      .catch((err) => {
        console.log('error');
        console.log(err);
      });
  }
};

onMounted(() => {
  initPage();
});

const client_id = import.meta.env.VITE_KAKAO_OAUTH_API_KEY;
const redirect_uri = 'http://localhost:3000';
const kakaoLoginUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${client_id}&redirect_uri=${redirect_uri}`;

const onClickKakaoLoginButton = () => {
  window.location.assign(kakaoLoginUrl);
};
</script>

<style scoped>
.wrapper-home {
  display: flex;
  flex-flow: column nowrap;
  gap: 2rem;
  .logoContainer {
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
}
</style>
