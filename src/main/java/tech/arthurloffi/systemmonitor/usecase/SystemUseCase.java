package tech.arthurloffi.systemmonitor.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.arthurloffi.systemmonitor.domain.dto.SystemDto;
import tech.arthurloffi.systemmonitor.domain.ports.SystemPort;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemUseCase {

    private final SystemPort systemPort;

    public Float getTempCPU() {
        log.info("[execute] Getting CPU Temperature");

        return systemPort.getTempCPU();
    }

    public Float getTempGPU() {
        log.info("[execute] Getting GPU Temperature");

        return systemPort.getTempGPU();
    }

    public Integer getRpm() {
        log.info("[execute] Getting RPM from fans");

        return systemPort.getRpm();
    }
}
