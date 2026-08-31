    package tech.arthurloffi.systemmonitor.adapter.outboard.data.resolver;

    import org.springframework.stereotype.Component;

    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.util.stream.Stream;

    @Component
    public class CpuRpmPathResolver {
        private static final Path HWMON_BASE = Path.of("/sys/devices/platform/asus-nb-wmi/hwmon");

        public Path resolverPackageCpuRpm() throws IOException {
            try (Stream<Path> hwmons = Files.list(HWMON_BASE)) {
                for (Path hwmon : hwmons.toList()) {
                    Path fanInput = hwmon.resolve("fan1_input");
                    if (Files.exists(fanInput)) {
                        return fanInput;
                    }
                }
            }
            throw new IOException("fan1_input not found in any hwmon under " + HWMON_BASE);
        }
    }
