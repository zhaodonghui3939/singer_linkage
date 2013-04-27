package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


import singer_linkage.HierarchyTree;
import singer_linkage.SinLinkage;


import math.Matrix;

public class main {
  
	public static void main(String[] args) throws FileNotFoundException, IOException{
		Matrix data = Matrix.read(new BufferedReader(new FileReader(
				"C:\\Users\\东辉\\Desktop\\linkage\\data.txt")));
		//Matrix data = dimensionalityReduced(data1);
		SinLinkage sinLinkage = new SinLinkage(data.getRowDimension());
		HierarchyTree hierarchyTree = new HierarchyTree();
		sinLinkage.cluster(data);
		dataIO_clustered(data, 2 ,SinLinkage.getClusterMatrix());
		//sinLinkage.getClusterMatrix().print(1, 0);
		sinLinkage.getHierarchyTree().LayerOrder();
		//double[] temp = new  double[1] ;
	    //temp[0] = 9.8;
		//int result = sinLinkage.getHierarchyTree().findData(temp);
		//System.out.println("The  nearest data compared with "+temp[0]+" is finded via tree:"+result+" position,true value is:"+data.get(result, 0));
		
	}
	
	
	
	public static void dataIO_clustered(Matrix data, int N ,Matrix ClusterMatrix) throws FileNotFoundException{
		Matrix data_clustered = new Matrix(data.getRowDimension(),data.getColumnDimension() + 1);
		for(int i = 0; i < data_clustered.getRowDimension(); ++i)
		{
			for(int j = 0; j < data_clustered.getColumnDimension() - 1;++j){
				data_clustered.set(i, j, data.get(i, j));
			}
			data_clustered.set(i, data_clustered.getColumnDimension() - 1, ClusterMatrix.get(data.getRowDimension() - N, i));
		}
		
		PrintWriter FILE = new PrintWriter(new FileOutputStream(
				"C:\\Users\\东辉\\Desktop\\linkage\\datacluster.txt"));
		data_clustered.print(FILE, 2, 3);
		FILE.close();	
	}
	
	public  Matrix dimensionalityReduced (Matrix data){
		Matrix datanew = new Matrix(data.getRowDimension(),data.getColumnDimension() - 1);
		for(int i = 0; i < data.getRowDimension(); ++i)
			for(int j = 0; j < data.getColumnDimension() -1; ++j){
				datanew.set(i, j, data.get(i, j));
			}		
		return datanew;
	}	
}
