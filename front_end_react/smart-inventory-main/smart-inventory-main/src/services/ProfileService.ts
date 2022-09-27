import { APIUrl } from "src/config/APIUrlConfig";
import { AxiosInstance } from "src/config/AxiosConfig";
import { Response, UserInfo } from "src/model";

export const ProfileService = {
    async getUserProfile(): Promise<Response<UserInfo>> {
        let response = await AxiosInstance.get(APIUrl.profile.user);
        return response.data;
    }
}