package src;

public class Author {
	private AuthorName name;
	
	public Author(String name){
		this.name = this.parseAuthorName(name);
	}
	
	public Author(String first, String last){
		this.name = new AuthorName(first, last);
	}
	
	public String getAuthorsLastName(){
		return this.name.getLastName();
	}
	
	public String getAuthorsFirstName(){
		return this.name.getFirstName();
	}
	
	public String getAuthorsFullName(){
		return this.name.getFullName();
	}

	private AuthorName parseAuthorName(String s){
		String first = null;
		String last = null;

		int index = s.indexOf(' ');

		if(index == -1){
			last = s;
		}
		else {
			first = s.substring(0, index);
			last = s.substring(index+1, s.length());
		}
		return new AuthorName(first,last);
	}

	//can be replaced with a full-blown author class
	private final class AuthorName{
		private final String firstName;
		private final String lastName;

		private AuthorName(String first, String last){
			if(first != null)
				this.firstName = first;
			else 
				this.firstName = "";

			if(last != null)
				this.lastName = last;
			else 
				this.lastName = "";
		}

		private String getFirstName(){
			return this.firstName;
		}

		private String getLastName(){
			return this.lastName;
		}

		private String getFullName(){
			if(this.getFirstName().equals(""))
				return this.getLastName();	
			else
				return this.getLastName() + ", " + this.getFirstName();
		}
	}
}
