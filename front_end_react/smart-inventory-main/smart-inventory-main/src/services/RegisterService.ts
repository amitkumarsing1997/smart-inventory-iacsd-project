import { APIUrl } from "src/config/APIUrlConfig";
import { AxiosInstance } from "src/config/AxiosConfig";
import { RegisterUser, Response } from "src/model";

export const RegisterService = {
    
    async registerUser(payload: RegisterUser): Promise<Response<boolean>> {
        let response = await AxiosInstance.post<Response<boolean>>(APIUrl.register.shopkeeper, payload);
        return response.data;
    }
}