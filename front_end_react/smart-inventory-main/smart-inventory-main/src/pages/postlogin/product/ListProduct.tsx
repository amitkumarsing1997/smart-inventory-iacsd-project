import { Button, Flex, Heading, useColorModeValue, Wrap } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { MdOutlineAdd } from 'react-icons/md';
import { Link } from 'react-router-dom';
import { MemoizedPaginator, usePaginator } from 'src/components/Paginator';
import { PaginationResponse, ProductCriteria, ProductListing } from 'src/model';
import { ProductService } from 'src/services/ProductService';
import { ProductCard } from './ProductCard';

export const ListProduct = () => {
    const [page, size, totalPages, setPage, setSize, setTotal] = usePaginator(10);
    const [products, setProducts] = useState<Array<ProductListing>>([]);
    const cardBGColor = useColorModeValue('white', 'gray.800')
    

    useEffect(() => {
        const getProducts = async () => {
            let response: PaginationResponse<ProductListing> = await ProductService.getAllProductByCriteria({} as ProductCriteria, page, size);
            if (response.success) {
                setProducts(response.body);
                setTotal(response.totalElements);
            }
        }
        getProducts();
    }, [page, size]);

    return (
        <Flex direction={'column'}>
            <Flex w='full' justifyContent='space-between' alignItems='center'>
                <Heading fontSize={'xl'}>Manage Product</Heading>
                <Button as={Link} to={'../product/save'} leftIcon={<MdOutlineAdd />} colorScheme='teal' variant='solid'>
                    Add Product
                </Button>
            </Flex>
            <Wrap mt={2}>
                {products.map(product =>
                    <ProductCard product={product}/>
                )}
            </Wrap>
            <MemoizedPaginator
                sizes={[5, 10, 20, 30, 40]}
                page={page}
                size={size}
                totalPages={totalPages}
                setPage={setPage}
                setSize={setSize}
                setTotal={setTotal}
            />
        </Flex>
    )
}