package com.sew.drone.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  /**
   * Convert given image file to a byte array.
   *
   * @param image Multipart image file
   * @return A byte array of the image
   */
  byte[] convertImageToByteArray(MultipartFile image);
}
