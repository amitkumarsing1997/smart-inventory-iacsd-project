import { Flex, Heading, HStack, Text, VStack } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { UserInfo } from 'src/model';
import { ProfileService } from 'src/services/ProfileService';

export const UserProfile = () => {

    const [userInfo, setUserInfo] = useState<UserInfo>({} as UserInfo)

    useEffect(() => {
        const getUserProfile = async() => {
            let response = await ProfileService.getUserProfile();
            if (response.success) {
                setUserInfo(response.body);
            }
        }

        getUserProfile();
    }, []);

    return (
        <Flex direction={'column'}>
            <Heading fontSize={'xl'}>User Profile</Heading>
            <VStack alignItems={'start'} mt={5}>
                <HStack><Text>Name: </Text><Text fontWeight={'semibold'}>{userInfo.name}</Text></HStack>
                <HStack><Text>Email: </Text><Text fontWeight={'semibold'}>{userInfo.email}</Text></HStack>
                <HStack><Text>Shop Name: </Text><Text fontWeight={'semibold'}>{userInfo.shopName}</Text></HStack>
                <HStack><Text>Address: </Text><Text fontWeight={'semibold'}>{userInfo.shopAddress}</Text></HStack>
            </VStack>
        </Flex>
    )
}