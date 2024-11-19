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
const onClickKakaoLoginButton = () => {
  console.log('kakao login button click');
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
