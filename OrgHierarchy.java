
// Tree node
class Node {
	public int bossid;
	public int id;
	public int l=0;
	public int level;
	public Node employe[];
	public Node(){
		employe = new Node[1];
	}
	public void addemploye(int employeid){
	if(l==employe.length){
			Node temp[] = new Node[employe.length];
			temp = employe;
			employe = new Node[temp.length*2];
			for(int i=0;i<temp.length;i++){
				employe[i]=temp[i];
			}
		}
		employe[l] = new Node();
		employe[l].bossid=id;
		employe[l].level=level+1;
		employe[l].id=employeid;
		l++;
	}
	public void remove(int employeid){
		int n=0;
		if(l==(employe.length/2)+1){
			Node temp[] = new Node[employe.length/2];
			for(int i=0;i<employe.length;i++){
				if(employe[i].id!=employeid){
					temp[n]=employe[i];
					n++;
				}
			}
			employe = new Node[temp.length];
			employe = temp;
			l--;
		}
		else{
			Node temp[] = new Node[employe.length];
			for(int i=0;i<employe.length;i++){
				if(employe[i].id!=employeid){
					temp[n]=employe[i];
					n++;
				}
			}
			employe = temp;
			l--;
		}



  
}
}

public class OrgHierarchy implements OrgHierarchyInterface{

//root node
	Node root=null;
	avltree info = new avltree();



public boolean isEmpty(){

	if(info.size==0)
		return true;
	return false;
} 

public int size(){
	return info.size;
}
public int employeunder(Node s){
	int n=0;
	if(s.l!=0){
		for(int i=0;i<s.l;i++){
			n+=employeunder(s.employe[i]);
		}
		return n+1;
	}
	else{
		return 1;
	}
}
public int level(int id) throws IllegalIDException{
	return info.level(id);

} 

public void hireOwner(int id) throws NotEmptyException{
	if(root!=null){
		throw new NotEmptyException("the organization already have a owner");
	}
	else{
	info.assignroot(id,-1,1);
	root = new Node();
	root.level=1;
	root.id=id;
	root.bossid=-1;
	}
	


}

public void hireEmployee(int id, int bossid) throws IllegalIDException{
	Node temp = new Node();
	temp = find(root,bossid);
	temp.addemploye(id);
	info.add(id,bossid,temp.level+1);

}
 
public void fireEmployee(int id) throws IllegalIDException{
	if(root.id == id){
		throw new IllegalIDException("the owner can't be fired");
	}
	Node temp1 = new Node();
	Node temp2 = new Node();
	temp1 = find(root,id);
	temp2 = find(root,temp1.bossid);
	temp2.remove(id);
	info.delete(id);
}
public Node find(Node temp,int id)throws IllegalIDException{
	if(info.find(id)){
	Node temp2 = new Node();
	temp2=null;
	if(temp.id==id){
		return temp;
	}
	else if(temp.l==0){
		return null;
	}
	else{
		for(int i=0;i<temp.l;i++){
			temp2 = find(temp.employe[i],id);
			if(temp2!=null)
				break;
	}
	}
	return temp2;
	}
	else{
		throw new IllegalIDException("there is no one with this id");
	}
}

public void fireEmployee(int id, int manageid) throws IllegalIDException{
	Node temp1 = new Node();
	Node temp2 = new Node();
	temp1 = find(root,id);
	temp2 = find(root,manageid);
	for(int i=0;i<temp1.l;i++){

		temp2.addemploye(temp1.employe[i].id);
		info.updateboss(temp1.employe[i].id,manageid);
	}
	Node temp3 = new Node();
	temp3 = find(root,temp1.bossid);

	temp1=null;
	temp3.remove(id);
	info.delete(id);

	
}

public int boss(int id) throws IllegalIDException{
	return info.getbossid(id);
}

public int lowestCommonBoss(int id1, int id2) throws IllegalIDException{
	if(id1==root.id||id2==root.id)
		return -1;
	else if(level(id1)==level(id2))
		if(boss(id1)==boss(id2))
			return boss(id1);
		else
			return lowestCommonBoss(boss(id1),boss(id2));
	else if(level(id1)>level(id2))
		return lowestCommonBoss(boss(id1),id2);
	else 
		return lowestCommonBoss(id1,boss(id2));

	
}

public String toString(int id) throws IllegalIDException{
	return "abhi nahi likha h iska code";

}

}

class avlnode{
	public int id;
	public int bossid;
	public int level;
	public avlnode left;
	public avlnode right;
	public avlnode(){
	right=null;
	left=null;
	}
	public void addleft(int id, int bossid,int level){
		left = new avlnode();
		left.id = id;
		left.bossid = bossid;
		left.level=level;
	}
	public void addright(int id, int bossid,int level){
		right = new avlnode();
		right.id = id;
		right.bossid = bossid;
		right.level=level;
	}
}

class avltree{
	public int size=0;
	public avlnode root=null;
	public int level(int id)throws IllegalIDException{
		avlnode temp = new avlnode();
		temp = find(root,id);
		return temp.level;
	}

	public void assignroot(int id,int bossid,int level){
		root = new avlnode();
		root.id=id;
		root.bossid = bossid;
		root.level=level;
		size++;
	}
	public void add(avlnode checknode,int id,int bossid,int level) throws IllegalIDException{
		if(id<checknode.id){
			if(checknode.left==null){
				checknode.addleft(id,bossid,level);
			}
			else{
				add(checknode.left,id,bossid,level);
			}
		}
		else if(id>checknode.id){
			if(checknode.right==null){
				checknode.addright(id,bossid,level);
				
			}
			else{
				add(checknode.right,id,bossid,level);
			}
		}
		else{
			throw new IllegalIDException("there is already a employe with this id");
		}
	}
	public void add(int id,int bossid,int level)throws IllegalIDException{
		add(root,id,bossid,level);
		balanceinsert(id);
		size++;
	}
	public int getbossid(int id)throws IllegalIDException{
		avlnode temp = new avlnode();
		temp = find(root,id);
		return temp.bossid;
	}
	public void updateboss(int id,int manageid)throws IllegalIDException{
		avlnode temp = new avlnode();
		temp = find(root,id);
		temp.bossid = manageid;
	}
	public void delete(int id)throws IllegalIDException{
		avlnode todelete = new avlnode();
		todelete = find(root,id);
		if(root.id==id){
			avlnode temp = new avlnode();
			temp=getleastright(id);
			int tempid=temp.id;
			int tempbossid = temp.bossid;
			int templevel=temp.level;
			delete(temp.id);
			root.id=tempid;
			root.bossid=tempbossid;
			root.level=templevel;
		
		}
		else if(todelete.left==null&&todelete.right==null){
			avlnode temp = getparent(root,id);
			if(temp.left.id==id)
				temp.left=null;
			else
				temp.right=null;
		}
		else if(todelete.left==null&&todelete.right!=null){
			avlnode temp = getparent(root,id);
			if(temp.right.id==id)
				temp.right=todelete.right;
			else
				temp.left=todelete.right;
		}
		else if(todelete.right==null&&todelete.left!=null){
			avlnode temp = getparent(root,id);
			if(temp.right.id==id)
				temp.right=todelete.left;
			else
				temp.left=todelete.left;
		}
		else{
			avlnode temp = new avlnode();
			temp = getleastright(id);
			System.out.println(temp.id);
			int tempid=temp.id;
			int tempbossid = temp.bossid;
			int templevel= temp.level;
			delete(temp.id);
			avlnode temp2 = find(root,id);
			temp2.id=tempid;
			temp2.bossid = tempbossid;
			temp2.level=templevel;
		}
		balancedelete(root);
		size--;
	}
	public boolean find(int id)throws IllegalIDException{
		find(root,id);
		return true;
	}

	public avlnode find (avlnode checknode,int id)throws IllegalIDException{
		if(checknode.id==id){
			return checknode;
		}
		else if(checknode.id > id){
			if(checknode.left==null){
				throw new IllegalIDException("There is now Employe with this id");
			}
			else{
				return find(checknode.left,id);
			}
		}
		else{
			if(checknode.right==null){
				throw new IllegalIDException("There is no Employe with this id");
			}
			else{
				return find(checknode.right,id);
			}
		}
	}
	public avlnode getparent(avlnode checknode,int id){
		if(checknode.id > id){
			if(checknode.left.id == id){
				return checknode;
			}
			else{
				return getparent(checknode.left,id);
			}
		}
		else{
			if(checknode.right.id == id){
				return checknode;
			}
			else {
				return getparent(checknode.right,id);
			}
		}

		}
	public avlnode getleastright(int id)throws IllegalIDException{
		avlnode temp = new avlnode();
		avlnode temp2=new avlnode();
		temp = find(root,id);
		temp2=temp.right;
		if(temp2==null){

			return temp;
		}
		else{
		while(temp2.left!=null)
			temp2=temp2.left;
		return temp2;
		}
	}

	public int getheight(int id)throws IllegalIDException{
		avlnode temp = find(root,id);
		if(temp.right==null&&temp.left==null){
			return 0;
		}
		else if(temp.right==null)
			return (getheight(temp.left.id)+1);
		else if(temp.left==null)
			return (getheight(temp.right.id)+1);
		else{
			int height1=getheight(temp.right.id);
			int height2=getheight(temp.left.id);
			if(height1>height2)
				return height1+1;
			else 
				return height2+1;
		}
	}
	public void preorder(avlnode temp){
		if(temp==null)
			return;
		preorder(temp.left);
		System.out.println(" "+temp.id+" ");
		preorder(temp.right);
	}


	public void balancedelete(avlnode temp)throws IllegalIDException{
		int h1;
		int h2;
		if(temp.left==null&&temp.right==null){
			return;
		}
		if(temp.left==null){
			h1=-1;
		}
		else{
			h1=getheight(temp.left.id);
		}
		if(temp.right==null){
			h2=-1;
		}
		else{
			h2=getheight(temp.right.id);
		}
		if(h1-h2>2||h1-h2<-2){
			if(h1>h2){
				int h3=getheight(temp.left.right.id);
				int h4=getheight(temp.left.left.id);
				if(h3>h4){
					balanceinsert(temp.left.right.id);

				}
				else{
					balanceinsert(temp.left.left.id);
				}
				
			}
			else{
				int h3=getheight(temp.right.right.id);
				int h4=getheight(temp.right.left.id);
				if(h3>h4){
					balanceinsert(temp.right.right.id);

				}
				else{
					balanceinsert(temp.right.left.id);
				}

			}
		}
		else{
			if((h1==-1&&h2==0)||(h1==0&&h2==-1)||(h1==0&&h2==0)||(h1==1&&h2==0)||(h1==0&&h2==1)){
				return;
			}
			else{
				balancedelete(temp.left);
				balancedelete(temp.right);
			}

		}
	}

	public void balanceinsert(int id)throws IllegalIDException{
		if(size>1){
		avlnode remake = new avlnode();
		avlnode addednode = new avlnode();
		avlnode temp = new avlnode();
		addednode = find(root,id);
		temp = getparent(root,id);
		if(temp.left!=null&&temp.right!=null){}

		else{

			avlnode temp1 = new avlnode();
			temp1 = getparent(root,temp.id);
			if(temp1.right==null||temp1.left==null){
				balancegrandparent(id);
			}

			else{
				if(temp1.id==root.id){
					return;
				}
				else{
				balanceinmiddle(temp1.id);
			}
			}
		}
					
		}		
	}
	public void balancegrandparent(int id)throws IllegalIDException{
	avlnode x = new avlnode();
	avlnode y = new avlnode();
	avlnode z = new avlnode();
	avlnode temp = new avlnode();
	x=find(root,id);
	y=getparent(root,x.id);
	z=getparent(root,y.id);
	if(z.left==null){
		if(y.left==null){
			signlerotation(x.id);
		}
		else{
			doublerotation(x.id);
		}

	}
	else{
		if(y.right==null){
			signlerotation(x.id);
		}
		else{
			doublerotation(x.id);	
		}
	}
	}
public void balanceinmiddle(int id) throws IllegalIDException{
	avlnode unbalance = new avlnode();
	unbalance = getparent(root,id);
	int h1=getheight(unbalance.left.id);
	int h2=getheight(unbalance.right.id);
	while(h1-h2<2&&h1-h2>-2){
		if(unbalance.id==root.id){
			return;
		}
		else{
		int n = unbalance.id;
		unbalance = getparent(root,n);
		h1=getheight(unbalance.left.id);
		h2=getheight(unbalance.right.id);
		}
	}
	if(h1>h2){
		int h3=getheight(unbalance.left.right.id);
		int h4=getheight(unbalance.left.left.id);
		if(h3>h4){
			doublerotation(unbalance.left.right.id);
		}
		else{
			signlerotation(unbalance.left.left.id);
		}
	}
	else{
		int h3=getheight(unbalance.right.right.id);
		int h4=getheight(unbalance.right.left.id);
		if(h4>h3){
			doublerotation(unbalance.right.left.id);
		}
		else{
			signlerotation(unbalance.right.right.id);
		}

	}
}


public void signlerotation(int id)throws IllegalIDException{
	avlnode temp1 = new avlnode();
	avlnode x = new avlnode();
	avlnode y = new avlnode();
	avlnode z = new avlnode();
	x=find(root,id);
	y=getparent(root,x.id);
	z=getparent(root,y.id);
	if(z.id==root.id){
	if(x.id<y.id){
		z.left=y.right;
		y.right=z;
		root=y;
	}
	else{
		z.right=y.left;
		y.left=z;
		root=y;

	}
	}
	else{
		temp1=getparent(root,z.id);
		if(x.id<y.id){
		if(z.id<temp1.id){
			z.left=y.right;
			y.right=z;
			temp1.left=y;
		}
		else{
			z.left=y.right;
			y.right=z;
			temp1.right=y;
		}
		}
		else{
			if(z.id<temp1.id){
			z.right=y.left;
			y.left=z;
			temp1.left=y;
		}
		else{
			z.right=y.left;
			y.left=z;
			temp1.right=y;
		}

		}

	}

}
public void doublerotation(int id) throws IllegalIDException{
	avlnode temp1 = new avlnode();
	avlnode temp2 = new avlnode();
	avlnode temp3 = new avlnode();
	avlnode x = new avlnode();
	avlnode y = new avlnode();
	avlnode z = new avlnode();
	x=find(root,id);
	y=getparent(root,x.id);
	z=getparent(root,y.id);
	if(z.id==root.id){
	if(x.id<y.id){
		y.left=x.right;
		x.right=y;
		z.right=x.left;
		x.left=z;
		root=x;
	}
	else{
		y.right=x.left;
		x.left=y;
		z.left=x.right;
		x.right=z;
		root=x;

	}
	}
	else{
		temp1=getparent(root,z.id);
		if(x.id<y.id){
		if(z.id<temp1.id){
			y.left=x.right;
			x.right=y;
			z.right=x.left;
			x.left=z;
			temp1.left=x;
		}
		else{
			y.left=x.right;
			x.right=y;
			z.right=x.left;
			x.left=z;
			temp1.right=x;
		}
		}
		else{
			if(z.id<temp1.id){
			y.right=x.left;
		x.left=y;
		z.left=x.right;
		x.right=z;
		temp1.left=x;
		}
		else{
			y.right=x.left;
			x.left=y;
			z.left=x.right;
			x.right=z;
			temp1.right=x;
		}

		}

	}


}
}
