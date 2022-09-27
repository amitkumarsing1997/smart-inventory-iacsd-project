import { Box, Flex, Heading, HStack, IconButton, Input, InputGroup, InputRightElement, Table, TableContainer, Tbody, Td, Text, Tfoot, Th, Thead, Tr, VStack } from '@chakra-ui/react';
import React, { ChangeEvent, useState } from 'react';
import { MdRemoveRedEye, MdSearch, MdSimCardDownload } from 'react-icons/md';
import { Link } from 'react-router-dom';
import { BillListing } from 'src/model';
import { BillService } from 'src/services/BillService';

export const ListBill = () => {

    const [mobileNumber, setMobileNumber] = useState<string>('');
    const [mnErr, setMnErr] = useState<string>('');
    const [bills, setBills] = useState<Array<BillListing>>([] as Array<BillListing>);

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.value) {
            setMnErr('');
        } else {
            setMnErr('Mobile number is required');
        }
        setMobileNumber(event.target.value);
    };

    const searchBill = () => {
        if (!mobileNumber) {
            setMnErr('Mobile number is required');
        } else {
            setMnErr('');
            getBills();
        }
    }

    const getBills = async () => {
        let response = await BillService.getBills(mobileNumber);
        if (response.success) {
            setBills(response.body);
        } else {
            //use toast to show error msg
        }
    }



    return (
        <Flex direction={'column'}>
            <Heading fontSize={'xl'}>Bills</Heading>
            <VStack alignItems={'center'} w={{ base: 'full', md: 350 }} mt={2}>
                <InputGroup size='md'>
                    <Input
                        pr='4.5rem'
                        type="text"
                        placeholder='Enter customer mobile number'
                        value={mobileNumber}
                        onChange={handleChange}
                    />
                    <InputRightElement width='4.5rem'>
                        <IconButton
                            variant='outline' size={'sm'}
                            colorScheme='teal'
                            aria-label='Send email'
                            icon={<MdSearch />}
                            onClick={searchBill}
                        />
                    </InputRightElement>
                </InputGroup>
                {mnErr && <Text fontSize='sm' mt={1} color={'red.500'}>{mnErr}</Text>}
            </VStack>
            <TableContainer mt={5}>
                <Table size='sm'>
                    <Thead>
                        <Tr>
                            <Th>Customer Name</Th>
                            <Th>Mobile Number</Th>
                            <Th isNumeric>Total Amt</Th>
                            <Th>Payment Status</Th>
                            <Th>Generated On</Th>
                            <Th>Action</Th>
                        </Tr>
                    </Thead>
                    <Tbody>
                        {bills.map(bill =>
                            <Tr key={bill.uid}>
                                <Td>{bill.customerName}</Td>
                                <Td>{bill.mobileNumber}</Td>
                                <Td isNumeric>&#8377;{bill.totalAmt}</Td>
                                <Td>{bill.paymentStatus}</Td>
                                <Td>{new Date(bill.createdOn).toDateString()}</Td>
                                <Td>
                                    <HStack>
                                        <IconButton variant={'ghost'} color={'teal'} aria-label='Search database' icon={<MdRemoveRedEye />} as={Link} to={'../bill/view/' + bill.uid} />
                                        <IconButton variant={'ghost'} color={'teal'} aria-label='Search database' icon={<MdSimCardDownload />} onClick={() => BillService.downloadBill(bill.uid)}/>
                                    </HStack>
                                </Td>
                            </Tr>
                        )}
                    </Tbody>
                </Table>
            </TableContainer>
        </Flex>
    )
}