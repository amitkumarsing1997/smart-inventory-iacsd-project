import { FiHome, FiPackage, FiSettings } from "react-icons/fi";
import { MdCompareArrows, MdInput, MdOutlineCategory, MdOutlineReceipt, MdOutlineReceiptLong, MdReceipt } from "react-icons/md";
import { LinkItemProps } from "src/model";

export const LinkItems: Array<LinkItemProps> = [
    {
        name: 'Home',
        icon: FiHome,
        route: '/postlogin'
    },
    {
        name: 'Category',
        icon: MdOutlineCategory,
        route: '/postlogin/productcategory'
    },
    {
        name: 'Product',
        icon: FiPackage,
        route: '/postlogin/product/list'
    },
    {
        name: 'Check-In',
        icon: MdInput,
        route: '/postlogin/check-in'
    },
    {

        name: 'Bill',
        icon: MdOutlineReceipt,
        route: '/postlogin/bill', 
        submenu: [
            { name: 'Generate Bill', icon: MdReceipt, route: '/postlogin/bill/generate' },
            { name: 'Manage Bill', icon: MdOutlineReceiptLong, route: '/postlogin/bill/list' },
        ]
    },
    {
        name: 'Check-I/O History',
        icon: MdCompareArrows,
        route: '/postlogin/checkiohistory'
    }
];