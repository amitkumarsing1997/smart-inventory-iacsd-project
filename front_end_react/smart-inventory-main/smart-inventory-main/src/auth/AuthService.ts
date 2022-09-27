import { APIUrl } from "../config/APIUrlConfig"
import { AxiosInstance } from "../config/AxiosConfig"
import { Cred, AuthResponse, Response } from "../model"

export const AuthService = {

    async signIn(cred: Cred): Promise<Response<AuthResponse>> {
        let response = await AxiosInstance.post<Response<AuthResponse>>(APIUrl.auth.login, cred)
        return response.data;
    },

    async refreshTokenSignIn(refreshToken: string): Promise<Response<AuthResponse>>  {
        let response = await AxiosInstance.post<Response<AuthResponse>>(APIUrl.auth.login, {refreshToken: refreshToken})
        return response.data;
    }
}