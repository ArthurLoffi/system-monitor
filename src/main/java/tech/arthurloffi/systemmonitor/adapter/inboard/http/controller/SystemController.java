package tech.arthurloffi.systemmonitor.adapter.inboard.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.arthurloffi.systemmonitor.usecase.SystemUseCase;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController {

    private final SystemUseCase systemUseCase;

    @RequestMapping("/cpu-temp")
    public Float getTempCPU() {
        return systemUseCase.getTempCPU();
    }

    @RequestMapping("/gpu-temp")
    public Float getTempGPU() {
        return systemUseCase.getTempGPU();
    }

    @RequestMapping("/rpm")
    public Integer getRpm() {
        return systemUseCase.getRpm();
    }
}
