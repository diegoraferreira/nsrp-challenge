package com.nsrp.challenge.controller.campanha;

import com.nsrp.challenge.domain.Campanha;
import com.nsrp.challenge.exceptionhandler.ApiError;
import com.nsrp.challenge.model.campanha.CampanhaModel;
import com.nsrp.challenge.service.campanha.CampanhaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nsrp.challenge.utils.NsrpChallengeDateUtils.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(tags = "API para cadastro de campanha", value = "Disponibiliza acesso para cadastro, consulta, alteração e exclusão de Campanha.")
@RestController
@RequestMapping("/campanha")
public class CampanhaController {

    @Autowired
    private CampanhaService campanhaService;

    @ApiOperation(value = "Cadastro de campanha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Campanha cadastrada com sucesso", response = CampanhaResponse.class),
            @ApiResponse(code = 404, message = "Campanha não encontrada", response = ApiError.class),
            @ApiResponse(code = 500, message = "Erro interno do servidor ao cadastrar a campanha", response = ApiError.class),
    })
    @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CampanhaResponse> create(@RequestBody CampanhaModel campanhaModel) {
        Campanha campanha = campanhaService.save(campanhaModel);
        String nome = campanha.getNome();
        Long idTimeCoracao = campanha.getTimeDoCoracao().getId();
        String dataVigencia = format(campanha.getDataInicioVigencia()) + " - " + format(campanha.getDataFimVigencia());
        CampanhaResponse campanhaResponse = new CampanhaResponse(nome, idTimeCoracao, dataVigencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(campanhaResponse);
    }

    @ApiOperation(value = "Atualização de campanha")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Campanha atualizada com sucesso", response = CampanhaModel.class),
            @ApiResponse(code = 404, message = "Campanha não encontrada", response = ApiError.class),
            @ApiResponse(code = 500, message = "Erro interno do servidor ao atualizar a campanha", response = ApiError.class),
    })
    @PutMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CampanhaModel> update(@RequestBody CampanhaModel campanhaModel) {
        this.campanhaService.update(campanhaModel);
        return ResponseEntity.ok(campanhaModel);
    }

    @ApiOperation(value = "Lista todas as campanhas vigentes, com a data fim da vigência após a data atual")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Campanhas encontradas", response = CampanhaModel.class),
            @ApiResponse(code = 500, message = "Erro interno do servidor ao buscar as campanhas", response = ApiError.class),
    })
    @GetMapping("/list")
    public List<CampanhaModel> list() {
        return this.campanhaService.findAllCampanhasVigentes();
    }

    @ApiOperation(value = "Exclui da campanha a partir do código da campanha informado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Campanhas excluida com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno do servidor ao execular a campanha", response = ApiError.class),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        this.campanhaService.delete(id);
        return ResponseEntity.ok().build();
    }
}