package tech.arthurloffi.systemmonitor.domain.ports;

import tech.arthurloffi.systemmonitor.domain.dto.SystemDto;

public interface SystemPort {

    Float getTempCPU();

    Float getTempGPU();

    Integer getRpm();

}