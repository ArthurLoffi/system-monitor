package tech.arthurloffi.systemmonitor.adapter.outboard.data.resolver;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class CoretempParhResolver {

    private static final Path HWMON_BASE = Path.of("/sys/class/hwmon");

    public Path resolverPackageTempPath() throws IOException {
        try (Stream<Path> hwmons = Files.list(HWMON_BASE)) {
            for (Path hwmon : hwmons.toList()) {
                Path namePath = hwmon.resolve("name");
                if (!Files.exists(namePath)) continue;

                String driverName = Files.readString(namePath).trim();
                if (!driverName.equals("coretemp")) continue;

                return findPackageLabelInput(hwmon)
                        .orElseThrow(() -> new IOException("Package id not found in " + hwmon));
            }
        }
        throw new IOException("Driver coretemp not found in any hwmon");
    }

    public static Optional<Path> findPackageLabelInput(Path hwmonDir) throws IOException {
        try (Stream<Path> files = Files.list(hwmonDir)) {
            for (Path labelFile : files.filter(p -> p.getFileName().toString().matches("temp\\d+_label")).toList()) {
                String label = Files.readString(labelFile).trim();
                if (label.startsWith("Package id")) {
                    String inputFileName = labelFile.getFileName().toString().replace("_label", "_input");
                    return Optional.of(hwmonDir.resolve(inputFileName));
                }
            }
        }
        return Optional.empty();
    }
}
