import { IconType } from "react-icons";
import { To } from "react-router-dom";

export interface LinkItemProps {
    name: string;
    icon: IconType;
    route: To;
    submenu?: Array<LinkItemProps>
}

export interface Cred {
    username: string;
    password: string;
}

export interface User {
    uid: string;
    email: string;
}

export interface AuthResponse {
    accessToken: string;
    refreshToken: string;
    moreInfo: Record<string, any>;
}

export interface Response<T> {
    success: boolean;
    message?: string;
    body: T;
}

export interface PaginationResponse<T> {
    success: boolean;
    pageNumber: number;
    pageSize: number;
    totalPages: number;
    numberOfElementa: number;
    totalElements: number;
    body: Array<T>;
}

export interface RegisterUser {
    name: string;
    email: string;
    password: string;
    shopName: string;
    address: string;
}

export interface ProductCategory {
    uid?: string;
    name: string;
    verified?: boolean;
}

export interface ProductCriteria {
    categoryUid?: string;
    productName?: string;
    productCode?: string;
}

export interface ProductListing {
    uid: string;
    name: string;
    code: string;
    unit: string;
    avlQty: number;
}

export interface Product {
    uid?: string;
    name: string;
    code: string;
    minimumQuantity: number;
    unit: string;
    avlQty: number;
    productCatUid: string;
    productCatName?: string;
}

export interface CheckIn {
    uid?: string;
    productUid: string;
    quantity: number;
    unit: string;
    rate: number;
    rateUnit: string;
    checkInOn: string;
    expireOn: string;
    remindBefore: number;
    repeatReminder: number;
    remainingQty?: number;
}

export interface CheckOut {
    checkInUid: string;
    quantity: number;
    rate: number;
}

export interface Bill {
    customerName: string;
    mobileNumber: string;
    totalAmt: number;
    paidAmt: number;
    paymentMode: "CASH" | "ONLINE" | "NONE";
    paymentStatus: "PAID" | "PARTIALLY_PAID" | "CREDIT";
    products: Array<CheckOut>
}

export interface BillListing {
    uid: string;
    customerName: string;
    mobileNumber: string;
    totalAmt: number;
    paymentStatus: string;
    createdOn: string;
}

export interface ProductInfo {
    name: string;
    code: string;
    quantity: number;
    unit: string;
    rate: number;
    rateUnit: string;
    total: number;
}

export interface BillDtls {
    uid: string;
    customerName: string;
    mobileNumber: string;
    totalAmt: number;
    paidAmt: string;
    paymentMode: string;
    paymentStatus: string;
    createdOn: string;
    products: Array<ProductInfo>;
}

export interface CheckInOut {
    productName: string;
    productCode: string;
    qty: number;
    unit: string;
    rate: number;
    rateUnit: string;
    entryOn: string;
}

export interface CheckInOutCriteria {
    productCode: string;
}

export interface ProductCheckIn {
    product: Product;
    checkIns: Array<CheckIn>;
}

export interface UserInfo {
    name: string;
    email: string;
    shopName: string;
    shopAddress: string;
}
