import { APIUrl } from "src/config/APIUrlConfig";
import { AxiosInstance } from "src/config/AxiosConfig";
import { PaginationResponse, Product, ProductCriteria, ProductListing, Response } from "src/model";

export const ProductService = {
    async getAllProductByCriteria(criteria: ProductCriteria, page: number, size: number): Promise<PaginationResponse<ProductListing>> {
        let response = await AxiosInstance.post<PaginationResponse<ProductListing>>(APIUrl.product.getAllByPage, criteria, {params: {page: page, size: size}});
        return response.data;
    },

    async saveProduct(product: Product): Promise<Response<boolean>> {
        let response = await AxiosInstance.post<Response<boolean>>(APIUrl.product.save, product);
        return response.data;
    },

    async getProduct(uid: string): Promise<Response<Product>> {
        let response = await AxiosInstance.get<Response<Product>>(`${APIUrl.product.getProduct}${uid}`);
        return response.data;
    },

    async getProductByCode(code: string): Promise<Response<Product>> {
        let response = await AxiosInstance.get<Response<Product>>(`${APIUrl.product.getByCode}${code}`);
        return response.data
    },

    async getLowStockProducts(): Promise<Response<Array<Product>>> {
        let response = await AxiosInstance.get<Response<Array<Product>>>(`${APIUrl.product.lowStock}`);
        return response.data;
    },

    async downloadBarcode(code: string, qty: number): Promise<void> {
        let response = await AxiosInstance.get(`${APIUrl.product.printBarcode}${code}/${qty}`, { responseType: 'blob' });

        // create file link in browser's memory
        const href = URL.createObjectURL(response.data);

        // create "a" HTLM element with href to file & click
        const link = document.createElement('a');
        link.href = href;
        link.setAttribute('download', code+'_barcode.pdf'); //or any other extension
        document.body.appendChild(link);
        link.click();

        // clean up "a" element & remove ObjectURL
        document.body.removeChild(link);
        URL.revokeObjectURL(href);
    }
}