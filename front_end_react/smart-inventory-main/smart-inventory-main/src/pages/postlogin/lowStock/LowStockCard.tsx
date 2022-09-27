import { Box, Divider, Flex, HStack, Text, VStack } from '@chakra-ui/react';
import React from 'react';
import { Product } from 'src/model';


export interface LowStockCardProps {
    product: Product
}


export const LowStockCard = (props: LowStockCardProps) => {
    return (
            <Flex
                bgGradient='linear(to-r, red.100, green.100)'
                w={{base: 'full', md: 3/12}}
                minW={300}
                alignItems='center'
                justifyContent={'space-between'}
                role={'group'}
                px={4}
                py={1}
                boxShadow={'lg'}
                rounded={'lg'}
                pos={'relative'}
                zIndex={1}>

                <VStack w={'full'}>
                    <Box>
                        <Text fontSize='xl' textTransform={'capitalize'}>{props.product.name}</Text>
                    </Box>
                    <Divider/>
                    <HStack gap={5}>
                        <VStack justifyItems={'center'}>
                            <Text color='red' fontWeight={'bold'}>{props.product.avlQty}</Text>
                            <Text fontSize={'sm'}>Available({props.product.unit})</Text>
                        </VStack>
                        <VStack>
                            <Text color='green' fontWeight={'bold'}>{props.product.minimumQuantity}</Text>
                            <Text fontSize={'sm'}>Minimum({props.product.unit})</Text>
                        </VStack>
                    </HStack>
                </VStack>

            </Flex>
    )
}