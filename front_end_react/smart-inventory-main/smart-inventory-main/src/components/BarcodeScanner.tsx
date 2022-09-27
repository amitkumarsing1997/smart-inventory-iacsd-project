import { Flex, Modal, ModalBody, ModalCloseButton, ModalContent, ModalHeader, ModalOverlay, Select } from '@chakra-ui/react';
import { BrowserMultiFormatReader, ChecksumException, Exception, FormatException, NotFoundException, Result } from '@zxing/library';
import React, { ChangeEvent, useEffect, useState } from 'react';

interface BarcodeScannerProps {
    getCode: (code: string) => void,
    onError: (err: string) => void
    isOpen: boolean
    onClose: VoidFunction
}

export const BarcodeScanner = (props: BarcodeScannerProps) => {


    const { isOpen } = props || false;

    const codeReader = new BrowserMultiFormatReader();
    const [onClose, setOnClose] = useState<(VoidFunction)>(() => {});
    const [selectedCameraId, setSelectedCameraId] = useState<string>('');
    const [cameraDevices, setCameraDevices] = useState<Array<MediaDeviceInfo>>([]);

    useEffect(() => {
        const getDevice = async () => {
            try {
                const cameraDevices: Array<MediaDeviceInfo> = await codeReader.listVideoInputDevices();
                if (cameraDevices) {
                    setSelectedCameraId(cameraDevices[0].deviceId);
                    setCameraDevices(cameraDevices);
                    startDecoding();
                }
            } catch (error) {
                console.error('Camera error :: ', error)
                props.onError('Camera error occurred');
            }
        }
        if (isOpen) {
            getDevice();
        }
    }, [isOpen])

    const startDecoding = () => {
        codeReader.decodeFromVideoDevice(selectedCameraId, 'video', (result: Result, err: Exception | undefined) => {

            const close = () => {
                codeReader.reset();
                props.onClose();
            }
            setOnClose(() => close);    // if you directly pass the function it will be executed, so either pass like this or  ()=>()=>

            if (result) {
                props.getCode(result.getText());
                close();
            }

            if (err instanceof NotFoundException) {
                console.log("No QR code found.");
            }

            if (err instanceof ChecksumException) {
                console.log("A code was found, but it's read value was not valid.");
            }

            if (err instanceof FormatException) {
                console.log("A code was found, but it was in a invalid format.");
            }
        })
    }

    return (
        <Modal isOpen={props.isOpen} onClose={onClose}>
            <ModalOverlay />
            <ModalContent>
                <ModalHeader>Scan Barcode</ModalHeader>
                <ModalCloseButton />
                <ModalBody>
                    <Flex direction={'column'}>
                        <Select mb={2}
                            onChange={(deviceId: ChangeEvent<HTMLSelectElement>) => setSelectedCameraId(deviceId.target.value)}>
                            {cameraDevices.map((device) => <option key={device.deviceId} value={device.deviceId}>{device.label}</option>)}
                        </Select>
                        <video id="video" width="100%" height="100%"></video>
                    </Flex>
                </ModalBody>
            </ModalContent>
        </Modal>
    )
}