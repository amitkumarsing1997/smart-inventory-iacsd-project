import React from "react";
import { Accordion, AccordionButton, AccordionIcon, AccordionItem, AccordionPanel, Box, Flex, FlexProps, Icon, Link } from "@chakra-ui/react";
import { IconType } from "react-icons";
import { Link as RRDL, To, useMatch } from "react-router-dom"
import { LinkItemProps } from "src/model";

interface NavItemProps extends FlexProps {
  icon: IconType;
  children: String;
  route: To;
  submenu?: Array<LinkItemProps>
}

export const NavItem = ({ icon, children, route, submenu, ...rest }: NavItemProps) => {
  const isMatch = useMatch(route.toString());
  const activeLink = {
    bg: 'teal.500   ',
    color: 'white',
  }

  if (submenu) {
    return (
      <Accordion allowMultiple>
        <AccordionItem sx={{border: 'none'}}>
          <AccordionButton sx={{px: 4, py: 0}}>
            <Flex
              align="center"
              py="2"
              mx="4"
              my="1"
              cursor="pointer"
              sx={isMatch ? activeLink : {}}
              {...rest}>
              {icon && (
                <Icon
                  mr="4"
                  fontSize="16"
                  as={icon}
                />
              )}
              {children}
            </Flex>
            <AccordionIcon />
          </AccordionButton>
          <AccordionPanel py={0}>
            {submenu.map((link) => (
              <NavItem key={link.name} icon={link.icon} route={link.route} submenu={link.submenu}>
                {link.name}
              </NavItem>
            ))}
          </AccordionPanel>
        </AccordionItem>
      </Accordion>
    )
  } else {
    return (
      <Link as={RRDL} to={route} style={{ textDecoration: 'none' }} _focus={{ boxShadow: 'none' }}>
        <Flex
          align="center"
          px="4"
          py="2"
          mx="4"
          my="1"
          borderRadius="lg"
          role="group"
          cursor="pointer"
          sx={isMatch ? activeLink : {}}
          _hover={{
            bg: 'gray.400',
            color: 'white',
          }}

          {...rest}>
          {icon && (
            <Icon
              mr="4"
              fontSize="16"
              _groupHover={{
                color: 'white',
              }}
              as={icon}
            />
          )}
          {children}
        </Flex>
      </Link >
    );
  }
};