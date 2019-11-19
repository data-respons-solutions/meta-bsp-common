DESCRIPTION = "NVRAM in eMMC GP partition"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

SRC_URI = "file://nvram-factory.mount.in \
            file://nvram-user.mount \
"

NVRAM_PLUS_PRODUCTION_MOUNT_OPTIONS = "${@bb.utils.contains('DISTRO_FEATURES', 'harden', 'ro', 'rw', d)}"

RDEPENDS_${PN} = "nvram"

do_install () {
    install -d ${D}${systemd_unitdir}/system

    sed 's:@OPTS@:${NVRAM_PLUS_PRODUCTION_MOUNT_OPTIONS}:g'  ${WORKDIR}/nvram-factory.mount.in > ${D}${systemd_unitdir}/system/nvram-factory.mount
    chmod 0644 ${D}${systemd_unitdir}/system/nvram-factory.mount
    install -m 0644 ${WORKDIR}/nvram-user.mount ${D}${systemd_unitdir}/system/
    
    install -d ${D}/nvram/factory
    install -d ${D}/nvram/user
}

PACKAGES =+ "${PN}-production"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "nvram-factory.mount nvram-user.mount"
FILES_${PN} += "${systemd_unitdir}/system/* /nvram/*"

PACKAGE_ARCH = "${MACHINE}"