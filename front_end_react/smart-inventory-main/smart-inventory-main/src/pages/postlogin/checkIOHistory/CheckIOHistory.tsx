import { Flex, Heading, HStack, Select, Table, TableContainer, Tbody, Td, Tfoot, Th, Thead, Tr } from '@chakra-ui/react';
import React, { ChangeEvent, useEffect, useState } from 'react';
import { MemoizedPaginator, usePaginator } from 'src/components/Paginator';
import { ProductSearch } from 'src/components/ProductSearch';
import { CheckInOut, CheckInOutCriteria } from 'src/model';
import { CheckInOutHistoryService } from 'src/services/CheckInOutHistoryService';

export const CheckIOHistory = () => {

    const [page, size, totalPages, setPage, setSize, setTotal] = usePaginator(10);
    const [entries, setEntries] = useState<Array<CheckInOut>>([] as Array<CheckInOut>);
    const [type, setType] = useState<string>('checkIn');
    const [criteria, setCriteria] = useState<CheckInOutCriteria>({} as CheckInOutCriteria);

    const recvCode = (code: string) => {
        criteria.productCode = code;
        setCriteria({...criteria});
    }

    const handleTypeChange = (event: ChangeEvent<HTMLSelectElement>) => {
        setType(event.target.value);
    }

    const getCheckIn = async() => {
        let resp = await CheckInOutHistoryService.getCheckInHistory(criteria, page, size);
        if (resp.success) {
            setEntries(resp.body);
            setTotal(resp.totalElements);
        }
    }

    const getCheckOut = async() => {
        let resp = await CheckInOutHistoryService.getCheckOutHistory(criteria, page, size);
        if (resp.success) {
            setEntries(resp.body);
            setTotal(resp.totalElements);
        }
    }

    useEffect(() => {
        if (type == 'checkOut') {
            getCheckOut();
        } else {
            getCheckIn();
        }
    }, [page, size, criteria, type]);

    return (
        <Flex direction='column'>
            <Heading fontSize={'xl'}>Check In-Out History</Heading>
            <HStack gap={10} mt={'2'}>
                <ProductSearch getCode={recvCode} />
                <Select w={300} onChange={handleTypeChange} value={type}>
                    <option value='checkIn'>Check-In</option>
                    <option value='checkOut'>Check-Out</option>
                </Select>
            </HStack>
            {entries.length > 0 && <TableContainer mt={5} w={'full'}>
                <Table size='sm'>
                    <Thead>
                        <Tr>
                            <Th>Product</Th>
                            <Th>Code</Th>
                            <Th isNumeric>Qty</Th>
                            <Th isNumeric>Rate</Th>
                            <Th>Entry On</Th>
                        </Tr>
                    </Thead>
                    <Tbody>
                        {entries.map((product, idx) =>
                            <Tr key={idx}>
                                <Td>{product.productName}</Td>
                                <Td>{product.productCode}</Td>
                                <Td isNumeric>{product.qty} {product.unit}</Td>
                                <Td isNumeric>&#8377;{product.rate}/{product.rateUnit}</Td>
                                <Td>{new Date(product.entryOn).toDateString()}</Td>
                            </Tr>
                        )}
                    </Tbody>
                    <Tfoot>
                        <Tr>
                            <Td colSpan={5}>
                                <MemoizedPaginator
                                    sizes={[5, 10, 20, 30, 40]}
                                    page={page}
                                    size={size}
                                    totalPages={totalPages}
                                    setPage={setPage}
                                    setSize={setSize}
                                    setTotal={setTotal}
                                />
                            </Td>
                        </Tr>
                    </Tfoot>
                </Table>
            </TableContainer>
            }
        </Flex>
    )
}