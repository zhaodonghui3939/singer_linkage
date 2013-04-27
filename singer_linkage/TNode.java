package singer_linkage;

/*Here is the data structure of the tree node*/
public class TNode implements Cloneable {

  private TNode leftNode;
	private TNode rightNode;
	private int index;       //the position of the dataset
	private double[] data;
	
	public TNode(){
		leftNode = null;
		rightNode = null;
		index = 0;
		data = null;
	}

	//Same as the copy construct,because in java all by reference
	//And sometime we need by data
	public Object clone() {
		TNode o = null;
		try {
			o = (TNode) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	public TNode getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(TNode leftNode) {
		this.leftNode = leftNode;
	}

	public TNode getRightNode() {
		return rightNode;
	}

	public void setRightNode(TNode rightNode) {
		this.rightNode = rightNode;
	}

	public double[] getData() {
		return data;
	}

	public void setData(double[] data) {
		this.data = data;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
