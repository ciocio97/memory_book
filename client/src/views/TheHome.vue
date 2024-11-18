<template>
  <div>나에게 메모를 남겨줘</div>
  <button class="loginButtons" @click.once="onClickKakaoLoginButton">
    <img class="kakaoImage" src="../assets/kakao_login_image.png" />
  </button>
</template>

<script setup>
const onClickKakaoLoginButton = () => {
  console.log('kakao login button click');
};
</script>

<style>
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
