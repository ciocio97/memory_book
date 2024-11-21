const debounce = (func, timeout = 300) => {
  let timerID;
  return (...args) => {
    clearTimeout(timerID);
    timerID = setTimeout(() => {
      // func(...args);
      func.apply(this, args);
    }, timeout);
  };
};

export { debounce };
