import { ArrowForwardIcon } from "@chakra-ui/icons";
import { Box, Button, Center, Flex, FormControl, FormErrorMessage, FormLabel, Heading, Input ,useToast} from "@chakra-ui/react";
import { Field, Form, Formik, FormikHandlers, FormikProps, FormikState } from "formik";
import React from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import * as Yup from 'yup';
import { useAuth } from "../../auth/AuthHook";
import { AuthService } from "../../auth/AuthService";
import { AuthResponse, Cred, Response } from "../../model";

export const Login = () => {

    const auth = useAuth();
    const location = useLocation();
    const navigate = useNavigate();
    const toast = useToast()  // by Amit

    const onLogin = async (cred: Cred, { resetForm, setSubmitting } : {resetForm: (nextState?: Partial<FormikState<Cred>>) => void, setSubmitting: (isSubmitting: boolean) => void;}) => {
        try {
            let response: Response<AuthResponse> = await AuthService.signIn(cred);
            if (response.success) {
                let redirectTo: string = '';
                if (location.state instanceof Location) {
                    redirectTo = location.state.pathname;
                }
                console.log('Redirect to :: ', redirectTo || '/postlogin');
                auth.signIn(response.body, (() => navigate(redirectTo || '/postlogin' )));
                resetForm();
            }
        } catch (err) {
            console.error(err);
            alert("invlaid login")   //by amit
            toast({                       //by amit
                title: 'Login Fail',      
                description: 'Invalid Login',
                status: 'error',
                isClosable: true,
            })

        } finally {
            setSubmitting(false);
        }
    }

    return (
        <Box w='calc(100vw)' h='calc(100vh)' bg="gray.500">
            <Center h='calc(100vh)'>
                <Flex direction='column' bg='white' w='500px' borderRadius='10px' p='4'>
                    <Heading as='h3' size='lg' py='2' mb='2'>
                        Login
                    </Heading>
                    <Formik
                        initialValues={{username: '', password: ''} as Cred}
                        onSubmit={onLogin}
                        validationSchema={Yup.object({
                            username: Yup.string().email('Invalid email address').required('Required'),
                            password: Yup.string().required('Required'),
                        })}
                    >
                        {(props: FormikProps<Cred>) =>
                            <Form>
                                <Field name='username'>
                                    {({ field, form }: {field: FormikHandlers, form: FormikState<Cred>}) => (
                                        <FormControl my='2' isInvalid={!!(form.errors.username && form.touched.username)}>
                                            <FormLabel htmlFor='username'>Username</FormLabel>
                                            <Input id='username' type='email' placeholder="Username" {...field} />
                                            <FormErrorMessage>{form.errors.username}</FormErrorMessage>
                                        </FormControl>
                                    )}
                                </Field>    
                                <Field name='password'>
                                    {({ field, form }: {field: FormikHandlers, form: FormikState<Cred>}) => (
                                        <FormControl my='2' isInvalid={!!(form.errors.password && form.touched.password)}>
                                            <FormLabel htmlFor='password'>Password</FormLabel>
                                            <Input id='password' type='password' placeholder="password" {...field} />
                                            <FormErrorMessage>{form.errors.password}</FormErrorMessage>
                                        </FormControl>
                                    )}
                                </Field>
                                <Flex my='2' alignItems='center' justifyContent='center' position='relative'>
                                    <Button colorScheme='teal' size='sm' type='submit' isLoading={props.isSubmitting}>
                                        Login
                                    </Button>
                                    <Button size='md' rightIcon={<ArrowForwardIcon />} colorScheme='blue' variant='ghost' position='absolute' right='0' as={Link} to='/register'>
                                        Register
                                    </Button>
                                </Flex>
                            </Form>
                        }
                    </Formik>
                </Flex>
            </Center>
        </Box>
    )
}