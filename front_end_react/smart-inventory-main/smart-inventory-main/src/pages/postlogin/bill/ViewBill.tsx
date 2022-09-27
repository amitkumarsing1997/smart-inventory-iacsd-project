import React, { useEffect, useState } from 'react';
import { Box, Button, Divider, Flex, Heading, HStack, Spacer, Table, TableContainer, Tbody, Td, Text, Tfoot, Th, Thead, Tr, VStack } from "@chakra-ui/react"
import { useParams } from 'react-router-dom';
import { BillDtls } from 'src/model';
import { BillService } from 'src/services/BillService';
import { MdSimCardDownload } from 'react-icons/md';

export const ViewBill = () => {

    const { uid } = useParams();

    const [billDtls, setBillsDtls] = useState<BillDtls>({} as BillDtls);

    useEffect(() => {
        getBillDtls();
    }, [])

    const getBillDtls = async () => {
        let response = await BillService.getBill(uid || '');
        if (response.success) {
            console.log('response.body ::', response);
            setBillsDtls(response.body);
        } else {
            //use toast to show the msg
        }
    }

    return (
        <Flex direction={'column'}>
            <Flex>
                <Heading fontSize={'xl'}>Bill Details</Heading>
                <Spacer/>
                <Button size={'sm'} leftIcon={<MdSimCardDownload />} colorScheme='teal' variant='solid' onClick={() => BillService.downloadBill(uid || '')}>
                    Download Bill
                </Button>
            </Flex>
            {billDtls && <VStack mt={4} alignItems={'flex-start'}>
                <Divider></Divider>
                <HStack gap={20}>
                    <HStack gap={2}>
                        <Text>Customer Name: </Text><Text fontWeight={'bold'}>{billDtls.customerName}</Text>
                    </HStack>
                    <HStack gap={2}>
                        <Text>Mobile Number:</Text><Text fontWeight={'bold'}>{billDtls.mobileNumber}</Text>
                    </HStack>
                </HStack>
                <Divider></Divider>
                <TableContainer mt={5} w={'full'}>
                    <Table size='sm'>
                        <Thead>
                            <Tr>
                                <Th>Product</Th>
                                <Th>Code</Th>
                                <Th isNumeric>Qty</Th>
                                <Th isNumeric>Rate</Th>
                                <Th isNumeric>Total</Th>
                            </Tr>
                        </Thead>
                        <Tbody>
                            {billDtls.products?.map(product =>
                                <Tr key={product.code}>
                                    <Td>{product.name}</Td>
                                    <Td>{product.code}</Td>
                                    <Td isNumeric>{product.quantity} {product.unit}</Td>
                                    <Td isNumeric>&#8377;{product.rate}/{product.rateUnit}</Td>
                                    <Td isNumeric>&#8377;{product.total}</Td>
                                </Tr>
                            )}
                        </Tbody>
                        <Tfoot>
                            <Tr>
                                <Td colSpan={4} textAlign={'right'} fontWeight={'bold'}>Total</Td>
                                <Td isNumeric fontWeight={'bold'}>&#8377;{billDtls.totalAmt}</Td>
                            </Tr>
                        </Tfoot>
                    </Table>
                </TableContainer>
                <HStack gap={20}>
                    <HStack gap={2}>
                        <Text>Payment Mode: </Text><Text fontWeight={'bold'}>{billDtls.paymentMode}</Text>
                    </HStack>
                    <HStack gap={2}>
                        <Text>Payment Status:</Text><Text fontWeight={'bold'}>{billDtls.paymentStatus}</Text>
                    </HStack>
                    <HStack gap={2}>
                        <Text>Paid Amount:</Text><Text fontWeight={'bold'}>&#8377;{billDtls.paidAmt}</Text>
                    </HStack>
                </HStack>
            </VStack>}
        </Flex>
    )
}