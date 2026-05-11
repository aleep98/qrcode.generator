package com.aleedev.qrcode.generator.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aleedev.qrcode.generator.dto.QrCodeGenerateResponse;
import com.aleedev.qrcode.generator.ports.StoragePort;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QrCodeGeneratorService {
    private final StoragePort storage;

    public QrCodeGeneratorService(StoragePort storage) {
        this.storage = storage;
    }

    public QrCodeGenerateResponse generateAndUploadQrCode(String text) throws IOException, WriterException{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, com.google.zxing.BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] qrCodeData = pngOutputStream.toByteArray();


        String url = storage.uploadFile(qrCodeData, UUID.randomUUID().toString(), "image/png");

        return new QrCodeGenerateResponse(url);
    }
}
