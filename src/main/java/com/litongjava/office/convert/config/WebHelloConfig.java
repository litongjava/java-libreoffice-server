package com.litongjava.office.convert.config;

import com.litongjava.annotation.AConfiguration;
import com.litongjava.annotation.Initialization;
import com.litongjava.context.BootConfiguration;
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

@AConfiguration
public class WebHelloConfig implements BootConfiguration {

  @Initialization
  public void config() {

    TioBootServer server = TioBootServer.me();
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
