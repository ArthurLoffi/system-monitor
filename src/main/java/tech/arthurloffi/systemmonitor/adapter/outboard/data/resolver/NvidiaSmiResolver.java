package tech.arthurloffi.systemmonitor.adapter.outboard.data.resolver;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class NvidiaSmiResolver {
    public Float readGpuTemp() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "nvidia-smi",
                "--query-gpu=temperature.gpu",
                "--format=csv,noheader,nounits"
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();

        String output;
        try (InputStream is = process.getInputStream()) {
            output = new String(is.readAllBytes()).trim();
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("nvidia smi failed with exit code " + exitCode + ", output: " + output);
        }

        return Float.parseFloat(output);
    }
}
