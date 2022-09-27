import { Box, Button, Flex, Heading, IconButton, Tooltip, useColorModeValue, useDisclosure, VStack, Wrap, WrapItem } from '@chakra-ui/react';
import React, { useState } from 'react';
import { useEffect } from 'react';
import { MdEdit, MdInfo, MdOutlineAdd } from 'react-icons/md';
import { MemoizedPaginator, usePaginator } from 'src/components/Paginator';
import { PaginationResponse, ProductCategory } from 'src/model';
import { ProductCategoryService } from 'src/services/ProductCategoryService';
import { SaveCategory } from './SaveCategory';


export const ListProductCategory = () => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const [page, size, totalPages, setPage, setSize, setTotal] = usePaginator(10);
    const [categories, setCategories] = useState<Array<ProductCategory>>([]);
    const cardBgColor = useColorModeValue('white', 'gray.800');
    const [fetchData, setFetchData] = useState<boolean>(false);
    const [category, setCategory] = useState<ProductCategory>({ name: '' } as ProductCategory);

    useEffect(() => {
        const getCategories = async () => {
            let response: PaginationResponse<ProductCategory> = await ProductCategoryService.getAllCategory(page, size);
            if (response.success) {
                setCategories(response.body);
                setTotal(response.totalElements);
            }
        }
        getCategories();
    }, [page, size, fetchData]);

    const onAddCategory = () => {
        setCategory({ name: '' } as ProductCategory);
        onOpen();
    }

    const onEditCategory = (category: ProductCategory) => {
        setCategory(category);
        onOpen();
    }

    const onModalClose = (reload: boolean) => {
        if (reload) {
            setFetchData(!fetchData);
        }
        onClose();
    }

    return (
        <Flex direction={'column'} w={'full'}>
            <Flex w='full' justifyContent='space-between' alignItems='center'>
                <Heading fontSize={'xl'}>Manage Category</Heading>
                <Button leftIcon={<MdOutlineAdd />} colorScheme='teal' variant='solid' onClick={onAddCategory}>
                    Add Category
                </Button>
            </Flex>
            <SaveCategory isOpen={isOpen} onClose={onModalClose} category={category} />
            <Wrap mt={2}>
                {categories.map(category =>
                    <WrapItem key={category.uid}>
                        <Flex
                            bgColor={'gray.100'}
                            w={250}
                            alignItems='center'
                            justifyContent={'space-between'}
                            role={'group'}
                            px={4}
                            py={1}
                            boxShadow={'sm'}
                            rounded={'lg'}
                            pos={'relative'}
                            zIndex={1}>
                            <Heading fontSize={'2xl'} fontFamily={'body'} fontWeight={500}>
                                {category.name}
                            </Heading>
                            <VStack direction={'column'} align={'center'} ml={5}>
                                <Tooltip label='Edit'>
                                    <IconButton
                                        variant='ghost'
                                        colorScheme='teal'
                                        aria-label='Edit'
                                        icon={<MdEdit />}
                                        onClick={() => onEditCategory(category)}
                                    />
                                </Tooltip>
                                <Tooltip label='More Info'>
                                    <IconButton
                                        variant='ghost'
                                        colorScheme='teal'
                                        aria-label='More Info'
                                        icon={<MdInfo />}
                                    />
                                </Tooltip>
                            </VStack>
                        </Flex>
                    </WrapItem>
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