package br.inatel.pos.dm111.vfr.api.promo.controller;

import br.inatel.pos.dm111.vfr.api.core.ApiException;
import br.inatel.pos.dm111.vfr.api.core.AppError;
import br.inatel.pos.dm111.vfr.api.promo.PromoRequest;
import br.inatel.pos.dm111.vfr.api.promo.PromoResponse;
import br.inatel.pos.dm111.vfr.api.promo.service.PromoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/valefood/promos")
public class PromoController {

    private static final Logger log = LoggerFactory.getLogger(PromoController.class);

    private final PromoRequestValidator validator;
    private final PromoService service;

    public PromoController(PromoRequestValidator validator, PromoService service) {
        this.validator = validator;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PromoResponse>> getAllPromos() throws ApiException {
        log.debug("Received request to list all promos");

        var response = service.searchPromos();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(value = "/{promoId}")
    public ResponseEntity<PromoResponse> getUserById(@PathVariable("promoId") String id)
            throws ApiException {
        log.debug("Received request to list an promo by id: {}", id);

        var response = service.searchPromo(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<PromoResponse> postPromo(@RequestBody PromoRequest request,
                                                             BindingResult bindingResult)
            throws ApiException {
        log.debug("Received request to create a new user...");

        validateRequest(request, bindingResult);
        var response = service.createPromo(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping(value = "/{promoId}")
    public ResponseEntity<PromoResponse> putUser(@RequestBody PromoRequest request,
                                                      @PathVariable("promoId") String promoId,
                                                      BindingResult bindingResult)
            throws ApiException {
        log.debug("Received request to update an user...");

        validateRequest(request, bindingResult);

        var response = service.updatePromo(request, promoId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping(value = "/{promoId}")
    public ResponseEntity<List<PromoResponse>> deletePromo(@PathVariable("promoId") String id)
            throws ApiException {
        log.debug("Received request to delete an promo: id {}", id);

        service.removePromo(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    private void validateRequest(PromoRequest request, BindingResult bindingResult)
            throws ApiException {
        ValidationUtils.invokeValidator(validator, request, bindingResult);

        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(fe -> new AppError(fe.getCode(), fe.getDefaultMessage()))
                    .toList();
            throw new ApiException(HttpStatus.BAD_REQUEST, errors);
        }
    }
}