import { APIUrl } from "src/config/APIUrlConfig";
import { AxiosInstance } from "src/config/AxiosConfig";
import { CheckIn, ProductCheckIn, Response } from "src/model";

export const CheckInService = {
    async checkInProduct(checkInData: CheckIn): Promise<Response<string>> {
        let response = await AxiosInstance.post<Response<string>>(APIUrl.checkIn.checkIn, checkInData);
        return response.data
    },

    async getCheckInByProduct(code: string): Promise<Response<ProductCheckIn>> {
        let response = await AxiosInstance.get<Response<ProductCheckIn>>(`${APIUrl.checkIn.getAll}${code}`);
        return response.data;
    } 
}