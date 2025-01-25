package com.litongjava.office.convert.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.local.LocalConverter;

import com.litongjava.model.body.RespBodyVo;
import com.litongjava.office.convert.config.LocalOffice;
import com.litongjava.tio.boot.http.TioRequestContext;
import com.litongjava.tio.http.common.HttpRequest;
import com.litongjava.tio.http.common.HttpResponse;
import com.litongjava.tio.http.common.UploadFile;
import com.litongjava.tio.http.server.util.Resps;
import com.litongjava.tio.utils.hutool.FilenameUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OfficeToPdfHandler {

  /**
   * 支持的文件类型
   */
  private static final String[] SUPPORTED_EXTENSIONS = { "doc", "docx", "xls", "xlsx", "odt", "ods", "odp" };

  /**
   * 将上传的Office文件转换为PDF并返回给客户端下载
   *
   * @param request      HttpRequest 对象，包含上传的文件
   * @param targetFormat 目标格式，固定为PDF
   * @return HttpResponse 对象，包含转换后的PDF文件
   */
  public HttpResponse toPdf(HttpRequest request, String targetFormat) {
    HttpResponse response = TioRequestContext.getResponse();
    UploadFile uploadFile = request.getUploadFile("file");

    if (uploadFile == null) {
      log.error("No file uploaded with key 'file'");
      return Resps.json(request, RespBodyVo.fail("未上传文件"));
    }

    // 检查文件类型
    String extension = FilenameUtils.getSuffix(uploadFile.getName()).toLowerCase();
    boolean supported = false;
    for (String ext : SUPPORTED_EXTENSIONS) {
      if (ext.equals(extension)) {
        supported = true;
        break;
      }
    }

    if (!supported) {
      log.error("Unsupported file type: {}", extension);
      return Resps.json(request, RespBodyVo.fail("不支持的文件类型"));
    }

    // 创建临时文件
    File inputFile = null;
    File outputFile = null;

    try {
      // 将上传的文件保存到临时文件
      inputFile = File.createTempFile("upload-", "." + extension);
      byte[] data = uploadFile.getData();
      FileUtils.writeByteArrayToFile(inputFile, data);

      log.info("Uploaded file saved to temporary file: {}", inputFile.getAbsolutePath());

      // 创建输出临时文件
      outputFile = File.createTempFile("output-", ".pdf");
      log.info("Output PDF will be saved to temporary file: {}", outputFile.getAbsolutePath());


      // 执行文件转换
      LocalConverter.make(LocalOffice.manager).convert(inputFile).to(outputFile).execute();
      log.info("File converted to PDF successfully");

      // 读取转换后的PDF文件
      byte[] fileBytes;
      try (InputStream inputStream = new FileInputStream(outputFile)) {
        int available = inputStream.available();
        fileBytes = new byte[available];
        int bytesRead = inputStream.read(fileBytes, 0, available);
        if (bytesRead != available) {
          log.warn("Expected to read {} bytes, but read {}", available, bytesRead);
        }
      }

      // 设置响应内容类型和文件名
      String contentType = "application/pdf";
      String downloadFilename = FilenameUtils.getBaseName(uploadFile.getName()) + ".pdf";

      // 构建响应
      response = Resps.bytesWithContentType(request, fileBytes, contentType);
      response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFilename + "\"");

      log.info("PDF file prepared for download: {}", downloadFilename);
      return response;

    } catch (IOException | OfficeException e) {
      log.error("Error converting file to PDF", e);
      return Resps.json(request, RespBodyVo.fail("文件转换失败"));
    } finally {
      // 删除临时文件
      if (inputFile != null && inputFile.exists()) {
        if (inputFile.delete()) {
          log.info("Temporary input file deleted: {}", inputFile.getAbsolutePath());
        } else {
          log.warn("Failed to delete temporary input file: {}", inputFile.getAbsolutePath());
        }
      }

      if (outputFile != null && outputFile.exists()) {
        if (outputFile.delete()) {
          log.info("Temporary output file deleted: {}", outputFile.getAbsolutePath());
        } else {
          log.warn("Failed to delete temporary output file: {}", outputFile.getAbsolutePath());
        }
      }
    }
  }
}
