package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Configuracao {
    
    private double bestWay[];
    private double worstWay[];
    private double auxWay[];
    private int numeroVertices;
    private int numeroArestas;
    private int numeroFormigas;
    private Vertice vetorVertices[];
    private Aresta vetorArestas[];
    private Formiga vetorFormigas[];
    
    public void setAuxWay(double[] auxWay){
        this.auxWay=auxWay;
    }
    public double[] getAuxWay(){
        return this.auxWay;
    }
    
    public void setBestWay(double[] bestWay){
        this.bestWay=bestWay;
    }
    public double[] getBestWay(){
        return this.bestWay;
    }
    
    public void setWorstWay(double[] worstWay){
        this.worstWay=worstWay;
    }
    public double[] getWorstWay(){
        return this.worstWay;
    }
    
    public void setNumeroVertices(int numeroVertices){
        this.numeroVertices=numeroVertices;
    }
    public int getNumeroVertices(){
        return numeroVertices;
    }
    
    public void setNumeroArestas(int numeroArestas){
        this.numeroArestas=numeroArestas;
    }
    public int getNumeroArestas(){
        return numeroArestas;
    }
    
    public void setNumeroFormigas(int numeroFormigas){
        this.numeroFormigas=numeroFormigas;
    }
    public int getNumeroFormigas(){
        return numeroFormigas;
    }
    
    public Vertice[] getVetorVertices(){
        return this.vetorVertices;
    }
    
    public void iniciarPrograma(){
        
// LEITURA DO ARQUIVO ----------------------------------------------------------

/*

O ARQUIVO DEVE CONTER:

numeroVertices,numeroArestas,numeroFormigas
origem,destino,custo
origem,destino,custo
origem,destino,custo
origem,destino,custo
origem,destino,custo
...

*/

        Scanner entrada=null;
        String arquivo=null;
        System.out.print("APERTE ENTER.");
        Scanner aux = new Scanner(System.in);
        int i = 0;
        
        try{    
            entrada = new Scanner(new File("src\\main\\grafo2.txt\\"+aux.nextLine()));
            entrada.useDelimiter(",|" + System.getProperty("line.separator"));
        }
        catch(FileNotFoundException e){
            System.out.println("Arquivo nao encontrado.");
        }

        setNumeroVertices(Integer.parseInt(entrada.next()));
        setNumeroArestas(Integer.parseInt(entrada.next()));
        setNumeroFormigas(Integer.parseInt(entrada.next()));
        
        int [][] grafo = new int[getNumeroArestas()][3]; // [origem,destino,peso]
        
        int a;
        while(entrada.hasNext()){            
            a=Integer.parseInt(entrada.next());
            grafo[i][0]=a;
            
            a=Integer.parseInt(entrada.next());
            grafo[i][1]=a;
            
            a=Integer.parseInt(entrada.next());
            grafo[i][2]=a;
            
            i++;
        }
        entrada.close();
        
        /*
        for(i=0; i<8020; i++){
            for(int j=0; j<3; j++){
                System.out.print(grafo[i][j]+" ");
            }
            System.out.println();
        }
        */
        
        /*
        for(i=0; i<190; i++){
            for(int j=0; j<3; j++){
                System.out.print(grafo[i][j]+" ");
            }
            System.out.println();
        }
        */
        
//------------------------------------------------------------------------------

        bestWay = new double[numeroVertices+1];
        worstWay = new double[numeroVertices+1];
        auxWay = new double[numeroVertices+1];
        bestWay[0]=999999;
        worstWay[0]=0;
               
        // CRIA O VETOR DE ARESTAS.
        vetorArestas = new Aresta[numeroArestas]; // [origem,destino,custo]
        for(i=0; i<numeroArestas; i++){
            vetorArestas[i] = new Aresta();
            vetorArestas[i].setOrigem(grafo[i][0]);
            vetorArestas[i].setDestino(grafo[i][1]);
            vetorArestas[i].setPeso(grafo[i][2]);
            vetorArestas[i].setCusto(grafo[i][2]); 
            vetorArestas[i].setFeromonio(0.01);
        }
        
        // CRIA O VETOR DE VÉRTICES.
        vetorVertices = new Vertice[numeroVertices];
        for(i=0; i<numeroVertices; i++){
            vetorVertices[i] = new Vertice(grafo, vetorArestas, numeroVertices, i+1); 
        }
        // TESTE.
        //System.out.println("Vértices:");
        //for(i=0; i<numeroVertices; i++){
            //System.out.println(vetorVertices[i].getNumero());
        //}
        
        // CRIA O VETOR DE FORMIGAS.
        vetorFormigas = new Formiga[numeroFormigas];
        for(i=0; i<numeroFormigas; i++){            
            vetorFormigas[i] = new Formiga(numeroVertices, i);
        }
        
        // ENVIA AS FORMIGAS
        for(int c=0; c<3000; c++){
            for(int b=0; b<numeroFormigas; b++){                
                setAuxWay(vetorFormigas[b].andar(vetorVertices, vetorArestas, grafo)); // MANDA AS FORMIGAS ANDAREM b VEZES. 

                // VERIFICA SE É MELHOR OU PIOR CAMINHO.
                if(auxWay[0]>worstWay[0]){
                    System.arraycopy(auxWay, 0, worstWay, 0, auxWay.length); // COPIA A VARIÁVEL.
                }
                if(auxWay[0]<bestWay[0]){
                    System.arraycopy(auxWay, 0, bestWay, 0, auxWay.length); // COPIA A VARIÁVEL.
                }                   
                // ZERA O VETOR auxWay.
                for(int q=0; q<20; q++){
                    auxWay[q]=0;
                }                                
            }
        }          
    }    
    
    public void mostraResultados(){
        System.out.println();
        System.out.println("======= MELHOR CAMINHO =======");
        System.out.print("CUSTO: "+(int)bestWay[0]+"\n");
        System.out.print("CAMINHO: ");
        for(int v=1; v<numeroVertices; v++){
            if(bestWay[v]!=0){
                System.out.print((int)bestWay[v]+", ");
            }            
        }
        System.out.println("\n");
        System.out.println("======= PIOR CAMINHO =======");
        System.out.print("CUSTO: "+(int)worstWay[0]+"\n");
        System.out.print("CAMINHO: ");
        for(int v=1; v<numeroVertices; v++){
            if(worstWay[v]!=0){
                System.out.print((int)worstWay[v]+", ");
            }  
        }
        System.out.println("\n");
    }    
}
