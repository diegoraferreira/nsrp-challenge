package com.nsrp.challenge.controller.campanha;

import com.nsrp.challenge.domain.Campanha;
import com.nsrp.challenge.model.campanha.CampanhaModel;
import com.nsrp.challenge.service.campanha.CampanhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nsrp.challenge.utils.NsrpChallengeDateUtils.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/campanha")
public class CampanhaController {

    @Autowired
    private CampanhaService campanhaService;

    @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CampanhaResponse> create(@RequestBody CampanhaModel campanhaModel) {
        Campanha campanha = campanhaService.save(campanhaModel);
        String nome = campanha.getNome();
        Long idTimeCoracao = campanha.getTimeDoCoracao().getId();
        String dataVigencia = format(campanha.getDataInicioVigencia()) + " - " + format(campanha.getDataFimVigencia());
        CampanhaResponse campanhaResponse = new CampanhaResponse(nome, idTimeCoracao, dataVigencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(campanhaResponse);
    }

    @PutMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CampanhaModel> update(@RequestBody CampanhaModel campanhaModel) {
        this.campanhaService.update(campanhaModel);
        return ResponseEntity.ok(campanhaModel);
    }

    @GetMapping("/list")
    public List<CampanhaModel> list() {
        return this.campanhaService.findAllCampanhasVigentes();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        this.campanhaService.delete(id);
        return ResponseEntity.ok().build();
    }
}