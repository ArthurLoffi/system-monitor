package tech.arthurloffi.systemmonitor.adapter.outboard.data.adapter;

import org.springframework.stereotype.Component;
import tech.arthurloffi.systemmonitor.adapter.outboard.data.resolver.CoretempParhResolver;
import tech.arthurloffi.systemmonitor.adapter.outboard.data.resolver.CpuRpmPathResolver;
import tech.arthurloffi.systemmonitor.adapter.outboard.data.resolver.NvidiaSmiResolver;
import tech.arthurloffi.systemmonitor.domain.exception.SensorReadException;
import tech.arthurloffi.systemmonitor.domain.ports.SystemPort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class SystemAdapter implements SystemPort {

    private final CoretempParhResolver cpuResolver;
    private final NvidiaSmiResolver nvidiaSmiReader;
    private final CpuRpmPathResolver rmpResolver;

    public SystemAdapter(CoretempParhResolver cpuResolver, NvidiaSmiResolver nvidiaSmiReader, CpuRpmPathResolver rmpResolver) {
        this.cpuResolver = cpuResolver;
        this.rmpResolver = rmpResolver;
        this.nvidiaSmiReader = nvidiaSmiReader;
    }

    @Override
    public Float getTempCPU() {
        try {
            Path tempInputPath = cpuResolver.resolverPackageTempPath();
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
    public String getRpm() {
        try {
            Path rpmInputPath = rmpResolver.resolverPackageCpuRpm();
            return Files.readString(rpmInputPath).trim();
        } catch (IOException e) {
            throw new SensorReadException("Error reading the sensor", e);
        }
    }
}
