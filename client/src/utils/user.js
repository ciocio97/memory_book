const isLogin = () => {
  if (localStorage.getItem('access_token') === null) {
    return false;
  }

  return true;
};

export { isLogin };
