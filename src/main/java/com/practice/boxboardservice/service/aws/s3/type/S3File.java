package com.practice.boxboardservice.service.aws.s3.type;

/**
 * S3File.
 *
 * @author : middlefitting
 * @since : 2023/08/28
 */
public enum S3File {
  SCRIPT("script_file/", "sh", "application/x-sh", "boards.error.s3-script-upload-fail"),
  FREE_BOARD_IMAGE("free_board_image/", "png", "image/png",
      "boards.error.s3-free-board-image-upload-fail");

  private final String folder;
  private final String format;
  private final String contentType;
  private final String errMsg;

  S3File(String folder, String format, String contentType, String errMsg) {
    this.folder = folder;
    this.format = format;
    this.contentType = contentType;
    this.errMsg = errMsg;
  }

  public String getFolder() {
    return folder;
  }

  public String getFormat() {
    return format;
  }

  public String getContentType() {
    return contentType;
  }

  public String getErrMsg() {
    return errMsg;
  }
}
