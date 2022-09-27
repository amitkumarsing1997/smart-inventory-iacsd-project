import { Button, Divider, Flex, FormControl, FormErrorMessage, FormLabel, Heading, HStack, Input, InputGroup, InputLeftAddon, Select, Text, useToast, VStack } from '@chakra-ui/react';
import { Field, FieldArray, Form, Formik, FormikHandlers, FormikProps, FormikState } from 'formik';
import React, { useState } from 'react';
import { MdOutlineAdd } from 'react-icons/md';
import { Bill, CheckOut } from 'src/model';
import { BillService } from 'src/services/BillService';
import { AProduct } from './AProduct';

export const GenerateBill = () => {

    const toast = useToast();
    const [grandTotal, setGrandTotal] = useState<number>(0);
    const [totals, setTotals] = useState<Array<number>>([0]);

    const updateTotal = (totals: Array<number>) => {
        setTotals(totals);
        calcGrandTotal();
    }

    const calcGrandTotal = () => {
        setGrandTotal(totals.reduce((p, c) => p + c, 0));
    }

    const initialBill = {} as Bill;
    initialBill.customerName = '';
    initialBill.mobileNumber = '';
    initialBill.paidAmt = 0;
    initialBill.totalAmt = 0;
    initialBill.paymentMode = 'NONE';
    initialBill.paymentStatus = 'CREDIT';
    initialBill.products = [{ checkInUid: '', quantity: 0, rate: 0 } as CheckOut]


    const onGenerateBill = async (bill: Bill, { resetForm, setSubmitting }: { resetForm: (nextState?: Partial<FormikState<Bill>>) => void, setSubmitting: (isSubmitting: boolean) => void; }) => {
        bill.totalAmt = grandTotal;
        let resp = await BillService.gnerateBill(bill);
        if (resp.success) {
            toast({
                title: 'Bill Generated',
                status: 'success',
                isClosable: true,
            });
            setGrandTotal(0);
            resetForm();
        }

        setSubmitting(false);
    }



    return (
        <Flex direction='column'>
            <Heading fontSize={'xl'}>Billing</Heading>

            <VStack alignItems={'start'} mt={2}>
                <Heading fontSize={'md'}>Items</Heading>
                <HStack w={'full'}>
                    <Text w={3 / 14} align={'center'}>Product Code</Text>
                    <Text w={3 / 14} align={'center'}>Check-In</Text>
                    <Text w={3 / 14} align={'center'}>Rate</Text>
                    <Text w={3 / 14} align={'center'}>Qty</Text>
                    <Text w={1 / 12} align={'right'}>Total</Text>
                </HStack>
                <Formik
                    initialValues={initialBill}
                    onSubmit={onGenerateBill}
                >
                    {(props: FormikProps<Bill>) =>
                        <Form>
                            <FieldArray name='products'
                                render={arrayHelpers => (
                                    <><VStack>
                                        {props.values.products.map((product, idx) =>
                                            <AProduct key={idx} idx={idx} arrayHelpers={arrayHelpers}
                                                checkOut={product} setFieldValue={props.setFieldValue}
                                                totals={totals}
                                                updateTotal={updateTotal}
                                            />
                                        )}
                                    </VStack>
                                        <Flex justifyContent={'flex-end'} w={'full'} py={5}>
                                            <Button size={'sm'} leftIcon={<MdOutlineAdd />} colorScheme='teal' variant='solid'
                                                onClick={() => {
                                                    arrayHelpers.push({ checkInUid: '', quantity: 0, rate: 0 } as CheckOut);
                                                    totals.push(0);
                                                }}>
                                                Add More
                                            </Button>
                                        </Flex>
                                    </>
                                )} />


                            <Divider></Divider>
                            <Flex w={'full'} justifyContent={'flex-end'}>
                                <Text fontWeight={'bold'}>Total</Text>
                                <Text mx={2}>:</Text>
                                <Text fontWeight={'bold'}>&#8377; {grandTotal}</Text>
                            </Flex>
                            <Divider></Divider>
                            <VStack alignItems={'start'}>
                                <Heading fontSize={'md'}>Customer Info</Heading>
                                <HStack>
                                    <Field name='customerName'>
                                        {({ field, form }: { field: FormikHandlers, form: FormikState<Bill> }) => (
                                            <FormControl isInvalid={!!(form.errors.customerName && form.touched.customerName)}>
                                                <FormLabel htmlFor='customerName'>Name</FormLabel>
                                                <Input id='customerName' placeholder="Customer name" {...field} />
                                                <FormErrorMessage>{form.errors.customerName}</FormErrorMessage>
                                            </FormControl>
                                        )}
                                    </Field>
                                    <Field name='mobileNumber'>
                                        {({ field, form }: { field: FormikHandlers, form: FormikState<Bill> }) => (
                                            <FormControl isInvalid={!!(form.errors.mobileNumber && form.touched.mobileNumber)}>
                                                <FormLabel htmlFor='mobileNumber'>Mobile Number</FormLabel>
                                                <InputGroup>
                                                    <InputLeftAddon children={'+91'} />
                                                    <Input id='mobileNumber' placeholder="Mobile number" {...field} />
                                                </InputGroup>
                                                <FormErrorMessage>{form.errors.mobileNumber}</FormErrorMessage>
                                            </FormControl>
                                        )}
                                    </Field>
                                </HStack>
                                <Heading fontSize={'md'}>Payment Info</Heading>
                                <HStack>
                                    <Field name='paidAmt'>
                                        {({ field, form }: { field: FormikHandlers, form: FormikState<Bill> }) => (
                                            <FormControl isInvalid={!!(form.errors.paidAmt && form.touched.paidAmt)}>
                                                <FormLabel htmlFor='paidAmt'>Amount Paid</FormLabel>
                                                <InputGroup>
                                                    <InputLeftAddon children='&#8377;' />
                                                    <Input type='number' id="paidAmt" placeholder="Amount Paid" {...field} />
                                                </InputGroup>
                                                <FormErrorMessage>{form.errors.paidAmt}</FormErrorMessage>
                                            </FormControl>
                                        )}
                                    </Field>
                                    <Field name='paymentMode'>
                                        {({ field, form }: { field: FormikHandlers, form: FormikState<Bill> }) => (
                                            <FormControl isInvalid={!!(form.errors.paymentMode && form.touched.paymentMode)}>
                                                <FormLabel htmlFor='paymentMode'>Payment Mode</FormLabel>
                                                <Select id='paymentMode' placeholder='Select Payment Mode' {...field}>
                                                    <option value={"NONE"}>None</option>
                                                    <option value={"ONLINE"}>Online</option>
                                                    <option value={"CASH"}>Cash</option>
                                                </Select>
                                            </FormControl>
                                        )}
                                    </Field>
                                    <Field name="paymentStatus">
                                        {({ field, form }: { field: FormikHandlers, form: FormikState<Bill> }) => (
                                            <FormControl isInvalid={!!(form.errors.paymentStatus && form.touched.paymentStatus)}>
                                                <FormLabel htmlFor='paymentStatus'>Payment Status</FormLabel>
                                                <Select id='paymentStatus' placeholder='Select Payment Status' {...field}>
                                                    <option value={'CREDIT'}>Credit</option>
                                                    <option value={'PARTIALLY_PAID'}>Partially Paid</option>
                                                    <option value={'PAID'}>Paid</option>
                                                </Select>
                                            </FormControl>
                                        )}
                                    </Field>
                                </HStack>
                            </VStack>
                            <HStack justifyContent={'flex-end'} w={'full'}>
                                <Button size={'md'} colorScheme='red' variant='solid' type='reset'>
                                    Reset
                                </Button>
                                <Button size={'md'} colorScheme='teal' variant='solid' type='submit' isLoading={props.isSubmitting}>
                                    Generate Bill
                                </Button>
                            </HStack>
                        </Form>
                    }
                </Formik>
            </VStack>
        </Flex>
    )
}