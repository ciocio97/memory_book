const hasReader = () => {
  const receiver_token = sessionStorage.getItem('receiver_token');

  if (receiver_token === null) {
    return false;
  }

  return true;
};

const hasWriter = () => {
  const sender_token = sessionStorage.getItem('sender_token');

  if (sender_token === null) {
    return false;
  }

  return true;
};

export { hasReader, hasWriter };
