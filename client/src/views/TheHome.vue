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
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { isLogin } from '@/utils/user';

import { AXIOS, get, put } from '@/api';

const client_id = import.meta.env.VITE_KAKAO_OAUTH_API_KEY;
const javascript_key = import.meta.env.VITE_KAKAO_JAVASCRIPT_KEY;

const router = useRouter();

const initPage = () => {
  sessionStorage.clear();

  let splittedUrl = window.location.href.split('?receiver=');
  let receiver_token = null;
  let sender_token = null;

  if (isLogin()) {
    const access_token = localStorage.getItem('access_token');

    AXIOS.defaults.headers.common['Authorization'] = `Bearer ${access_token}`;

    console.log('access_token 있음');

    if (splittedUrl.length === 2) {
      console.log('편지 써줘 url');
      receiver_token = splittedUrl[1];
      sessionStorage.setItem('receiver_token', receiver_token);
      router.push('/write');
      return;
    }

    splittedUrl = window.location.href.split('?sender=');

    if (splittedUrl.length === 2) {
      console.log('옛다 편지 url');
      sender_token = splittedUrl[1];
      // writer 지정 !!
      // sessionStorage.setItem('sender_token', sender_token);

      put('/memo', {
        linked_token: sender_token,
      })
        .then((res) => {
          console.log('writer 지정');
          console.log(res);
        })
        .catch((err) => {
          console.log('writer 지정 에러');
          console.log(err);
        });

      router.push('/read');
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
    sender_token = splittedUrl[1];
    // writer 지정 !!
    // sessionStorage.setItem('sender_token', sender_token);

    put('/memo', {
      linked_token: sender_token,
    })
      .then((res) => {
        console.log('writer 지정');
        console.log(res);
      })
      .catch((err) => {
        console.log('writer 지정 에러');
        console.log(err);
      });
  }

  splittedUrl = window.location.href.split('?code=');

  if (splittedUrl.length === 2) {
    console.log('로그인 시도');
    const authorization_code = splittedUrl[1];

    get('/oauth/kakao/callback', {
      code: authorization_code,
    })
      .then((res) => {
        console.log('success login');
        const access_token = res.data.accessToken;
        const linked_token = res.data.linkedToken;

        localStorage.setItem('access_token', access_token);
        localStorage.setItem('linked_token', linked_token);

        AXIOS.defaults.headers.common['Authorization'] =
          `Bearer ${access_token}`;

        router.replace('/');

        if (sender_token === null) {
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

const redirect_uri = 'http://localhost:3000';
const kakaoLoginUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${client_id}&redirect_uri=${redirect_uri}`;

const onClickKakaoLoginButton = () => {
  window.location.assign(kakaoLoginUrl);
};

Kakao.init(javascript_key);
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
