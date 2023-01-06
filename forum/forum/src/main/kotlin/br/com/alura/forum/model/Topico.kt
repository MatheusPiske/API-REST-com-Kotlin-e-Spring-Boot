package br.com.alura.forum.model

import java.time.LocalDateTime

// As classes de domínio irão representar os recursos dentro da API

data class Topico (

    // O var permite mudanças
    var id: Long? = null,
    val titulo: String,
    val mensagem: String,
    val dataCriacao:LocalDateTime= LocalDateTime.now(),
    val curso: Curso,
    val autor: Usuario,
    val status: StatusTopico = StatusTopico.NAO_RESPONDIDO,
    val respostas: List<Respostas> = ArrayList()
)