import { Box, Heading, HStack, VStack, Wrap, WrapItem } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { Product, Response } from 'src/model';
import { ProductService } from 'src/services/ProductService';
import { LowStockCard } from './LowStockCard';

export const LowStockPage = () => {

    const [products, setProducts] = useState([] as Array<Product>);

    useEffect(() => {
        const getLowStockProduct = async () => {
            let response: Response<Array<Product>> = await ProductService.getLowStockProducts();
            if (response.success) {
                setProducts(response.body);
            }
        }
        getLowStockProduct();
    }, []);

    return (
        <VStack alignItems={'start'}>
             <Heading fontSize={'xl'}>Low Stock</Heading>
            <Wrap>
                {products.map(product => <WrapItem key={product.uid}>
                    <LowStockCard product={product} />
                </WrapItem>)}
            </Wrap>
        </VStack>
    )
}