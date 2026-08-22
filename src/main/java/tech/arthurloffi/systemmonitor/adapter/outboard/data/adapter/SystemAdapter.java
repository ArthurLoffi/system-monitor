package tech.arthurloffi.systemmonitor.adapter.outboard.data.adapter;

import org.springframework.stereotype.Component;
import tech.arthurloffi.systemmonitor.domain.ports.SystemPort;

@Component
public class SystemAdapter implements SystemPort {

    @Override
    public Float getTempCPU() {
        return 45.0f;
    };

    @Override
    public Float getTempGPU() {
        return 30.0f;
    }

    @Override
    public Integer getRpm() {
        return 4000;
    }
}
