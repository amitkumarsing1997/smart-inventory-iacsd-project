export const APIUrl = {
    auth: {
        login: '/auth/login',
        refreshTokem: '/auth/refresh'
    },
    register: {
        shopkeeper: '/user/reg',
    },
    category: {
        getAllByPage: '/productcategory/getAll/',
        getAll: '/productcategory/getAll',
        save: '/productcategory/saveOrUpdate',
    },
    product: {
        getAllByPage: '/product/get/all/by/criteria',
        save: '/product/saveOrUpdate',
        getProduct: '/product/get/a/',
        getByCode: '/product/get/by/code/',
        lowStock: '/product/lowstock',
        printBarcode: '/barcode/generate/'
    },
    checkIn: {
        checkIn: '/checkin',
        getAll: '/checkin/get/all/by/product/'
    },

    bill: {
        generateBill: '/bill/generate',
        getBills: '/bill/get/bills/',
        billDtls: '/bill/get/bill/dtls/',
        download: '/bill/download/'
    },

    checkInOut: {
        getCheckIn: '/checkin/get/all/by/page',
        getCheckOut: '/checkOut/get/all/by/page'
    },

    profile: {
        user: "/user"
    }
}