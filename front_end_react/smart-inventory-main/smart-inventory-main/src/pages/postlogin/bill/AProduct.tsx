import { Container, FormControl, HStack, IconButton, Input, InputGroup, InputLeftAddon, InputRightAddon, Select, Text, VStack } from '@chakra-ui/react';
import { ArrayHelpers, Field, FormikHandlers, FormikHelpers, FormikState } from 'formik';
import React, { useEffect, useState } from 'react';
import { MdOutlineCancel } from 'react-icons/md';
import { ProductSearch } from 'src/components/ProductSearch';
import { Bill, CheckIn, CheckOut, ProductCheckIn } from 'src/model';
import { CheckInService } from 'src/services/CheckInService';

export interface AProductProps {
    idx: number;
    arrayHelpers: ArrayHelpers;
    checkOut: CheckOut;
    setFieldValue: FormikHelpers<Bill>['setFieldValue'];
    totals: Array<number>;
    updateTotal: (totals: Array<number>) => void;
}

export const AProduct = (props: AProductProps) => {

    const [chekcIn, setCheckIn] = useState<ProductCheckIn>();
    const [total, setTotal] = useState<number>(0);

    useEffect(() => {
        props.totals[props.idx] = total;
        props.updateTotal(props.totals);
    }, [total])

    const recvCode = (code: string) => {
        console.log('Code:: ', code);
        getProduct(code)
    }

    const getProduct = async (code: string) => {
        let response = await CheckInService.getCheckInByProduct(code);
        if (response.success) {
            setCheckIn(response.body);
        }
    }

    const onCheckInChange = (checkIn: CheckIn) => {
        props.setFieldValue(`products[${props.idx}].checkInUid`, checkIn.uid);
        onRateChange(checkIn.rate);
    }

    const onRateChange = (value: number) => {
        props.setFieldValue(`products[${props.idx}].rate`, value);
        setTotal(props.checkOut.quantity * value);
    }

    const onQtyChange = (value: number) => {
        props.setFieldValue(`products[${props.idx}].quantity`, value);
        setTotal(props.checkOut.rate * value);
    }

    return (
        <HStack w={'full'} alignItems={'center'} key={props.idx}>
            <VStack alignItems={'start'} w={'full'}>
                <ProductSearch getCode={recvCode} />
            </VStack>
            <Field name={`products[${props.idx}].checkInUid`}>
                {({ field, form }: { field: FormikHandlers, form: FormikState<Bill> }) => (
                    <FormControl>
                        <Select placeholder='Select Check-In' {...field} onChange={(event) => onCheckInChange(chekcIn?.checkIns.find(ci => ci.uid === event.target.value) || {} as CheckIn)} >
                            {chekcIn?.checkIns.map((ci) => <option key={ci.uid} value={ci.uid}>{`Avl: ${ci.remainingQty} (${ci.unit})|Expire on :${new Date(ci.expireOn).toLocaleDateString()}|Rate: ${ci.rate}/${ci.rateUnit}`}</option>)}
                        </Select>
                    </FormControl>
                )}
            </Field>
            <Field name={`products[${props.idx}].rate`}>
                {({ field, form }: { field: FormikHandlers, form: FormikState<Bill> }) => (
                    <FormControl>
                        <InputGroup>
                            <InputLeftAddon children='&#8377;'/>
                            <Input type='number' {...field} onChange={(event) => onRateChange(isNaN(parseInt( event.target.value)) ? 0: parseInt( event.target.value))}/>
                            <InputRightAddon children={`/${chekcIn?.product.unit || ''}`} />
                        </InputGroup>
                    </FormControl>
                )}
            </Field>
            <Field name={`products[${props.idx}].quantity`}>
                {({ field, form }: { field: FormikHandlers, form: FormikState<Bill> }) => (
                    <FormControl>
                        <InputGroup>
                            <Input type='number' {...field} onChange={(event) => onQtyChange(isNaN(parseInt(event.target.value)) ? 0: parseInt(event.target.value))}/>
                            <InputRightAddon children={chekcIn?.product.unit} />
                        </InputGroup>
                    </FormControl>
                )}
            </Field>
            <VStack alignItems={'end'} w={500}>
                <Text>&#8377; {total}</Text>
            </VStack>
            <Container w={5}>
                {props.idx > 0 && <IconButton
                    ml={-5}
                    variant='ghost'
                    colorScheme='red'
                    aria-label='Call Sage'
                    fontSize='20px'
                    icon={<MdOutlineCancel />}
                    onClick={() => {
                        props.arrayHelpers.remove(props.idx);
                        props.totals.splice(props.idx, 1);
                        props.updateTotal(props.totals);
                    }}
                />}
            </Container>
        </HStack>
    );
}