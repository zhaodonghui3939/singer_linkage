package singer_linkage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import math.Matrix;

public class SinLinkage {
  private static  Matrix clusterMatrix;         //is the cluster Matrix
	private  Map<Integer, int[]> similarity;
	private  HierarchyTree hierarchyTree;

	public static Matrix getClusterMatrix() {
		return clusterMatrix;
	}

	// init the clusterMatridx and similarityMatrix
	public SinLinkage(int N) {
		clusterMatrix = new Matrix(N, N);
		similarity = new HashMap<Integer, int[]>();
		hierarchyTree = new HierarchyTree();
		
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				clusterMatrix.set(i, j, j);
			}
		}
	}

	// Here to update the clusterMatrix until the tree be builded.
	public  void cluster(Matrix data) {	
		TNode[] tempNode = new TNode[data.getRowDimension()];
		initData(data,tempNode);
		int COUNT = similarity.size();
		int NUM = 0, INDEX = 0;
		
		/*update the clusterMatrix and build the Hierarchytree*/
		while (INDEX < COUNT) {
			int[] min = similarity.get(INDEX);  //get the min col and row for update
			
			/* Check the cluster, if cluster has been clustered one ,break*/
			if (check(clusterMatrix.getArray()[NUM]) == 1) {		
				break;
			}

			/* if, the col and row is different, update now */
			if (clusterMatrix.get(NUM, min[0]) != clusterMatrix.get(NUM, min[1])) {	
				for (int i = 0; i < data.getRowDimension(); ++i) {
					clusterMatrix.set(NUM + 1, i, clusterMatrix.get(NUM, i)); //copy the previous cluster row		
					if (clusterMatrix.get(NUM, i) == clusterMatrix.get(NUM,min[1])) /*Update the now cluster row */
						clusterMatrix.set(NUM + 1, i,clusterMatrix.get(NUM, min[0]));
				}

				/*Here to create a new tree node for building the Hierarchytree
				 * and the node is the parent node for the previous two node*/
				TNode pnode = new TNode();       //Create parent node;
			    
				VectorMean meanData = new VectorMean(data.getColumnDimension());   
				for (int i = 0; i < data.getRowDimension(); ++i) {//compute the node value,//and is the the mean of belonging set node
					if (clusterMatrix.get(NUM + 1, i) == clusterMatrix.get(NUM,min[0])){
						meanData.add(data.getArray()[i]);
					}
				}
				pnode.setData(meanData.getMean());           			
				pnode.setIndex((int) clusterMatrix.get(NUM, min[0]));//put the node index with clusterMatrix.get(NUM, min[0])
				
				/*Very important here, we must use data by data,not by reference*/
				TNode s1 = new TNode();
				s1 = (TNode)tempNode[(int) clusterMatrix.get(NUM, min[0])].clone();
				TNode s2 = new TNode();
				s2 = (TNode)tempNode[(int) clusterMatrix.get(NUM, min[1])].clone();				
				
				if (compare(pnode, s1, s2) == 1) {//Compare the children node with parent,the lower are left
					pnode.setLeftNode(s1);
					pnode.setRightNode(s2);
				}else{
					pnode.setLeftNode(s2);
					pnode.setRightNode(s1);
				}
				
				//update the tempnode set for the next building
				for (int i = 0; i < data.getRowDimension(); ++i) {
					if (clusterMatrix.get(NUM + 1, i) == clusterMatrix.get(NUM,min[0])){
						tempNode[i] =  pnode;
					}
				}
				++NUM;
			}
			++INDEX;
		}
		
		//The tree is builded,and we create the root 
		hierarchyTree.setHeadNode(tempNode[0]);
	}

	private static int check(double[] clusterpart) {
		for (int i = 1; i < clusterpart.length; ++i) {
			if (clusterpart[i] != clusterpart[0]) {
				return 0;
			}
		}
		return 1;
	}

	private  int compare(TNode P, TNode t1, TNode t2) {
		double s1 = 0, s2 = 0;
		for (int i = 0; i < P.getData().length; ++i) {
			s1 = s1 + (P.getData()[i] - t1.getData()[i])
					* (P.getData()[i] - t1.getData()[i]);
			s2 = s2 + (P.getData()[i] - t2.getData()[i])
					* (P.getData()[i] - t2.getData()[i]);
		}

		if (s1 <= s2) {
			return 1;
		} else
			return 0;
	}

	// initialization the initSimilarity
	private  void initData(Matrix data,TNode[] tempNode) {
		Map<Double, int[]> tempsimilarity = new HashMap<Double, int[]>();
		int N = data.getRowDimension();
		double[] array = new double[(N * N -N)/2];
		Random rand = new Random();
		int count = 0;
		for (int i = 0; i < data.getRowDimension(); ++i) {		
			//init the node
			TNode node = new TNode();
			node.setIndex(i);
			node.setLeftNode(null);
			node.setRightNode(null);
			node.setData(data.getArray()[i]);
			tempNode[i] = node;

			//init the similarity Map
			for (int j = i + 1; j < data.getRowDimension(); ++j) {
				double s = 0;
				int[] index = new int[2];
				index[0] = i;
				index[1] = j;
				for (int k = 0; k < data.getColumnDimension(); ++k) {
					s = s + Math.pow((data.get(i, k) - data.get(j, k)), 2);
				}
				s = s / data.getColumnDimension();
				
				//Here maybe the key is same,so we must process this situation
				if(tempsimilarity.containsKey(s)){
					double snew = s +  s * rand.nextGaussian() * rand.nextGaussian()/100;
					tempsimilarity.put(snew, index);
					array[count] = snew;
				}else{
					tempsimilarity.put(s, index);
					array[count] = s;
				}	
				++count;
			}
		}

		// sort the similarity by Similarity
		mapSortByKey(tempsimilarity,array);
		for(int i = 0; i < similarity.size(); ++i){
			System.out.println(similarity.get(i)[0] + " "+similarity.get(i)[1]);
		}

	}
	
	private void mapSortByKey(Map<Double, int[]> tempsimilarity,double[] array){
		Arrays.sort(array);
		for(int i = 0; i < tempsimilarity.size(); ++i){
			similarity.put(i, tempsimilarity.get(array[i]));
		}	
	}

	
	public HierarchyTree getHierarchyTree() {
		return hierarchyTree;
	}
}
