package com.nsrp.challenge.controller.time;

import com.nsrp.challenge.domain.Time;
import com.nsrp.challenge.exceptionhandler.ApiError;
import com.nsrp.challenge.model.time.TimeModel;
import com.nsrp.challenge.service.TimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(tags = "API para consulta de time", value = "Disponibiliza acesso para consulta de time.")
@RestController
@RequestMapping("/time")
public class TimeController {

    @Autowired
    private TimeService timeService;

    @ApiOperation(value = "Retorna o time a partir do nome informado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Time encontrado"),
            @ApiResponse(code = 500, message = "Erro interno do servidor ao buscar o time", response = ApiError.class),
    })
    @GetMapping(value = "list/{nome}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TimeModel> findByNome(@PathVariable("nome") String nome) {
        Optional<Time> timeOptional = this.timeService.findByNome(nome);
        if (timeOptional.isPresent()) {
            Time time = timeOptional.get();
            TimeModel timeModel = new TimeModel(time.getId(), time.getNome());
            return ResponseEntity.ok().body(timeModel);
        }
        return ResponseEntity.notFound().build();
    }
}