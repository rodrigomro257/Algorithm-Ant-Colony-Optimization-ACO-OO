package main;

public class Vertice{

    private final int numero;  // NÚMERO DO VÉRTICE.
    private final double taxaEvaporacao=0.01; // RO.
    private int posicao;       // PARA CONTROLAR POSIÇÕES DENTRO DE VETORES USADOS NA CLASSE.
    private Aresta arestas[];  // CADA VÉRTICE TEM UM NÚMERO DE ARESTAS LIGADOS A ELE.
    private double[][] matriz; // LINHA 0 GUARDA O DESTINO, 
                               // LINHA 1 GUARDA O CUSTO, 
                               // LINHA 2 GUARDA O FEROMÔNIO,
                               // LINHA 3 GUARDA A PROBABILIDADE.
    
    Vertice(int grafo[][], Aresta vetorArestas[], int tamanho, int numero){
        matriz = new double[4][tamanho-1]; // HÁ POSSIBILIDADE DE NO MÁXIMO n-1 CAMINHOS EM CADA VÉRTICE.
        this.numero=numero; // COLOCA O NÚMERO DO VÉRTICE.        
        arestas=new Aresta[tamanho-1]; // PODE TER, NO MÁXIMO, n-1 ARESTAS.
        
        posicao=0;
        for(int i=0; i<vetorArestas.length; i++){
            if(grafo[i][0] == this.numero){                             
                matriz[0][posicao] = grafo[i][1]; // DESTINO.
                matriz[1][posicao] = vetorArestas[i].getCusto();
                matriz[2][posicao] = vetorArestas[i].getFeromonio();
                arestas[posicao]=new Aresta();
                arestas[posicao]=vetorArestas[i];
                posicao++;     
            }
        }
    }
    
    public int getNumero(){
        return this.numero;
    }
    
    private double[][] calculaProbabilidade(double matriz[][], int quantidade){
        double somatorio=0;
        
        // FAZ O SOMATÓRIO.
        for(int i=0; i<quantidade; i++){
            somatorio += matriz[1][i]*arestas[i].getFeromonio();
            //System.out.println(somatorio);
        }
        
        // DEFINE OS VALORES DE PROBABILIDADE.
        for(int i=0; i<quantidade; i++){
            //System.out.println(quantidade);
            if(i==0){
                matriz[3][i] = ((matriz[1][i]*arestas[i].getFeromonio())/somatorio);
                //System.out.println(matriz[3][i]);
            }
            else{
                matriz[3][i] = ((matriz[1][i]*arestas[i].getFeromonio())/somatorio)+matriz[3][i-1];
                //System.out.println(matriz[3][i]);
            }           
        }
        
        return matriz;
    }
    
    private void atualizaFeromonio(double matriz[][], double caminho[]){        
        int lugarMatriz, lugarCaminho;        
        for(lugarMatriz=0; lugarMatriz<caminho.length; lugarMatriz++){            
            for(lugarCaminho=1; lugarCaminho<=caminho.length; lugarCaminho++){                
                if(matriz[0][lugarMatriz]==caminho[lugarCaminho]){
                    arestas[lugarMatriz].setFeromonio(((1-taxaEvaporacao)*arestas[lugarMatriz].getFeromonio())+matriz[1][lugarMatriz]);
                }
                else{
                    arestas[lugarMatriz].setFeromonio(((1-taxaEvaporacao)*arestas[lugarMatriz].getFeromonio()));
                }
            }
        }        
    }  

    public double[] caminhar(double caminho[], int lugar, Vertice vetorVertices[], Aresta vetorArestas[], int grafo[][]){

        // A PROBABILIDADE DE CADA CAMINHO É CALCULADA.
        matriz = calculaProbabilidade(matriz, posicao); 
        
        double rand = Math.random();       
        int destino=0;
        boolean achei = false; // SE ACHOU O CAMINHO.
 
        while(achei!=true){
            if(destino==0){
                if(rand>=0 && rand<matriz[3][destino]){ 
                    caminho[0] += 1/(matriz[1][destino]); // SOMA O PESO. 1/x PARA USAR O PESO, E NÃO O CUSTO.
                    caminho[lugar] = matriz[0][destino];
                    achei=true;
                }
                else{
                    destino++;
                }                
            }
            else{
                if(rand>=matriz[3][destino-1] && rand<=matriz[3][destino]){ 
                    caminho[0] += 1/(matriz[1][destino]); // SOMA O PESO. 1/x PARA USAR O PESO, E NÃO O CUSTO.
                    caminho[lugar] = matriz[0][destino]; 
                    achei=true;                 
                }
                else{
                    destino++;
                }
            }
        } 
        
        if(numero==20){
           atualizaFeromonio(matriz, caminho);
        }
        
        return caminho;    
    }    
}
