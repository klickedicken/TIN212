import java.io.*;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JOptionPane.*;
import static javax.swing.JLabel.*;

public class Memory extends JFrame implements ActionListener { 
    private int rows; //antal rader med kort
    private int cols; // antal kolumner med kort
    private Card[] allCards; // lista med alla möjliga kort
    private Card[] cards; // lista med kort som används i nuvarande spelomgång
    private Card[] picks = new Card[2]; // lista med de kort som är synliga för tillfället

    private Player[] players; // lista över alla spelarpaneler som ligger till vänster i fönstret.

    private MenuItem newGame = new MenuItem("Nytt spel");
    private MenuItem quit = new MenuItem("Avsluta");
    private MenuItem playerCount = new MenuItem("Antal spelare");
    private JPanel board = new JPanel();
    private JPanel scoreBoard = new JPanel();
    
    private Timer timer; // låter spelaren se sina valda kort innan de försninner.
    
    private boolean active = true;
    private int[] score = new int[2];
    private int turn;
    private int visible;

    private int best = -1;
        
    public Memory(){
        initAllCards();
        initComponents();
        newGame();
        
        setSize(400,300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
   /**
    * Lägger in alla grafisa objekt som inte kommer ändras
    * medans programmet kör. Detta görs alltså en gång. 
    */      
    public void initComponents() {
        setLayout(new BorderLayout());
        
	MenuBar mb = new MenuBar();
	Menu game = new Menu("Spel");
	Menu options = new Menu("Inställningar");

	quit.addActionListener(this);        
    newGame.addActionListener(this);
	playerCount.addActionListener(this);
	
	game.add(newGame);	game.add(quit);
	options.add(playerCount);
	mb.add(game);   	mb.add(options);

	setMenuBar(mb);
        
    add(board, BorderLayout.CENTER);
    add(scoreBoard, BorderLayout.LINE_START);        
    timer = new Timer(0, e -> { endTurn(); });
    timer.setRepeats(false);
    timer.setInitialDelay(1500);
    }
    
   /**
    * initAllCards länkar samman kort och bilderna i mappen mypictures. 
    * Skapar en lista över alla möjliga kort.
    */
    public void initAllCards(){
        File folder = new File("mypictures");
        File[] pictures = folder.listFiles();
        allCards = new Card[pictures.length];
        for(int i=0; i<pictures.length; i++){
            allCards[i] = new Card(new ImageIcon(pictures[i].getPath()));
        }
    }
   /**
    * Placerar ut "spelarna" till vänster i fönstret.
    */
    private void placePlayers(){
        int n = score.length;
        
        scoreBoard.setLayout(new GridLayout(n,1));
        scoreBoard.removeAll();
        
        players = new Player[n];
        
        for(int i = 0;i < n;i++){
            players[i] = new Player("Player " + (i+1));
            scoreBoard.add(players[i]);
        }
        players[0].activate();
    }
   /**
    * Placerar korten i panelen board, som har en rutnätslayout.
    */
    private void placeCards(){    
        board.removeAll();
        board.setLayout(new GridLayout(rows,cols));
        for(int i=0;i<cards.length;i++){
            board.add(cards[i]);
            cards[i].setStatus(Card.Status.HIDDEN);
            cards[i].addActionListener(this);
        }  
    }
    public void newGame(){
	newGame(true);
    }
   /**
    * Startar en ny omgång av memory. Blandar om och väljer
    * om korten. Beroende på värdet på newBoard gör den ett 
    * bräde med andra dimensioner. 
    */
    public void newGame(boolean newBoard){	
        if (newBoard) setBoardSize();
        
        visible = 0;
        turn = 0;
	Arrays.fill(score,0);
	       
        Card[] temp = Arrays.copyOf(allCards,allCards.length);
        Tools.randomOrder(temp);        
        
        cards = new Card[rows*cols];
        for(int i=0;i<rows*cols/2;i++){
            cards[i]             = temp[i].copy();
            cards[i+rows*cols/2] = temp[i].copy();
        }
        
        Tools.randomOrder(cards);
	
        placePlayers();
        placeCards();

	repaint();
	revalidate();
    }
   /**
    * Frågar användaren om önskat antal rader och kolumner. 
    * Meddelar användaren om hen angett för höga tal eller 
    * tal som ger ett uda antal kort.
    */
    public void setBoardSize(){
        while (true) { 
            rows = Integer.parseInt(showInputDialog(
                        null, "Antal rader?", "Memory", QUESTION_MESSAGE));
            cols = Integer.parseInt(showInputDialog(
                        null, "Antal kolumner?", "Memory", QUESTION_MESSAGE));
            if (rows*cols>36){
                showMessageDialog(null,"För stor spelplan! Max 36 kort.", 
                                  "Memory", ERROR_MESSAGE);
            }
            else if (rows*cols % 2 == 1){
                showMessageDialog(null,"Välj jämnt antal kort!", 
                                  "Memory", ERROR_MESSAGE); 
            }
            else break;                
        }                         
    }
    private boolean boardEmpty(){
	boolean empty = true;
	for(int i = 0;i < cards.length;i++){
	    if(cards[i].getStatus() != Card.Status.MISSING){
		empty = false;
		break;
	    }
	}
	return empty;
    }
    private int winner(){
	boolean draw = false;
	int max = score[0];
	int winner = 0;
	for(int i = 1;i < score.length;i++) {
	    if(score[i] > max){
		max = score[i];
		draw = false;
		winner = i;
	    }
	    else if(score[i] == max){
		draw = true;
	    }
	}
	if(draw) return -1;
	else     return winner;
    }
    private void endGame(){
	if (score.length == 1){
	    incrementTurn();
	}
	else{
	    score[turn]++;
	    players[turn].setScore(score[turn]);
	}
	if(boardEmpty()){
	    String text;	
	    if (score.length == 1){
		text = "Du försökte " + score[0] + " gånger!";
		if (best != -1) text += " Ditt bästa var " + best;
		if (score[0] < best || best == -1) best = score[0];	    
	    }
	    else{
		int winner = winner();
		text = "Oavgjort!";
		if(winner != -1){
		    text = "Spelare " + (winner + 1) + " vann!";
		}	    
	    }
	    int n = showConfirmDialog(this, text + "\nVill ni spela igen?",
				      "Omgång slut", YES_NO_OPTION);	    
	    if (n == 0) newGame(false);
	    else        System.exit(0);
	}
    }
    private void endTurn(){
        active = true;
        visible = 0;

	if(score.length == 1){
	    score[turn]++;
	    players[turn].setScore(score[turn]);	    
	}
	if(picks[0].equalIcon(picks[1])) {
	    picks[0].setStatus(Card.Status.MISSING);
	    picks[1].setStatus(Card.Status.MISSING);
	    endGame();
	}
	else {
	    picks[0].setStatus(Card.Status.HIDDEN);
	    picks[1].setStatus(Card.Status.HIDDEN);
	    incrementTurn();
	}
    }
    private void incrementTurn(){
	players[turn].deactivate();
	turn = (turn + 1) % score.length;
	players[turn].activate();
    }
    public void actionPerformed(ActionEvent e) {
	if(active){
	    Object o = e.getSource();            	
            if (o == quit) {
                System.exit(0);
            }
            else if (o == newGame) {
                newGame();
            }
	    else if (o == playerCount){
		String input = showInputDialog(null, "Antal spelare?");
		if(input != null){
		    int n = Integer.parseInt(input);
		    score = new int[n];
		    newGame(false);
		}
	    }
            else {
                Card c = (Card) o;
                if(c.getStatus() == Card.Status.HIDDEN) {
                    c.setStatus(Card.Status.VISIBLE);
		    picks[visible] = c;
                    if(++visible >= 2) {
                        active = false;
                        timer.restart();
                    }
                }                
            }  
        }      
    }

    private class Player extends JPanel{
	private JLabel player;
	private JLabel score;
	private int best;
	
	public Player(String name){
	    player = new JLabel(name);
	    player.setHorizontalAlignment(CENTER);	    
	    score = new JLabel("0");
	    score.setHorizontalAlignment(CENTER);
	    deactivate();

	    setPreferredSize(new Dimension(100,50));
	    
	    setLayout(new GridLayout(2,1));
	    add(player);
	    add(score);
	}
	public void setScore(int n){
	    score.setText(Integer.toString(n));
	}
	public void activate(){
	    setBackground(Color.yellow);
	}
	public void deactivate(){
	    setBackground(Color.gray);
	}
    }
    
    public static void main(String[] args){
        new Memory();        
    }
}
