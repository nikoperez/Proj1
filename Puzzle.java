public class Puzzle {
	
	private Grid wordsearch;
	private Deque<Loc> path1; 
	private boolean [][] visited;
	
	// directional vertices controls the direction of 
	// where to search next
	static int rowDirect[] = { -1, 0, 1, 0 };
	static int colDirect[] = { 0, 1, 0, - 1 };
	
	public Puzzle(Grid grid) {
		this.wordsearch = grid;
		visited = new boolean[wordsearch.size()][wordsearch.size()];
	}

	public String find(String word, int r, int c) {
		BFS(word, r, c);
				
		String answer = "";
		int size = path1.size();
				
		for(int i = 0; i < size; i++) {
			try {
				answer += path1.getFirst().toString();
			} catch (EmptyDequeException e) {}
		}
		visited =  new boolean[wordsearch.size()][wordsearch.size()];
		return answer;
	}

	private void BFS(String word, int r, int c) {
		Deque<Loc>queue = new Deque<Loc>();
		Loc start = wordsearch.getLoc(r, c);
		queue.addToBack(start);
		
		visited[start.row][start.col] = true;
				
		try {
			while(!queue.isEmpty()) {
								
				Loc cord = queue.getFirst();
				
				int currX = cord.row;
				int currY = cord.col;
				if(cord.getVal().equals(word.substring(0,1))) {
					path1 = DFS(word, cord);
					if(path1.size() == word.length())
						return;
				}
				
				// neighbor index
				for(int i = 0; i < 4; i++) {
					int adjx = currX + rowDirect[i];
					int adjy = currY + colDirect[i];
										
					if(valid(adjx, adjy)) {
						Loc add = wordsearch.getLoc(adjx, adjy);
						queue.addToBack(add);
						visited[add.row][add.col] = true;
					}
				}
			}
		} catch (EmptyDequeException e) {}
	}
	
	private Deque<Loc> DFS(String word, Loc cord) {
		Deque<Loc> dfs = new Deque<Loc>(); // creates a stack
	
		int letter = 1;
		dfs.addToBack(cord);
		dfs = DFSrecur(word, cord, dfs, letter);
		return dfs;
	}
	
	
	private Deque<Loc> DFSrecur(String word, Loc cord, Deque<Loc> stack, int letter){
		
		if(stack.size() == word.length()) {
			return stack;
		}
		else {
			for(int i = 0; i < 4; i++) {
				int adjx = cord.row + rowDirect[i];
				int adjy = cord.col + colDirect[i];
				
				Loc poss = wordsearch.getLoc(adjx, adjy);
				
				if(poss != null && poss.getVal().equals(word.substring(letter, letter+1))) {
										
					stack.addToBack(poss);
					DFSrecur(word, poss, stack, letter+1);
					
					
					try {
						if(stack.size() == word.length()) {
							return stack;
						}
						stack.getLast();
					} catch (EmptyDequeException e) {}
				}
			}
			return stack;	
		}	
	}

	
	private boolean valid(int x, int y) {
		if (x < 0 || x > wordsearch.size()-1  || y < 0 ||
				y > wordsearch.size()-1)
			return false;
		if (visited[x][y])
			return false;
		
		return true;
	}

}
