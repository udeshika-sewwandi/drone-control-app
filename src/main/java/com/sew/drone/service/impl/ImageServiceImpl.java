package com.sew.drone.service.impl;

import com.sew.drone.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

  private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

  @Override
  public byte[] convertImageToByteArray(MultipartFile image) {
    try {
      return image.getBytes();
    } catch (IOException ioException) {
      logger.error("An error occurred while converting image file to bytes array");
    }
    return new byte[0];
  }
}
