package com.example.project;

public class Item {
    private int item;
    private Produto produto;
    private int quantidade;

    private String brk = System.lineSeparator();

    public Item(int item, Produto produto, int quantidade) {
        this.item = item;
        this.produto = produto;
        this.quantidade = quantidade;
    }

	public Produto getProduto() {
        return this.produto;
    }

    public int getItem() {
        return this.item;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public double valorDoItem() {
        return this.quantidade * this.produto.getValorUnitario();
    }

    public String dadosDoItem() {
        return String.format("%d %d %s %d %s %.2f %s %.2f" + brk, 
        this.getItem(), produto.getCodigo(), produto.getDescricao(),
        this.getQuantidade(), produto.getUnidade(), 
        produto.getValorUnitario(), produto.getSubTribu(),
        this.valorDoItem());
    }

}
