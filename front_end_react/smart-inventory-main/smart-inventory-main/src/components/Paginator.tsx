import { Flex, IconButton, Select, Text } from '@chakra-ui/react';
import React, { ChangeEvent, Dispatch, memo, useEffect, useState } from 'react';
import { MdKeyboardArrowLeft, MdKeyboardArrowRight } from 'react-icons/md';

export const usePaginator = (initalSize: number):[page: number, size: number, totalPages: number, setPage: Dispatch<number>, setSize: Dispatch<number>, setTotal: Dispatch<number>] => {
    const [page, setPage] = useState<number>(0);
    const [size, setSize] = useState<number>(initalSize);
    const [total, setTotal] = useState<number>(0);
    const [totalPages, setTotalPages] = useState<number>(0);

    useEffect(() => {
        setTotalPages(Math.ceil(total / size));
    }, [size, total])

    return [page, size, totalPages, setPage, setSize, setTotal];
}

interface PaginatorProps {
    sizes: Array<number>;
    page: number;
    size: number;
    totalPages: number;
    setPage: (page: number) => void;
    setSize: (size: number) => void;
    setTotal: (total: number) => void;
}

export const Paginator = (props: PaginatorProps) => {
    return (
        <Flex justifyContent={'end'} alignItems={'center'} px={5} py={2}>
            <Text fontSize='sm'>Items per page:</Text>
            <Select variant='unstyled' w={70} mx={2} size='sm' onChange={(event: ChangeEvent<HTMLSelectElement>) => props.setSize(parseInt(event.target.value))} value={props.size}>
                {props.sizes.map(size => <option key={size} value={size}>{size}</option>)}
            </Select>
            <Text fontSize='sm'>Page {props.page+1} of {props.totalPages}</Text>
            <IconButton
                disabled={props.page < 1}
                onClick={() => props.setPage(props.page-1)}
                mx={2}
                variant='ghost'
                colorScheme='teal'
                aria-label='Call Sage'
                fontSize='20px'
                icon={<MdKeyboardArrowLeft />}
            />
            <IconButton
                disabled={props.page === props.totalPages-1}
                onClick={() => props.setPage(props.page+1)}
                variant='ghost'
                colorScheme='teal'
                aria-label='Call Sage'
                fontSize='20px'
                icon={<MdKeyboardArrowRight />}
            />
        </Flex>
    )
}

const equalityCheck = (prev: PaginatorProps, cur: PaginatorProps) => prev.page === cur.page && prev.totalPages === cur.totalPages && prev.size === cur.size;

export const MemoizedPaginator = memo(Paginator, equalityCheck);