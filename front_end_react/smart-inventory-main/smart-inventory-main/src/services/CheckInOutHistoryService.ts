import { APIUrl } from "src/config/APIUrlConfig";
import { AxiosInstance } from "src/config/AxiosConfig";
import { CheckInOut, CheckInOutCriteria, PaginationResponse } from "src/model";

export const CheckInOutHistoryService = {
    async getCheckInHistory(payload: CheckInOutCriteria, page: number, size: number): Promise<PaginationResponse<CheckInOut>> {
        let response = await AxiosInstance.post<PaginationResponse<CheckInOut>>(APIUrl.checkInOut.getCheckIn, payload, {params: {page: page, size: size}});
        return response.data;
    },

    async getCheckOutHistory(payload: CheckInOutCriteria, page: number, size: number) {
        let response = await AxiosInstance.post<PaginationResponse<CheckInOut>>(APIUrl.checkInOut.getCheckOut, payload, {params: {page: page, size: size}});
        return response.data;
    }
}