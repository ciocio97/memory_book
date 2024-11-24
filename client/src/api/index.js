import axios from 'axios';

const AXIOS = axios.create({
  baseURL: 'http://localhost:8080',
});

const get = (url, params) => {
  return AXIOS({
    method: 'get',
    url,
    params,
  });
};

const post = (url, data) => {
  return AXIOS({
    method: 'post',
    url,
    data,
  });
};

const put = (url, data) => {
  return AXIOS({
    method: 'put',
    url,
    data,
  });
};

export { AXIOS, get, post, put };
