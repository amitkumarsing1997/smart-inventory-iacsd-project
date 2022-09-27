import { APIUrl } from "src/config/APIUrlConfig";
import { AxiosInstance } from "src/config/AxiosConfig";
import { Bill, BillDtls, BillListing, Response } from "src/model";

export const BillService = {

    async gnerateBill(bill: Bill): Promise<Response<string>> {
        let response = await AxiosInstance.post<Response<string>>(APIUrl.bill.generateBill, bill);
        return response.data;
    },

    async getBills(mobileNumber: string): Promise<Response<Array<BillListing>>> {
        let response = await AxiosInstance.get<Response<Array<BillListing>>>(`${APIUrl.bill.getBills}${mobileNumber}`);
        return response.data;
    },

    async getBill(uid: string): Promise<Response<BillDtls>> {
        let response = await AxiosInstance.get<Response<BillDtls>>(`${APIUrl.bill.billDtls}${uid}`);
        return response.data
    },

    async downloadBill(uid: string): Promise<void> {
        let response = await AxiosInstance.get(`${APIUrl.bill.download}${uid}`, { responseType: 'blob' });

        // create file link in browser's memory
        const href = URL.createObjectURL(response.data);

        // create "a" HTLM element with href to file & click
        const link = document.createElement('a');
        link.href = href;
        link.setAttribute('download', uid+'.pdf'); //or any other extension
        document.body.appendChild(link);
        link.click();

        // clean up "a" element & remove ObjectURL
        document.body.removeChild(link);
        URL.revokeObjectURL(href);
    }
}