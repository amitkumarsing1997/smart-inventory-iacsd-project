import {
  Box, Drawer,
  DrawerContent, Progress, useColorModeValue, useDisclosure
} from '@chakra-ui/react';
import React from 'react';
import { Outlet } from 'react-router-dom';
import { MobileNav } from './MobileNav';
import { SidebarContent } from './SidebraContent';


export const PostloginLayout = () => {

  const { isOpen, onOpen, onClose } = useDisclosure();

  return (
    <>
      {/* {<Progress colorScheme='teal' size='xs' isIndeterminate />} */}
      <Box minH="100vh">
        <SidebarContent
          onClose={() => onClose}
          display={{ base: 'none', md: 'block' }}
        />
        <Drawer
          autoFocus={false}
          isOpen={isOpen}
          placement="left"
          onClose={onClose}
          returnFocusOnClose={false}
          onOverlayClick={onClose}
          size="full">
          <DrawerContent>
            <SidebarContent onClose={onClose} />
          </DrawerContent>
        </Drawer>
        {/* mobilenav */}
        <MobileNav onOpen={onOpen} />
        <Box ml={{ base: 0, md: 60 }} p="4">
          <Outlet />
        </Box>
      </Box>
    </>
  );
}