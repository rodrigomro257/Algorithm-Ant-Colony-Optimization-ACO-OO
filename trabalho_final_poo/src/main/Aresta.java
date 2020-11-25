package main;

public class Aresta{
    
    private double custo; // n(e)==1/peso.
    private double peso;
    private double feromonio; // T
    private int origem;
    private int destino;
    
    public void setPeso(double peso){
        this.peso=peso;
    }
    public double getPeso(){
        return this.peso;
    }
    
    public void setCusto(double peso){
        this.custo=1/peso; // PARA CÁLCULO DO n(e).
    }
    public double getCusto(){
        return this.custo;
    }
    
    public void setFeromonio(double feromonio){
        this.feromonio=feromonio;
    }
    public double getFeromonio(){
        return this.feromonio;
    }

    public void setOrigem(int origem){
        this.origem=origem;
    }
    public int getOrigem(){
        return origem;
    }
    
    public void setDestino(int destino){
        this.destino=destino;
    }
    public int getDestino(){
        return destino;
    }
    
}
