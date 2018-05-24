import java.util.*;

import static java.lang.Math.abs;

class Program{

    static final int SIZE_X=500;
    static final int SIZE_Y=500;

    Random r=new Random();
    Scanner s=new Scanner(System.in);

    int currentX=10;
    int currentY=10;

    static class Thing{
        int x;
        int y;

        Thing(int x, int y){
            this.x=x;
            this.y=y;
        }

        double fitness(){
//            double t=1/(1+Math.exp(-((double)(abs(100-x)+abs(100-y)))));
            double t=(double)(abs(100-x)+abs(100-y));
            t=-t/2 + 100;
            return t;
        }

}

    Thing[] sort(Thing[] a, Thing[] b){
        int i=0, j=0, k=0;
        Thing[] finalArr=new Thing[a.length+b.length];
        while(i<a.length && j<b.length){
            if(a[i].fitness()>b[j].fitness()){
                finalArr[k]=a[i];
                i++;
            }else{
                finalArr[k]=b[j];
                j++;
            }
            k++;
        }
        while(i<a.length){
            finalArr[k]=a[i];
            k++;
            i++;
        }
        while(j<b.length){
            finalArr[k]=b[j];
            k++;
            j++;
        }
        return finalArr;
    }

    Thing[] mergeSort(Thing[] array, int x, int y){
        if(y==x){
            Thing[] a=new Thing[1];
            a[0]=array[x];
            return a;
        }
        int mid=(x+y)/2;
        return sort(mergeSort(array, x, mid), mergeSort(array, mid+1, y));
    }

    Thing[] sortBasedOnFitness(Thing[] things){
        return mergeSort(things, 0, things.length-1);
    }

    void geneticAlgo(){
        Thing[] things=new Thing[100];
        for(int i=0;i<100;i++){
            int x=r.nextInt(50);
            int y=r.nextInt(50);
            things[i]=new Thing(x, y);
        }

        int generationCount=100;
        for(int i=0;i<generationCount;i++){
            Thing[] fitnessSortedThings=sortBasedOnFitness(things);
            System.out.print("\nGeneration "+i+" finished\n");
            System.out.print("Max fitness achieved = "+fitnessSortedThings[0].fitness()+" at "+fitnessSortedThings[0].x+", "+fitnessSortedThings[0].y+"\n");
            Thing bestThing=fitnessSortedThings[0];
            currentX=bestThing.x;
            currentY=bestThing.y;

            //if(mousePressed){
            //  //ellipse();
            //}
//            try{
//                Thread.sleep(500);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
            things=getNewPopulation(fitnessSortedThings);
        }
    }

    Thing[] getNewPopulation(Thing[] oldPopulation){
        Thing[] newPopulation=new Thing[oldPopulation.length];
        int i=0;
        int j=0;
        while(i<newPopulation.length){
            newPopulation[i]=new Thing(oldPopulation[j].x, oldPopulation[j].y);
            newPopulation[i+1]=new Thing(oldPopulation[j].x, oldPopulation[j+1].y);
            newPopulation[i+2]=new Thing(oldPopulation[j+1].x, oldPopulation[j].y);
            newPopulation[i+3]=new Thing(oldPopulation[j+1].x, oldPopulation[j+1].y);
            j+=2;

            for(int k=i;k<=i+3;k++){
                int mutationProbability=r.nextInt(100);
                if(mutationProbability<5){
                    int mutation=r.nextInt(5);
                    int xy=abs(r.nextInt(2));
                    if(xy==0){
                        int pm=abs(r.nextInt(2));
                        if(pm==0){
                            newPopulation[k].x+=mutation;
                        }else{
                            newPopulation[k].x-=mutation;
                        }
                    }else{
                        int pm=abs(r.nextInt(2));
                        if(pm==0){
                            newPopulation[k].y+=mutation;
                        }else{
                            newPopulation[k].y-=mutation;
                        }
                    }
                }
            }

            i+=4;
        }
        return newPopulation;
    }

    public static void main(String[] args){
        new Program().geneticAlgo();
    }

}