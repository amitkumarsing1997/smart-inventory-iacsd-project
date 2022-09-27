import { APIUrl } from "src/config/APIUrlConfig";
import { AxiosInstance } from "src/config/AxiosConfig";
import { PaginationResponse, ProductCategory, Response } from "src/model";

export const ProductCategoryService = {

    async getAllCategory(page: number, size: number): Promise<PaginationResponse<ProductCategory>> {
        let response = await AxiosInstance.get<PaginationResponse<ProductCategory>>(`${APIUrl.category.getAllByPage}${page}/${size}`);
        return response.data;
    },

    async getAll(): Promise<Response<Array<ProductCategory>>> {
        let response = await AxiosInstance.get<Response<Array<ProductCategory>>>(APIUrl.category.getAll);
        return response.data;
    },

    async SaveCategory(category: ProductCategory): Promise<Response<boolean>> {
        let response = await AxiosInstance.post<Response<boolean>>(APIUrl.category.save, category);
        return response.data;
    }
}