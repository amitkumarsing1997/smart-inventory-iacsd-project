import { Button, FormControl, FormErrorMessage, FormLabel, Input, Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, useToast } from '@chakra-ui/react';
import React, { ChangeEvent, ChangeEventHandler, useEffect, useState } from 'react';
import { ProductCategory } from 'src/model';
import { ProductCategoryService } from 'src/services/ProductCategoryService';

interface SaveCategoryProps {
    category: ProductCategory;
    isOpen: boolean;
    onClose: (reload: boolean) => void
}

export const SaveCategory = (props: SaveCategoryProps) => {
    const [category, setCategory] = useState<ProductCategory>(props.category);
    const [categoryError, setCategoryError] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        console.log('use effect');
        setCategory(props.category);
    }, [props.category.uid])

    const toast = useToast();

    const handleInputChange: ChangeEventHandler<HTMLInputElement> = (e: ChangeEvent<HTMLInputElement>) => setCategory({ ...category, name: e.target.value } as ProductCategory)

    const onSave = async () => {
        if (!category.name) {
            setCategoryError(true);
            return;
        } else {
            setCategoryError(false);
        }
        setLoading(true);
        let response = await ProductCategoryService.SaveCategory(category);
        if (response.success) {
            toast({
                title: 'Category Saved',
                status: 'success',
                isClosable: true,
            });
        } else {
            toast({
                title: 'Error',
                description: response.message,
                status: 'error',
                isClosable: true,
            });
        }
        setLoading(false);
        onCancel(true);
    }

    const onCancel = (reload: boolean) => {
        setCategory(props.category);
        setCategoryError(false);
        props.onClose(reload);
    }

    return (
        <Modal isOpen={props.isOpen} onClose={() => onCancel(false)}>
            <ModalOverlay />
            <ModalContent>
                <ModalHeader>Save Category</ModalHeader>
                <ModalCloseButton />
                <ModalBody>
                    <FormControl isInvalid={categoryError}>
                        <FormLabel>Product Category</FormLabel>
                        <Input
                            type='text'
                            value={category.name}
                            onChange={handleInputChange}
                        />
                        {categoryError &&
                            <FormErrorMessage>Product category is required.</FormErrorMessage>
                        }
                    </FormControl>
                </ModalBody>
                <ModalFooter>
                    <Button colorScheme='blue' mr={3} onClick={onSave} isLoading={loading}>
                        Save
                    </Button>
                    <Button variant='ghost' onClick={() => onCancel(false)}>Cancel</Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    )
}