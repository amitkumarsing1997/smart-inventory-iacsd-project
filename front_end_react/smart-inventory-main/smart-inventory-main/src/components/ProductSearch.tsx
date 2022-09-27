import { FormErrorMessage, HStack, IconButton, Input, InputGroup, InputRightElement, Text, useDisclosure, VStack } from "@chakra-ui/react";
import React, { ChangeEvent, useState } from 'react';
import { BiBarcodeReader } from 'react-icons/bi';
import { MdSearch } from 'react-icons/md';
import { BarcodeScanner } from "./BarcodeScanner";

interface ProductSearchProps {
    code?: string
    getCode: (code: string) => void
}

export const ProductSearch = (props: ProductSearchProps) => {

    const [code, setCode] = useState<string>(props.code || '');
    const [codeErr, setCodeErr] = useState<string>('');
    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.value) {
            setCodeErr('');
        } else {
            setCodeErr('Code is required');
        }
        setCode(event.target.value);
    };

    const { isOpen, onOpen, onClose } = useDisclosure()

    const sendCode = () => {
        if (!code) {
            setCodeErr('Code is required');
        } else {
            setCodeErr('');
            props.getCode(code);
        }
    }

    return (
        <VStack align={'start'}>
            <BarcodeScanner
                isOpen={isOpen}
                onClose={onClose}
                getCode={(code) => {
                    setCode(code);
                    props.getCode(code);
                }}
                onError={(err) => console.error('Error :: ', err)} />
            <HStack>
                <InputGroup size='md'>
                    <Input
                        isInvalid={!!codeErr}
                        pr='4.5rem'
                        type='text'
                        placeholder='Enter Product Code'
                        value={code}
                        onChange={handleChange}
                    />
                    <InputRightElement width='5.4rem'>
                        <HStack>
                            <IconButton
                                variant='outline' size={'sm'}
                                colorScheme='teal'
                                aria-label='Send email'
                                icon={<MdSearch />}
                                onClick={sendCode}
                            />
                            <IconButton
                                onClick={onOpen}
                                variant='outline' size={'sm'}
                                colorScheme='teal'
                                aria-label='Send email'
                                icon={<BiBarcodeReader />}
                            />
                        </HStack>
                    </InputRightElement>
                </InputGroup>
            </HStack>
            {codeErr && <Text fontSize='sm' mt={1} color={'red.500'}>{codeErr}</Text>}
        </VStack>
    )
}