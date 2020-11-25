package main;

public class Formiga {
    
    private final int numero;
    private double[] caminho; // POSIÇÃO 0 ARMAZENA O CUSTO TOTAL DO CAMINHO.
    private Vertice ondeEstou;
    private Vertice destino;
    
    Formiga(int tamanho, int numero){ // tamanho É IGUAL AO numeroVertices+1, PARA ARMAZENAR O CAMINHO E O CUSTO.
        this.numero=numero;
        caminho=new double[tamanho+1]; // DEFINE O CAMINHO POR ONDE A FORMIGA PASSOU.
        caminho[0]=0;
        caminho[1]=1;
    }

    public double[] andar(Vertice vetorVertices[], Aresta vetorArestas[], int grafo[][]){
        // vetorVertices É PARA A Formiga SE LOCALIZAR NO GRAFO.
        // vetorArestas[] É PARA O Vertice ATUALIZAR O FEROMONIO E PEGAR O CUSTO.
        // matriz[][] É PARA O Vertice CALCULAR A PROBABILIDADE.        
        
        ondeEstou=vetorVertices[0]; // A PRIMEIRA POSIÇÃO DO VETOR É ONDE A FORMIGA COMEÇA A CAMINHAR.
        destino=vetorVertices[vetorVertices.length-1]; // A ÚLTIMA POSIÇÃO DO VETOR É O DESTINO.
        caminho[0]=0;
        caminho[1]=1;
        int posicao=2;
        
        // ZERA O VETOR DE CAMINHOS.
        for(int i=2; i<vetorVertices.length; i++){
            caminho[i]=0;
        }
        
        while(ondeEstou.getNumero() != destino.getNumero()){ // CAMINHA ATÉ CHEGAR NO DESTINO
            
            // SE ALGUM ERRO OCORRER A FORMIGA RECOMEÇA A ANDAR DO INÍCIO.
            // O ERRO COMUM ERA A FORMIGA ESTRAPOLAR O LIMITE DO VETOR caminho.
            try{                
                caminho=ondeEstou.caminhar(caminho, posicao, vetorVertices, vetorArestas, grafo);    
                
                for(int v=0; v<vetorVertices.length; v++){
                    if(caminho[posicao]==vetorVertices[v].getNumero()){                       
                        ondeEstou=vetorVertices[v];
                     }
                }                
                // SE TIVER POSIÇÃO REPETIRA DÁ UM ERRO E ENTRA NO catch.
                for(int a=posicao; a>0; a--){
                    if(caminho[posicao]==caminho[a] && posicao!=a){
                        caminho[vetorVertices.length+1]=0; // ERRO PROPOSITAL.
                    }
                }                  
                posicao++;              
            }
            catch(ArrayIndexOutOfBoundsException e){
                andar(vetorVertices, vetorArestas, grafo);                
            }                      
        }   
        
        return caminho; // RETORNA O VETOR PARA A Configuracao CLASSIFICAR COMO PIOR OU MELHOR CAMINHO.
    }
}
