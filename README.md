# System Monitor

A Java + Spring Boot REST API for real-time hardware monitoring, built with hexagonal architecture.

## Endpoints

### `GET /system/cpu-temp`
Returns current CPU package temperature (°C), read from the `coretemp` hwmon driver.

### `GET /system/gpu-temp`
Returns current GPU temperature (°C), read via `nvidia-smi`.

### `GET /system/rpm`
Returns current CPU fan speed (RPM), read from the `fan1_input` file exposed by the ASUS hwmon driver.

## Requirements

- Java 25+
- Maven
- Linux with `hwmon` support (`coretemp` driver for CPU temp, ASUS WMI driver for fan RPM)
- `nvidia-smi` in `PATH` for GPU temperature

> Tested on an ASUS Vivobook 16X running Fedora Linux.

## Running

```bash
mvn spring-boot:run
```

Runs on port `8080` by default.

## Error handling

Sensor read failures throw `SensorReadException`, propagated up to the HTTP layer.
