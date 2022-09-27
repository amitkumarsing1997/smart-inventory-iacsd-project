import { Box, Button, Divider, Flex, FormControl, FormErrorMessage, FormLabel, Heading, HStack, Input, InputGroup, InputLeftAddon, InputRightAddon, Text, useToast } from '@chakra-ui/react';
import { Field, Form, Formik, FormikHandlers, FormikProps, FormikState } from 'formik';
import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { ProductSearch } from 'src/components/ProductSearch';
import { CheckIn, Product, Response } from 'src/model';
import { CheckInService } from 'src/services/CheckInService';
import { ProductService } from 'src/services/ProductService';
import * as Yup from 'yup';

export const CheckInScreen = () => {

    let [params] = useSearchParams();

    const today = new Date();
    const toast = useToast();
    const [checkIn, setCheckIn] = useState({quantity: 1, unit: '', rate: 1, rateUnit: '', checkInOn: '', expireOn: '', remindBefore: 1, repeatReminder: 1} as CheckIn);
    const [product, setProduct] = useState({} as Product);
    const [code, setCode] = useState('');

    useEffect(() => {
        if (params.get('code')) {
            setCode(params.get('code') || '');
        }
        const getProduct = async (code: string) => {
            const response: Response<Product> = await ProductService.getProductByCode(code);
            if (response.success) {
                setProduct(response.body);
                checkIn.productUid = response.body.uid || '';
                checkIn.rateUnit = response.body.unit;
                checkIn.unit = response.body.unit;
                setCheckIn(checkIn);
            }
        }
        console.log('Product code :: ', code);
        if (code) {
            getProduct(code);
        }
    }, [code]);

    const recvCode = (code: string) => {
        setCode(code);
    }

    const onCheckIn = async (checkIn: CheckIn, { resetForm, setSubmitting }: { resetForm: (nextState?: Partial<FormikState<CheckIn>>) => void, setSubmitting: (isSubmitting: boolean) => void; }) => {
        const cur_date = new Date();
        const checkInOn = new Date(checkIn.checkInOn); //to get the current date and time so that UTC time will not be zero
        checkInOn.setHours(cur_date.getHours());
        checkInOn.setMinutes(cur_date.getMinutes());
        checkIn.checkInOn = checkInOn.toISOString();
        if (checkIn.expireOn) {
            const expireOn = new Date(checkIn.expireOn);
            expireOn.setHours(cur_date.getHours());
            expireOn.setMinutes(cur_date.getMinutes());
            checkIn.expireOn = expireOn.toISOString();
        }
        // console.log('CheckIn', checkIn)

        const response: Response<string> = await CheckInService.checkInProduct(checkIn);
        if (response.success) {
            toast({
                title: 'Product has been checked-in',
                status: 'success',
                isClosable: true,
            });
            resetForm();
        } else {
            toast({
                title: 'Error',
                description: response.message,
                status: 'error',
                isClosable: true,
            });
        }
        setSubmitting(false);
    }


    return (
        <Flex direction='column'>
            <Heading fontSize={'xl'}>Check In</Heading>
            <Box my={2}>
                <ProductSearch code={code} getCode={recvCode} />
            </Box>
            {product.uid && <>
                <Heading my={2} fontSize={'md'}>Product Details</Heading>
                <Divider />
                <Flex direction={{ md: 'row', base: 'column' }} py={2}>
                    <HStack w={{ base: 'full', md: 4 / 12 }}>
                        <Text fontSize='sm'>Name: </Text>
                        <Heading fontSize={'sm'}>{product?.name}</Heading>
                    </HStack>
                    <HStack w={{ base: 'full', md: 4 / 12 }}>
                        <Text fontSize='sm'>Code: </Text>
                        <Heading fontSize={'sm'}>{product?.code}</Heading>
                    </HStack>
                    <HStack w={{ base: 'full', md: 4 / 12 }}>
                        <Text fontSize='sm'>Available Qty({product?.unit}): </Text>
                        <Heading fontSize={'sm'}>{product?.avlQty}</Heading>
                    </HStack>
                </Flex>
                <Divider />
                <Heading my={2} fontSize={'md'}>Check In Details</Heading>
                <Formik
                    initialValues={checkIn}
                    onSubmit={onCheckIn}
                    validationSchema={Yup.object({
                        quantity: Yup.number().required('Required').moreThan(0, 'Quantity must be greater than zero.'),
                        rate: Yup.number().required('Required').moreThan(0, 'Rate must be greater than zero.'),
                        checkInOn: Yup.date().required('Required').max(today, 'Must not be greater than today.'),
                        expireOn: Yup.date().min(today, 'Must be greater than today'),
                        remindBefore: Yup.number().moreThan(0, 'Must be greater than zero.'),
                        repeatReminder: Yup.number().moreThan(0, 'Must be greater than zero.')
                    })}
                >
                    {(props: FormikProps<CheckIn>) =>
                        <Form>
                            <Flex direction={{ md: 'row', base: 'column' }} gap='2'>
                                <Box>
                                    <Field name='quantity'>
                                        {({ field, form }: { field: FormikHandlers, form: FormikState<CheckIn> }) => (
                                            <FormControl my={2} isInvalid={!!(form.touched.quantity && form.errors.quantity)}>
                                                <FormLabel htmlFor='quantity'>Quantity</FormLabel>
                                                <InputGroup>
                                                    <Input type='number' id='quantity' placeholder='Quantity' {...field} />
                                                    <InputRightAddon children={checkIn.unit} />
                                                </InputGroup>
                                                <FormErrorMessage>{form.errors.quantity}</FormErrorMessage>
                                            </FormControl>
                                        )}
                                    </Field>
                                </Box>
                                <Box>
                                    <Field name='rate'>
                                        {({ field, form }: { field: FormikHandlers, form: FormikState<CheckIn> }) => (
                                            <FormControl my={2} isInvalid={!!(form.touched.rate && form.errors.rate)}>
                                                <FormLabel htmlFor='rate'>Rate</FormLabel>
                                                <InputGroup>
                                                    <InputLeftAddon children='&#8377;' />
                                                    <Input type='number' id='rate' placeholder='Rate' {...field} />
                                                    <InputRightAddon children={'/' + checkIn.rateUnit} />
                                                </InputGroup>
                                                <FormErrorMessage>{form.errors.rate}</FormErrorMessage>
                                            </FormControl>
                                        )}
                                    </Field>
                                </Box>
                                <Box>
                                    <Field name='checkInOn'>
                                        {({ field, form }: { field: FormikHandlers, form: FormikState<CheckIn> }) => (
                                            <FormControl my={2} isInvalid={!!(form.touched.checkInOn && form.errors.checkInOn)}>
                                                <FormLabel htmlFor='checkIn'>Check In On</FormLabel>
                                                <Input type="date" id='checkIn' placeholder='Check in on' {...field} />
                                                <FormErrorMessage>{form.errors.checkInOn}</FormErrorMessage>
                                            </FormControl>
                                        )}
                                    </Field>
                                </Box>
                                <Box>
                                    <Field name='expireOn'>
                                        {({ field, form }: { field: FormikHandlers, form: FormikState<CheckIn> }) => (
                                            <FormControl my={2} isInvalid={!!(form.touched.expireOn && form.errors.expireOn)}>
                                                <FormLabel htmlFor='expireOn'>Expire On</FormLabel>
                                                <Input type="date" id='expireOn' placeholder='Expire On' {...field} />
                                                <FormErrorMessage>{form.errors.expireOn}</FormErrorMessage>
                                            </FormControl>
                                        )}
                                    </Field>
                                </Box>
                            </Flex>
                            <Flex direction={{ md: 'row', base: 'column' }} gap='2'>
                                <Box>
                                    <Field name='remindBefore'>
                                        {({ field, form }: { field: FormikHandlers, form: FormikState<CheckIn> }) => (
                                            <FormControl my={2} isInvalid={!!(form.touched.remindBefore && form.errors.remindBefore)}>
                                                <FormLabel htmlFor='remindBefore'>Remind Before</FormLabel>
                                                <InputGroup>
                                                    <Input type='number' id='remindBefore' placeholder='Remind before' {...field} />
                                                    <InputRightAddon children="Day(s)" />
                                                </InputGroup>
                                                <FormErrorMessage>{form.errors.remindBefore}</FormErrorMessage>
                                            </FormControl>
                                        )}
                                    </Field>
                                </Box>
                                <Box>
                                    <Field name='repeatReminder'>
                                        {({ field, form }: { field: FormikHandlers, form: FormikState<CheckIn> }) => (
                                            <FormControl my={2} isInvalid={!!(form.touched.repeatReminder && form.errors.repeatReminder)}>
                                                <FormLabel htmlFor='repeatReminder'>Repeat Reminder</FormLabel>
                                                <InputGroup>
                                                    <Input type='number' id='repeatReminder' placeholder='Repeat reminder' {...field} />
                                                    <InputRightAddon children="In a Day" />
                                                </InputGroup>
                                                <FormErrorMessage>{form.errors.repeatReminder}</FormErrorMessage>
                                            </FormControl>
                                        )}
                                    </Field>
                                </Box>
                            </Flex>
                            <Flex my='2' justifyContent={'end'}>
                                <Button colorScheme='teal' size='sm' type='submit' isLoading={props.isSubmitting}>
                                    Save
                                </Button>
                            </Flex>
                        </Form>
                    }
                </Formik>
            </>
            }
        </Flex >
    )
}