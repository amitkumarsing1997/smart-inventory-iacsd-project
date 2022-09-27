import React from 'react';
import { ArrowForwardIcon } from "@chakra-ui/icons"
import { Button, Center, Flex, FormControl, FormErrorMessage, FormLabel, Heading, Input, useToast } from "@chakra-ui/react"
import { Field, Form, Formik, FormikHandlers, FormikProps, FormikState } from "formik"
import { Link, useNavigate } from "react-router-dom"
import { RegisterUser, Response } from 'src/model';
import * as Yup from 'yup';
import { RegisterService } from 'src/services/RegisterService';

export const Register = () => {

    const navigate = useNavigate();
    const toast = useToast()

    const onRegister = async (payload: RegisterUser, { resetForm, setSubmitting }: { resetForm: (nextState?: Partial<FormikState<RegisterUser>>) => void, setSubmitting: (isSubmitting: boolean) => void; }) => {
        try {
            let response: Response<boolean> = await RegisterService.registerUser(payload);
            if (response.success) {
                resetForm();
                toast({
                    title: 'Registration Success',
                    status: 'success',
                    isClosable: true,
                })
                navigate('/login');
            } else {
                toast({
                    title: 'Registration Fail',
                    description: response.message,
                    status: 'error',
                    isClosable: true,
                })
            }
        } catch (err) {
            console.error('Error :: ', err);
            toast({
                title: 'Registration Fail',
                description: 'Some error occurred while registering',
                status: 'error',
                isClosable: true,
            })
        } finally {
            setSubmitting(false);
        }
    }

    return (
        <Center h='calc(100vh)' w='calc(100vw)' bg='gray.500'>
            <Flex direction='column' bg='white' w='500px' borderRadius='10px' p='4'>
                <Heading as='h3' size='lg' py='2' mb='2'>
                    Register
                </Heading>
                <Formik
                    initialValues={{ name: '', email: '', password: '', shopName: '', address: '' } as RegisterUser}
                    onSubmit={onRegister}
                    validationSchema={Yup.object({
                        name: Yup.string().required('Required'),
                        email: Yup.string().email('Invalid email address').required('Required'),
                        password: Yup.string().required('Required'),
                        shopName: Yup.string().required('Required'),
                        address: Yup.string().required('Required')
                    })}
                >
                    {(props: FormikProps<RegisterUser>) =>
                        <Form>
                            <Field name='name'>
                                {({ field, form }: { field: FormikHandlers, form: FormikState<RegisterUser> }) => (
                                    <FormControl my='2' isInvalid={!!(form.errors.name && form.touched.name)}>
                                        <FormLabel htmlFor='name'>Name</FormLabel>
                                        <Input id='name' type='text' {...field} />
                                        <FormErrorMessage>{form.errors.name}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Field name='email'>
                                {({ field, form }: { field: FormikHandlers, form: FormikState<RegisterUser> }) => (
                                    <FormControl my='2' isInvalid={!!(form.errors.email && form.touched.email)}>
                                        <FormLabel htmlFor='email'>Email address</FormLabel>
                                        <Input id='email' type='email' {...field} />
                                        <FormErrorMessage>{form.errors.email}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Field name='password'>
                                {({ field, form }: { field: FormikHandlers, form: FormikState<RegisterUser> }) => (
                                    <FormControl my='2' isInvalid={!!(form.errors.password && form.touched.password)}>
                                        <FormLabel htmlFor='password'>Password</FormLabel>
                                        <Input id='password' type='password' {...field} />
                                        <FormErrorMessage>{form.errors.password}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Field name='shopName'>
                                {({ field, form }: { field: FormikHandlers, form: FormikState<RegisterUser> }) => (
                                    <FormControl my='2' isInvalid={!!(form.errors.shopName && form.touched.shopName)}>
                                        <FormLabel htmlFor='shop_name'>Shop Name</FormLabel>
                                        <Input id='shop_name' type='text' {...field} />
                                        <FormErrorMessage>{form.errors.shopName}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Field name='address'>
                                {({ field, form }: { field: FormikHandlers, form: FormikState<RegisterUser> }) => (
                                    <FormControl my='2' isInvalid={!!(form.errors.address && form.touched.address)}>
                                        <FormLabel htmlFor='address'>Address</FormLabel>
                                        <Input id='address' type='text' {...field} />
                                        <FormErrorMessage>{form.errors.address}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>

                            <Flex my='2' alignItems='center' justifyContent='center' position='relative'>
                                <Button colorScheme='teal' size='sm' type='submit' isLoading={props.isSubmitting}>
                                    Register
                                </Button>
                                <Button size='sm' rightIcon={<ArrowForwardIcon />} colorScheme='blue' variant='ghost' position='absolute' right='0' as={Link} to='/login'>
                                    Login
                                </Button>
                            </Flex>
                        </Form>
                    }
                </Formik>
            </Flex>
        </Center>
    )
}