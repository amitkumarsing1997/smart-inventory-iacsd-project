import { ArrowBackIcon } from '@chakra-ui/icons';
import { Button, Flex, FormControl, FormErrorMessage, FormLabel, Heading, IconButton, Input, Select, useToast, VStack } from '@chakra-ui/react';
import { Field, Form, Formik, FormikHandlers, FormikProps, FormikState } from 'formik';
import React, { useEffect, useState } from 'react';
import { MdOutlineArrowBack } from 'react-icons/md';
import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import { Product, ProductCategory, Response } from 'src/model';
import { ProductCategoryService } from 'src/services/ProductCategoryService';
import { ProductService } from 'src/services/ProductService';
import * as Yup from 'yup';

export const SaveProduct = () => {
    let [params] = useSearchParams();

    const [categories, setCategories] = useState<Array<ProductCategory>>([]);

    const [product, setProduct] = useState({} as Product);

    const toast = useToast();
    const navigate = useNavigate();

    const getAllProductCategory = async () => {
        let response: Response<Array<ProductCategory>> = await ProductCategoryService.getAll();
        if (response.success) {
            setCategories(response.body);
        }
    }
    const getProduct = async (productUid: string) => {
        let response: Response<Product> = await ProductService.getProduct(productUid);
        if (response.success) {
            setProduct(response.body);
        }
    }

    useEffect(() => {

        if (params.get('uid')) {
            getAllProductCategory();
            getProduct(params.get('uid') || '');
        } else {
            product.name = '';
            product.code = '';
            product.unit = '';
            product.avlQty = 0;
            product.minimumQuantity = 0
            product.productCatUid = ''
            setProduct(product);
            getAllProductCategory();
        }
    }, [])




    const onProductSave = async (product: Product, { resetForm, setSubmitting }: { resetForm: (nextState?: Partial<FormikState<Product>>) => void, setSubmitting: (isSubmitting: boolean) => void; }) => {
        const response = await ProductService.saveProduct(product);
        if (response.success) {
            toast({
                title: 'Product Saved',
                status: 'success',
                isClosable: true,
            });
            resetForm();
            navigate('../product/list');
        } else {
            toast({
                title: 'Error',
                description: response.message,
                status: 'error',
                isClosable: true,
            });
        }
        setSubmitting(false)
    }



    return (
        <Flex direction={'column'}>
            <Flex w='full' alignItems='center'>
                <IconButton
                    as={Link} to={'../product/list'}
                    mr={2}
                    size={'md'}
                    variant='ghost'
                    colorScheme='teal'
                    aria-label='Send email'
                    icon={<MdOutlineArrowBack />}
                />
                <Heading fontSize={'xl'}>Add Product</Heading>
            </Flex>
            <VStack align={'flex-start'} mt={2}>
                <Formik
                    enableReinitialize={true}
                    initialValues={product}
                    onSubmit={onProductSave}
                    validationSchema={Yup.object({
                        name: Yup.string().required('Required'),
                        code: Yup.string().required('Required'),
                        unit: Yup.string().required('Required'),
                        productCatUid: Yup.string().required('Required'),
                        minimumQuantity: Yup.number().required('Required').positive('Invalid Input')
                    })}
                >
                    {(props: FormikProps<Product>) =>
                        <Form>
                            <Field name='name'>
                                {({ field, form }: { field: FormikHandlers, form: FormikState<Product> }) => (
                                    <FormControl my='2' isInvalid={!!(form.errors.name && form.touched.name)}>
                                        <FormLabel htmlFor='name'>Name</FormLabel>
                                        <Input id='name' placeholder="Product name" {...field} />
                                        <FormErrorMessage>{form.errors.name}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Field name='code'>
                                {({ field, form }: { field: FormikHandlers, form: FormikState<Product> }) => (
                                    <FormControl my='2' isInvalid={!!(form.errors.code && form.touched.code)}>
                                        <FormLabel htmlFor='code'>Code</FormLabel>
                                        <Input id='code' placeholder="Product code" {...field} />
                                        <FormErrorMessage>{form.errors.code}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Field name="productCatUid">
                                {({ field, form }: { field: FormikHandlers, form: FormikState<Product> }) => (
                                    <FormControl my={2} isInvalid={!!(form.touched.productCatUid && form.errors.productCatUid)}>
                                        <FormLabel htmlFor='productCatUid'>Product Category</FormLabel>
                                        <Select id='productCatUid' placeholder='Select product category' {...field}>
                                            {categories.map(category => <option key={category.uid} value={category.uid}>{category.name}</option>)}
                                        </Select>
                                        <FormErrorMessage>{form.errors.productCatUid}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Field name="unit">
                                {({ field, form }: { field: FormikHandlers, form: FormikState<Product> }) => (
                                    <FormControl my={2} isInvalid={!!(form.touched.unit && form.errors.unit)}>
                                        <FormLabel htmlFor='unit'>Unit</FormLabel>
                                        <Select id='unit' placeholder='Select unit' {...field}>
                                            <option key={'count'} value='COUNT'>Count</option>
                                            <option key={'gram'} value='GRAM'>Gram</option>
                                            <option key={'kg'} value='KILOGRAM'>Kilogram</option>
                                            <option key={'quintal'} value='QUINTAL'>Quintal</option>
                                        </Select>
                                        <FormErrorMessage>{form.errors.unit}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Field name='minimumQuantity'>
                                {({ field, form }: { field: FormikHandlers, form: FormikState<Product> }) => (
                                    <FormControl my='2' isInvalid={!!(form.errors.minimumQuantity && form.touched.minimumQuantity)}>
                                        <FormLabel htmlFor='minimumQuantity'>Minimum Qunatity</FormLabel>
                                        <Input id='minimumQuantity' placeholder="Minimum Qunatity" {...field} />
                                        <FormErrorMessage>{form.errors.minimumQuantity}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Flex my='2' alignItems='center'>
                                <Button size='md' leftIcon={<ArrowBackIcon/>} colorScheme='blue' variant='ghost' as={Link} to={'../product/list'}>
                                    Back
                                </Button>
                                <Button colorScheme='teal' size='sm' type='submit' isLoading={props.isSubmitting}>
                                    Save
                                </Button>
                            </Flex>
                        </Form>
                    }
                </Formik>
            </VStack>
        </Flex>
    )
}