import java.util.List;
import java.util.Random;

public class RandomAI extends AIPlayer{

    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        if(gameStatus instanceof GameLogic){
            GameLogic gameLogic = (GameLogic) gameStatus;
            List<Position> possibleOptions=gameLogic.ValidMoves();
            Random random = new Random();
            int kind = random.nextInt(3);
            int location= random.nextInt(possibleOptions.size());
            Disc disc;
            Move move;
            if(kind==0){
                disc=new SimpleDisc(gameLogic.getCurrentPlayer());
            }
            else if(kind==1){
                disc=new UnflippableDisc(gameLogic.getCurrentPlayer());
            }
            else{
                disc=new BombDisc(gameLogic.getCurrentPlayer());
            }
            move=new Move(disc,possibleOptions.get(location),null);
            return move;
        }
        return null;
    }
}
