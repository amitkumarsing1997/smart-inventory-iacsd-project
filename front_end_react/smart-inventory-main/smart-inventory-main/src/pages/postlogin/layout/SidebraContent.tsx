import { Box, BoxProps, CloseButton, Flex, Image, Text, useColorModeValue } from '@chakra-ui/react';
import React from 'react';
import { LinkItems } from './LinkItems';
import { NavItem } from './NavItem';

interface SidebarProps extends BoxProps {
    onClose: () => void;
}

export const SidebarContent = ({ onClose, ...rest }: SidebarProps) => {
    return (
        <Box
            transition="3s ease"
            bg={useColorModeValue('white', 'gray.900')}
            borderRight="1px"
            borderRightColor={useColorModeValue('gray.200', 'gray.700')}
            w={{ base: 'full', md: 60 }}
            pos="fixed"
            h="full"
            {...rest}>
            <Flex h="20" alignItems="center">
                <Image
                    maxWidth={250}
                    // src='logo/Smart Stock-logos_transparent.png'
                    src='logo/Smart Inventory-logos.png'
                />
                <CloseButton display={{ base: 'flex', md: 'none' }} onClick={onClose} />
            </Flex>
            {LinkItems.map((link) => (
                <NavItem key={link.name} icon={link.icon} route={link.route} submenu={link.submenu}>
                    {link.name}
                </NavItem>
            ))}
        </Box>
    );
};