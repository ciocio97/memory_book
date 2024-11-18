const debounce = (func, timeout = 300) => {
  let timerID;
  return (...args) => {
    clearTimeout(timerID);
    timerID = setTimeout(() => {
      func(...args);
    }, timeout);
  };
};

export { debounce };
