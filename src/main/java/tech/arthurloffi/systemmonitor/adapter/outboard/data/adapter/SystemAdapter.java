package tech.arthurloffi.systemmonitor.adapter.outboard.data.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.arthurloffi.systemmonitor.domain.exception.SensorReadException;
import tech.arthurloffi.systemmonitor.domain.ports.SystemPort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class SystemAdapter implements SystemPort {

    private final CoretempParhResolver resolver;

    public SystemAdapter(CoretempParhResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public Float getTempCPU() {
        try {
            Path tempInputPath = resolver.resolverPackageTempPath();
            String raw = Files.readString(tempInputPath).trim();
            return Float.parseFloat(raw) / 1000f;
        } catch (IOException e) {
            throw new SensorReadException("Error reading the sensor", e);
        }
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
