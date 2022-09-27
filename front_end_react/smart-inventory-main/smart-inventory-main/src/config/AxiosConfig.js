import axios from "axios";
import { Constant } from "src/constants/Constants";

export const AxiosInstance = axios.create({
    baseURL: 'http://localhost:8090',
});

AxiosInstance.interceptors.request.use(async (config) => {
    let token = localStorage.getItem(Constant.ACCESS_TOKEN);
    if (token) {
        config.headers = {'Authorization' : "Bearer "+token};
    }
    // Do something before request is sent
    return config;
}, (error) => {
    // Do something with request error
    return Promise.reject(error);
});

// Add a response interceptor
AxiosInstance.interceptors.response.use(async (response) => {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    console.log('server response ::', response)
    return response;
}, (error) => {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
});
