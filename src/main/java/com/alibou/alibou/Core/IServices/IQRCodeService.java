package com.alibou.alibou.Core.IServices;

public interface IQRCodeService {
    byte[] generateQRCodeImage(String text, int width, int height);
}
