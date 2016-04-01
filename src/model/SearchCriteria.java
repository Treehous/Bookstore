package model;

public class SearchCriteria {
	public final static int ASCENDINGORDER = 1;
	public final static int DESCENDINGORDER = 2;
	public final static int ALPHANUMERICORDER = 3;
	
	public final static int SEARCHFORBOOKSBYAUTHOR = 1;
	public final static int SEARCHFORISBN = 2;
	public final static int SEARCHFORPRICERANGE = 3;
	
	private String criteria;
	private int type;
	private int order;
	
	public SearchCriteria(String criteria, int type,int order){
		this.criteria = criteria;
		this.type = type;
		this.order = order;
		
	}
	
	public String getCriteria(){
		return this.criteria;
	}
	
	public int getType(){
		return this.type;
	}
	
	public int getOrder(){
		return this.order;
	}

}
