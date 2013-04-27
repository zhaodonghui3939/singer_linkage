package singer_linkage;

import java.util.LinkedList;
import java.util.Queue;



public class HierarchyTree {
  private  TNode headNode;

	
	//visit by level
	public void LayerOrder( ){
		System.out.println("Travel by level:");
		Queue<TNode> queue = new LinkedList<TNode>(); 
		queue.add(headNode);
		while(!queue.isEmpty()){
			TNode temp = queue.poll();
			System.out.println(temp.getIndex()+" value:"+temp.getData()[0]);
			if(temp.getLeftNode() != null)
				queue.add(temp.getLeftNode());
			
			if(temp.getRightNode()!= null)
				queue.add(temp.getRightNode());
		}
	}
	
	public int findData(double[] data){
		TNode p = new TNode();
		p = (TNode)headNode.clone();
		while(p.getLeftNode() != null){
			if(compare(data, p.getLeftNode(),p.getRightNode()) == 1){
				p = p.getLeftNode();
			}else
				p = p.getRightNode();
		}
		return p.getIndex();
		
	}
	private  int compare(double[] data, TNode t1, TNode t2) {
		double s1 = 0, s2 = 0;
		for (int i = 0; i < data.length; ++i) {
			s1 = s1 + (data[i] - t1.getData()[i])
					* (data[i] - t1.getData()[i]);
			s2 = s2 + (data[i] - t2.getData()[i])
					* (data[i] - t2.getData()[i]);
		}

		if (s1 <= s2) {
			return 1;
		} else
			return 0;
	}


	public TNode getHeadNode() {
		return headNode;
	}

	public void setHeadNode(TNode headNode) {
		this.headNode = headNode;
	}

	public int findNode(Object data) {
		return 0;
	}

}


