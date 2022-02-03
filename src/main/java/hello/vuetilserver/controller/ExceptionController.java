package hello.vuetilserver.controller;

import hello.vuetilserver.controller.exception.CAuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/entrypoint")
    public CommonResponse entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping("/accessdenied")
    public CommonResponse accessdeniedException() {
        throw new AccessDeniedException("");
    }
}
