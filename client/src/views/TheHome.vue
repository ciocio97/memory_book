<template>
  <div class="logoContainer">
    <img
      class="mainLogo"
      src="../assets/main_logo_text.svg"
      alt="please_leave_me_a_message"
    />
  </div>
  <button class="loginButtons" @click.once="onClickKakaoLoginButton">
    <img
      class="kakaoImage"
      src="../assets/kakao_login_image.png"
      alt="kakao_login_btn"
    />
  </button>
</template>

<script setup>
const onClickKakaoLoginButton = () => {
  console.log('kakao login button click');
};
</script>

<style>
.logoContainer {
  margin-bottom: 40px;
  .mainLogo {
    width: 15rem;
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
