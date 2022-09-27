import { Box, Heading, HStack, IconButton, Input, InputGroup, InputRightElement, Text, Tooltip, WrapItem } from '@chakra-ui/react';
import React, { ChangeEvent, useState } from 'react';
import { MdEdit, MdInput, MdOutlineCheck, MdOutlineClose, MdPrint } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';
import { Barcode } from 'src/components/Barcode';
import { ProductListing } from 'src/model';
import { ProductService } from 'src/services/ProductService';

export interface ProductCardProps {
    product: ProductListing
}

export const ProductCard = (props: ProductCardProps) => {

    const navigate = useNavigate();
    const [print, setPrint] = useState<boolean>(false);
    const [printQty, setPrintQty] = useState<number>();
    const [qtyErr, setQtyErr] = useState<string>('');

    const printQtyChange = (event: ChangeEvent<HTMLInputElement>) => {

        const qty = parseInt(event.target.value);
        if (qty > 0 ) {
            setPrintQty(qty);
        } else {
            setPrintQty(undefined);
            setQtyErr('Must be positive');
        }
        
    }

    const onPrint = async() => {
        await ProductService.downloadBarcode(product.code, printQty || 3);
        setPrint(false);
    }


    const { product } = props;

    return (
        <WrapItem key={product.uid}>
            <Box
                bgColor={'gray.100'}
                w={350}
                role={'group'}
                px={4}
                py={2}
                boxShadow={'xl'}
                rounded={'lg'}
                pos={'relative'}
                zIndex={1}>
                <Box>
                    <Heading fontSize={'2xl'} fontFamily={'body'} fontWeight={500}>{product.name}</Heading>
                    <Text as={'span'}>Available Qty: </Text><Text as={'span'} fontWeight={800} fontSize={'xl'}>{product.avlQty}{product.unit.toLowerCase()}</Text>
                </Box>
                <Barcode value={product.code} />
                <HStack justifyContent='end'>
                    <Tooltip label='Check In'>
                        <IconButton
                            variant='ghost'
                            colorScheme='teal'
                            aria-label='Check In'
                            icon={<MdInput />}
                            onClick={() => {
                                navigate('/postlogin/check-in?code=' + product.code)
                            }}
                        />
                    </Tooltip>
                    {print ?
                        <Box>
                            <InputGroup size='md'>
                                <Input
                                    isInvalid={!!qtyErr}
                                    pr='4.5rem'
                                    type='number'
                                    placeholder='Enter Qty'
                                    value={printQty}
                                    onChange={printQtyChange}
                                />
                                <InputRightElement width='5.4rem'>
                                    <HStack>
                                        <IconButton
                                            variant='ghost' size={'sm'}
                                            colorScheme='red'
                                            aria-label='Cancel'
                                            icon={<MdOutlineClose />}
                                            onClick={() => setPrint(false)}
                                        />
                                        <IconButton
                                            onClick={onPrint}
                                            variant='ghost' size={'sm'}
                                            colorScheme='teal'
                                            aria-label='Print'
                                            icon={<MdOutlineCheck />}
                                        />
                                    </HStack>
                                </InputRightElement>
                            </InputGroup>
                        </Box>

                        :
                        <Tooltip label='Print Barcode'>
                            <IconButton
                                variant='ghost'
                                colorScheme='teal'
                                aria-label='Print'
                                icon={<MdPrint />}
                                onClick={() => setPrint(true)}
                            />
                        </Tooltip>
                    }
                    <Tooltip label='Edit'>
                        <IconButton
                            variant='ghost'
                            colorScheme='teal'
                            aria-label='Edit'
                            icon={<MdEdit />}
                            onClick={() => {
                                navigate('../product/save?uid=' + product.uid)
                            }}
                        />
                    </Tooltip>
                </HStack>
            </Box>
        </WrapItem>
    );
}