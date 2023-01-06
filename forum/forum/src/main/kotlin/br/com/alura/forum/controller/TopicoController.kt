package br.com.alura.forum.controller

import br.com.alura.forum.dto.AtualizacaoTopicoForm
import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.dto.TopicoView
import br.com.alura.forum.service.TopicoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topicos")
// Abaixo realizamos a injeção de dependências do TopicoService para manipulá-lo adiante
class TopicoController(private val service: TopicoService) {

    @GetMapping
    fun listar(): List<TopicoView> {
        return service.listar()
    }

    @GetMapping("/{id}")
    // O PathVariable indica que os parâmetros devem ser informados na URI da requisição
    fun listarPorId(@PathVariable id: Long): TopicoView {
        return service.listarPorId(id)
    }

    @PostMapping
    // O RequestBody indica que os parâmetros devem ser informados no corpo da requisição
    // O Valid indica que o Spring deve utilizar as validações implementadas na classe
    fun cadastrar(
        @RequestBody @Valid form: NovoTopicoForm,
        // O uriBuilder é uma classe do spring que sabe criar URI's, evitando o uso de endereços localhost
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoView> {
        var topicoView = service.cadastrar(form)
        val uri = uriBuilder.path("topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)
    }

    @PutMapping
    fun atualizar(@RequestBody @Valid form: AtualizacaoTopicoForm): ResponseEntity<TopicoView>{
        val topicoView = service.atualizar(form)
        return ResponseEntity.ok(topicoView)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletar(@PathVariable id: Long) {
        service.deletar(id)
    }
}