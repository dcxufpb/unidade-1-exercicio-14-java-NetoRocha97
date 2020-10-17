package com.example.project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private Loja loja;
    private String datahora;
    private int ccf;
    private int coo;
    private List<Item> itens = new ArrayList<Item>();

    public Venda(Loja loja, String DATAHORA, String CCF_VENDA, String COO_VENDA) {
	}

	public Loja getLoja() {
        return this.loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public String getDataHora(){
        return this.datahora;
    }

    public void setDataHora(String datahora) {
        this.datahora = datahora;
    }

    public int getCcf() {
        return this.ccf;
    }

    public void setCcf(int ccf) {
        this.ccf = ccf;
    }

    public int getCoo() {
        return this.coo;
    }

    public void setCoo(int coo){
        this.coo = coo;
    }

    public List<Item> getItens() {
        return this.itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public static String dataAtual(){
        //Data e hora atuais
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = formatterData.format(now);
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormatada = formatterHora.format(now);

        return dataFormatada + " " + horaFormatada + "V";
    }

    public void verificarCampoObrigatorioVenda(){
        if(this.loja == null) {
            throw new RuntimeException("O campo loja deve está preenchida");
        }
        if(this.datahora == null){
            throw new RuntimeException("O campo hora e data são obrigatórios");
        }

        if(this.ccf == 0) {
            throw new RuntimeException("O campo CCF é obrigatório");
        }

        if(this.coo == 0) {
            throw new RuntimeException("O campo COO é obrigatório");
        }
    }

    public String brk = System.lineSeparator();

    public void verificaItem(int item, Produto produto, int quantidade) {

        if(quantidade <= 0) {
            throw new RuntimeException("Venda sem itens");
        }

        for(Item itm: this.itens) {
            
            if(itm.getItem() != item && itm.getProduto().getCodigo() == produto.getCodigo()) {
                throw new RuntimeException("Venda com itens diferentes apontando para o mesmo produto");
            }
            
            if(itm.getItem() <= 0) {
                throw new RuntimeException("Item de Venda com quantidade zero ou negativa - não pode ser adicionado na venda");
            }
        }

        if(produto.getValorUnitario() <= 0) {
            throw new RuntimeException("Produto com valor unitário zero ou negativo - item não pode ser adicionado na venda com produto nesse estado");
        }
    }

    public void adicionarItem(int item, Produto produto, int quantidade) {
        verificaItem(item, produto, quantidade);
        Item itemDaVenda = new Item(item, produto, quantidade);
        this.itens.add(itemDaVenda);
    }

    public String dadosDoItem(){
        StringBuilder dados = new StringBuilder("ITEM CODIGO DESCRICAO QTD UN VL UNIT(R$) ST VL ITEM(R$)" + brk);
        for(Item itm: this.itens) {
            dados.append(itm.dadosDoItem());
        }
        return dados.toString();
    }

    public double calcularValorTotal() {
        double total = 0;
        for(Item itm: this.itens) {
            total += itm.valorDoItem();
        }
        return total;
    }

    public String dadosVenda() {

        String _ccf = "CCF:" + this.ccf + " ";
        String _coo = "COO: " + this.coo;

        return dataAtual()+ _ccf + _coo;
        
    }

    public String imprimeCupom() {
        this.verificarCampoObrigatorioVenda();
        String dadosLoja = this.loja.dadosLoja();
        String dadosVenda = this.dadosVenda();
        String dadosDoItem = this.dadosDoItem();
        double totalCalculado = this.calcularValorTotal();

        return String.format("%s---------------------------------------------------" + brk + "%s" + brk + "CUPOM FICAL" + brk +
        "%s" + "---------------------------------------------------" + brk + "TOTAL R$ %.2f" + brk ,
        dadosLoja, dadosVenda, dadosDoItem,totalCalculado);
    }

}