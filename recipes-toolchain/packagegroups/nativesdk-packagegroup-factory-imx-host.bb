# Copyright (C) 2019 Data Respons Solutions AB

DESCRIPTION = "Host factory tools"
LICENSE = "MIT"

inherit packagegroup nativesdk

RDEPENDS:${PN} += " \
    nativesdk-imx-usb-loader \
"
