package com.example.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestVenda {

    private void verificarCampoObrigatorio(String mensagemEsperada, Venda venda) {
        try {
            venda.dadosVenda();
        } catch (RuntimeException e) {
            assertEquals(mensagemEsperada, e.getMessage());
        }
    }

    private String NOME_LOJA = "Loja 1";
    private String LOGRADOURO = "Log 1";
    private int NUMERO = 10;
    private String COMPLEMENTO = "C1";
    private String BAIRRO = "Bai 1";
    private String MUNICIPIO = "Mun 1";
    private String ESTADO = "E1";
    private String CEP = "11111-111";
    private String TELEFONE = "(11) 1111-1111";
    private String OBSERVACAO = "Obs 1";
    private String CNPJ = "11.111.111/1111-11";
    private String INSCRICAO_ESTADUAL = "123456789";
    private String DATAHORA = Venda.dataAtual();
    private String CCF_VENDA = "021784";
    private String COO_VENDA = "035804";

    private Produto produto = new Produto(100, "banana", "cx", 7.45, "ST");
    private Produto produto_sem_valor = new Produto(102, "leite", "l", 0, "");

    public String brk = System.lineSeparator();

    Endereco endereco = new Endereco(LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, MUNICIPIO, ESTADO, CEP);
    Loja loja = new Loja(NOME_LOJA, endereco, TELEFONE, OBSERVACAO, CNPJ, INSCRICAO_ESTADUAL);

    @Test
    public void validarLoja() {
        Venda lojaNulo = new Venda(null, DATAHORA, CCF_VENDA, COO_VENDA);
        verificarCampoObrigatorio("O campo loja deve está preenchida", lojaNulo);
    }

    @Test
    public void validarDataHora() {
        Venda datahoraNulos = new Venda(loja, null, CCF_VENDA, COO_VENDA);
        verificarCampoObrigatorio("O campo hora e data são obrigatórios", datahoraNulos);

        Venda datahoraVazios = new Venda(loja, "", CCF_VENDA, COO_VENDA);
        verificarCampoObrigatorio("O campo hora e data são obrigatórios", datahoraVazios);
    }

    @Test
    public void validarCcf() {
        Venda ccfNulo = new Venda(loja, DATAHORA, null, COO_VENDA);
        verificarCampoObrigatorio("O campo CCF é obrigatório", ccfNulo);

        Venda ccfVazio = new Venda(loja, DATAHORA, "", COO_VENDA);
        verificarCampoObrigatorio("O campo CCF é obrigatório", ccfVazio);
    }

    @Test
    public void validarCoo() {
        Venda cooNulo = new Venda(loja, DATAHORA, CCF_VENDA, null);
        verificarCampoObrigatorio("O campo CCF é obrigatório", cooNulo);

        Venda cooVazio = new Venda(loja, DATAHORA, CCF_VENDA, "");
        verificarCampoObrigatorio("O campo CCF é obrigatório", cooVazio);
    }
    
    @Test
    public void teste_sem_itens() {
        Venda venda = loja.vender(DATAHORA, CCF_VENDA, COO_VENDA);
        verificarCampoObrigatorio("Venda sem itens", venda);
    }

    @Test
    public void teste_mesmo_produto() {
        try{
            Venda venda = loja.vender(DATAHORA, CCF_VENDA, COO_VENDA);
            venda.adicionarItem(1, produto, 3);
            venda.adicionarItem(2, produto, 5);
        } catch(RuntimeException e) {
            assertEquals("Venda com itens diferentes apontando para o mesmo produto", e.getMessage());
        }
    }

    @Test
    public void teste__quantidade_nula_negativa() {
        try{
            Venda venda = loja.vender(DATAHORA, CCF_VENDA, COO_VENDA);
            venda.adicionarItem(0, produto, 3);
            //
        } catch(RuntimeException e) {
            assertEquals("Item de Venda com quantidade zero ou negativa - não pode ser adicionado na venda", e.getMessage());
        }
    }

    @Test
    public void teste_valor_unit_nulo_negativo() {
        try{
            Venda venda = loja.vender(DATAHORA, CCF_VENDA, COO_VENDA);
            venda.adicionarItem(1, produto, 3);
            venda.adicionarItem(2, produto_sem_valor, 5);
        } catch(RuntimeException e) {
            assertEquals("Produto com valor unitário zero ou negativo - item não pode ser adicionado na venda com produto nesse estado", e.getMessage());
        }
    }
        
}