import { Flex } from '@chakra-ui/react';
import JsBarcode from 'jsbarcode';
import React from 'react';

export const Barcode = ({ value }: { value: string }) => {
    const ref = React.useRef<HTMLDivElement>(null);
    const canvas = document.createElement("canvas");

    React.useEffect(() => {
        JsBarcode(canvas, value);
        ref?.current?.appendChild(canvas);
    }, [ref]);

    return (
        <Flex justifyContent={'center'} alignItems={'center'} ref={ref}></Flex>
    )
}