package tech.arthurloffi.systemmonitor.adapter.outboard.data.adapter;

import org.springframework.stereotype.Component;
import tech.arthurloffi.systemmonitor.adapter.outboard.data.resolver.CoretempParhResolver;
import tech.arthurloffi.systemmonitor.adapter.outboard.data.resolver.NvidiaSmiResolver;
import tech.arthurloffi.systemmonitor.domain.exception.SensorReadException;
import tech.arthurloffi.systemmonitor.domain.ports.SystemPort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class SystemAdapter implements SystemPort {

    private final CoretempParhResolver resolver;
    private final NvidiaSmiResolver nvidiaSmiReader;

    public SystemAdapter(CoretempParhResolver resolver, NvidiaSmiResolver nvidiaSmiReader) {
        this.resolver = resolver;
        this.nvidiaSmiReader = nvidiaSmiReader;
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
        try {
            return nvidiaSmiReader.readGpuTemp();
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new SensorReadException("Error to read GPU temp: ", e);
        }
    }

    @Override
    public Integer getRpm() {
        return 4000;
    }
}
