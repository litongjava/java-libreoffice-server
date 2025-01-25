package com.litongjava.office.convert.config;

import org.jodconverter.core.office.OfficeException;
import org.jodconverter.local.office.LocalOfficeManager;

import com.litongjava.annotation.AConfiguration;
import com.litongjava.annotation.Initialization;
import com.litongjava.context.BootConfiguration;
import com.litongjava.hook.HookCan;
import com.litongjava.office.convert.handler.HelloHandler;
import com.litongjava.office.convert.handler.OfficeDocToPdfHandler;
import com.litongjava.office.convert.handler.OfficeOdpToPdfHandler;
import com.litongjava.office.convert.handler.OfficeOdsToPdfHandler;
import com.litongjava.office.convert.handler.OfficeOdtToPdfHandler;
import com.litongjava.office.convert.handler.OfficePptToPdfHandler;
import com.litongjava.office.convert.handler.OfficeToPdfHandler;
import com.litongjava.office.convert.handler.OfficeXlsToPdfHandler;
import com.litongjava.tio.boot.server.TioBootServer;
import com.litongjava.tio.http.server.router.HttpRequestRouter;

import lombok.extern.slf4j.Slf4j;

@AConfiguration
@Slf4j
public class WebHelloConfig implements BootConfiguration {

  @Initialization
  public void config() {

    TioBootServer server = TioBootServer.me();

    LocalOffice.manager = LocalOfficeManager.builder().install().build();
    try {
      LocalOffice.manager.start();
      log.info("LocalOfficeManager started");
    } catch (OfficeException e) {
      log.error(e.getMessage(), e);
    }
    HookCan.me().addDestroyMethod(() -> {

      // 停止OfficeManager
      if (LocalOffice.manager != null) {
        try {
          LocalOffice.manager.stop();
          log.info("LocalOfficeManager stopped");
        } catch (OfficeException e) {
          log.error("Error stopping OfficeManager", e);
        }
      }

    });

    HttpRequestRouter requestRouter = server.getRequestRouter();

    HelloHandler helloHandler = new HelloHandler();

    OfficePptToPdfHandler officePptToPdfHandler = new com.litongjava.office.convert.handler.OfficePptToPdfHandler();
    requestRouter.add("/hello", helloHandler::hello);
    requestRouter.add("/api/ppt/pdf", officePptToPdfHandler::toPdf);

    OfficeDocToPdfHandler officeDocToPdfHandler = new OfficeDocToPdfHandler();
    requestRouter.add("/api/doc/pdf", officeDocToPdfHandler::toPdf);

    OfficeXlsToPdfHandler officeXlsToPdfHandler = new OfficeXlsToPdfHandler();
    requestRouter.add("/api/xls/pdf", officeXlsToPdfHandler::toPdf);

    OfficeOdtToPdfHandler officeOdtToPdfHandler = new OfficeOdtToPdfHandler();
    requestRouter.add("/api/odt/pdf", officeOdtToPdfHandler::toPdf);

    OfficeOdsToPdfHandler officeOdsToPdfHandler = new OfficeOdsToPdfHandler();
    requestRouter.add("/api/ods/pdf", officeOdsToPdfHandler::toPdf);

    OfficeOdpToPdfHandler officeOdpToPdfHandler = new OfficeOdpToPdfHandler();
    requestRouter.add("/api/odp/pdf", officeOdpToPdfHandler::toPdf);

    OfficeToPdfHandler officeToPdfHandler = new OfficeToPdfHandler();
    requestRouter.add("/api/convert/pdf", request -> officeToPdfHandler.toPdf(request, "pdf"));

  }

}
